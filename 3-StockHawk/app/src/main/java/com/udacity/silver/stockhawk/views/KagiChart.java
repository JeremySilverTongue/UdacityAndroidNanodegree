package com.udacity.silver.stockhawk.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.google.common.primitives.Doubles;
import com.udacity.silver.stockhawk.R;

import java.util.ArrayList;
import java.util.List;


/**
 * This is a custom view I started working on for displaying the stock history in the list items themselves. I didn't get around to using it, but I'm including the code here for my own reference.
 */

public class KagiChart extends View {

    public static final String TAG = KagiChart.class.getSimpleName();

    private Paint upPaint;
    private Paint downPaint;
    private double reversalRatio = .05;
    private float stepSize = 30.0f;

    private ArrayList<Double> extrema;
    private ArrayList<Double> scaledExtrema;

    public KagiChart(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);

        extrema = new ArrayList<>();
        scaledExtrema = new ArrayList<>();
    }

    public void setData(List<Double> data) {
        this.extrema = findExtrema(data);
        scaleExtrema();
    }

    private ArrayList<Double> findExtrema(List<Double> data) {

        ArrayList<Double> rawExtrema = new ArrayList<>();

        double finalValue = data.get(data.size() - 1);
        double reversalThreshold = reversalRatio * finalValue;

        boolean increasing = true;
        double previousExtrema = data.get(0);

        rawExtrema.add(previousExtrema);


        for (Double dataPoint : data) {
            if (increasing) {
                if (dataPoint > previousExtrema) {
                    previousExtrema = dataPoint;
                } else if (previousExtrema - dataPoint > reversalThreshold) {
                    rawExtrema.add(previousExtrema);
                    previousExtrema = dataPoint;
                    increasing = false;
                }
            } else {
                if (dataPoint < previousExtrema) {
                    previousExtrema = dataPoint;
                } else if (dataPoint - previousExtrema > reversalThreshold) {
                    rawExtrema.add(previousExtrema);
                    previousExtrema = dataPoint;
                    increasing = true;
                }
            }
        }

        return rawExtrema;
    }

    private void scaleExtrema() {

        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;

        for (Double value : extrema) {
            min = Doubles.min(value, min);
            max = Doubles.max(value, max);
        }

        double span = max - min;

        scaledExtrema.clear();

        for (Double value : extrema) {
            scaledExtrema.add((value - min) / span * .9 + .05);
        }
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.KagiChart, 0, 0);


        @ColorInt int defaultUpColor = ContextCompat.getColor(context, R.color.kagi_default_up_color);
        @ColorInt int upColor = array.getColor(R.styleable.KagiChart_upColor, defaultUpColor);

        @ColorInt int defaultDownColor = ContextCompat.getColor(context, R.color.kagi_default_down_color);
        @ColorInt int downColor = array.getColor(R.styleable.KagiChart_downColor, defaultDownColor);

        float defaultUpWidth = getResources().getDimension(R.dimen.kagi_default_up_width);
        float upWidth = array.getDimension(R.styleable.KagiChart_upWidth, defaultUpWidth);

        float defaultDownWidth = getResources().getDimension(R.dimen.kagi_default_down_width);
        float downWidth = array.getDimension(R.styleable.KagiChart_downWidth, defaultDownWidth);


//        array.recycle();

        upPaint = new Paint();
        upPaint.setColor(upColor);
        upPaint.setStrokeWidth(upWidth);
        upPaint.setStrokeCap(Paint.Cap.ROUND);

        downPaint = new Paint();
        downPaint.setColor(downColor);
        downPaint.setStrokeWidth(downWidth);
        downPaint.setStrokeCap(Paint.Cap.ROUND);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        // Draw First Segment

        Boolean increasing = scaledExtrema.get(1) > scaledExtrema.get(0);
        Paint paint = increasing ? upPaint : downPaint;

        float y1 = (float) ((1 - scaledExtrema.get(0)) * canvas.getHeight());
        float y2 = (float) ((1 - scaledExtrema.get(1)) * canvas.getHeight());

        canvas.drawLine(0, y1, stepSize, y1, paint);
        canvas.drawLine(stepSize, y1, stepSize, y2, paint);
        canvas.drawLine(stepSize, y2, 2 * stepSize, y2, paint);

        // Draw Second Segment

        for (int i = 2; i < scaledExtrema.size() - 1; i++) {

            float startX = i * stepSize;
            float startY = (float) ((1 - scaledExtrema.get(i - 1)) * canvas.getHeight());

            if (increasing && scaledExtrema.get(i - 2) > scaledExtrema.get(i) ||
                    !increasing && scaledExtrema.get(i - 2) < scaledExtrema.get(i)) {
                float intermediateY = (float) ((1 - scaledExtrema.get(i - 2)) * canvas.getHeight());
                canvas.drawLine(startX, startY, startX, intermediateY, paint);
                startY = intermediateY;
                increasing = !increasing;
            }

            float endX = (i + 1) * stepSize;
            float endY = (float) ((1 - scaledExtrema.get(i)) * canvas.getHeight());
            paint = increasing ? upPaint : downPaint;
            canvas.drawLine(startX, startY, startX, endY, paint);
            canvas.drawLine(startX, endY, endX, endY, paint);
        }


    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        scaleExtrema();
    }


}
