/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.sunshine.wear;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.support.wearable.watchface.WatchFaceStyle;
import android.view.SurfaceHolder;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Digital watch face with seconds. In ambient mode, the seconds aren't displayed. On devices with
 * low-bit ambient mode, the text is drawn without anti-aliasing in ambient mode.
 */
public class SunshineWatchFace extends CanvasWatchFaceService implements DataApi.DataListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {

    }

    private static final Typeface NORMAL_TYPEFACE =
            Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);

    private static final long INTERACTIVE_UPDATE_RATE_MS = TimeUnit.SECONDS.toMillis(10);

    private static final int MSG_UPDATE_TIME = 0;

    @Override
    public Engine onCreateEngine() {
        return new Engine();
    }

    private static class EngineHandler extends Handler {

        private final WeakReference<SunshineWatchFace.Engine> mWeakReference;

        EngineHandler(SunshineWatchFace.Engine reference) {
            mWeakReference = new WeakReference<>(reference);
        }

        @Override
        public void handleMessage(Message msg) {
            SunshineWatchFace.Engine engine = mWeakReference.get();
            if (engine != null) {
                switch (msg.what) {
                    case MSG_UPDATE_TIME:
                        engine.invalidate();
                        break;
                }
            }
        }
    }

    private class Engine extends CanvasWatchFaceService.Engine {

        final Handler mUpdateTimeHandler = new EngineHandler(this);

        boolean mRegisteredTimeZoneReceiver = false;


        Paint backgroundPaint;
        Paint hourPaint;
        Paint minutesPaint;
        Paint datePaint;
        Paint highTempPaint;
        Paint lowTempPaint;

        Bitmap weatherIcon;

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd yyyy", Locale.getDefault());

        boolean mAmbient;
        Calendar mCalendar;

        final BroadcastReceiver mTimeZoneReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mCalendar.setTimeZone(TimeZone.getDefault());
                invalidate();
            }
        };

        float offsetX;
        float offsetY;

        boolean mLowBitAmbient;


        @Override
        public void onCreate(SurfaceHolder holder) {
            super.onCreate(holder);


            setWatchFaceStyle(new WatchFaceStyle.Builder(SunshineWatchFace.this)
                    .setCardPeekMode(WatchFaceStyle.PEEK_MODE_VARIABLE)
                    .setBackgroundVisibility(WatchFaceStyle.BACKGROUND_VISIBILITY_INTERRUPTIVE)
                    .setShowSystemUiTime(false)
                    .build());

            Resources resources = SunshineWatchFace.this.getResources();
            offsetY = resources.getDimension(R.dimen.digital_y_offset);


            backgroundPaint = new Paint();
            backgroundPaint.setColor(resources.getColor(R.color.background));

            hourPaint = createTextPaint(Color.WHITE, 1, 70);

            hourPaint.setTextAlign(Paint.Align.RIGHT);
            hourPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

            minutesPaint = createTextPaint(Color.WHITE, 1, 70);


            datePaint = createTextPaint(Color.WHITE, .5, 30);
            datePaint.setTextAlign(Paint.Align.CENTER);

            weatherIcon = BitmapFactory.decodeResource(getResources(), R.drawable.art_clear);

//            weatherIcon.setWidth(bounds.width()/8);
//            weatherIcon.setHeight(bounds.height()/8);

            mCalendar = Calendar.getInstance();
        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds) {
            if (isInAmbientMode()) {
                canvas.drawColor(Color.BLACK);
            } else {
                canvas.drawRect(0, 0, bounds.width(), bounds.height(), backgroundPaint);
            }


            long now = System.currentTimeMillis();
            mCalendar.setTimeZone(TimeZone.getDefault())
            mCalendar.setTimeInMillis(now);

            String hours = String.format(Locale.getDefault(), "%d", mCalendar.get(Calendar.HOUR));

            String minutes = String.format(Locale.getDefault(), ":%02d", mCalendar.get(Calendar.MINUTE));
            String date = dateFormat.format(new Date());
//            String date = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            canvas.drawText(hours, bounds.width() / 2, bounds.height() / 4, hourPaint);
            canvas.drawText(minutes, bounds.width() / 2, bounds.height() / 4, minutesPaint);

            canvas.drawText(date, bounds.width() / 2, bounds.height() / 2, datePaint);


            canvas.drawBitmap(weatherIcon, bounds.width() / 3, bounds.height() * 3 / 4, null);

            Rect src = new Rect(0, 0, weatherIcon.getWidth() - 1, weatherIcon.getHeight() - 1);
            Rect dest = new Rect(0, 0, 100 - 1, 20 - 1);
            canvas.drawBitmap(weatherIcon, src, dest, null);


//            canvas.drawText(date, offsetX, offsetY + mTextPaint.getTextSize(), mTextPaint);
        }

        @Override
        public void onDestroy() {
            mUpdateTimeHandler.removeMessages(MSG_UPDATE_TIME);
            super.onDestroy();
        }

        private Paint createTextPaint(int textColor, double alpha, float textSize) {
            Paint paint = new Paint();
            paint.setColor(textColor);
            paint.setTypeface(NORMAL_TYPEFACE);
            paint.setAlpha((int) (alpha * 255));
            paint.setAntiAlias(true);
            paint.setTextSize(textSize);
            return paint;
        }

        @Override
        public void onPropertiesChanged(Bundle properties) {
            super.onPropertiesChanged(properties);
            mLowBitAmbient = properties.getBoolean(PROPERTY_LOW_BIT_AMBIENT, false);
        }

        @Override
        public void onTimeTick() {
            super.onTimeTick();
            invalidate();
        }

        @Override
        public void onAmbientModeChanged(boolean inAmbientMode) {
            super.onAmbientModeChanged(inAmbientMode);
            if (mAmbient != inAmbientMode) {
                mAmbient = inAmbientMode;
                if (mLowBitAmbient) {
                    hourPaint.setAntiAlias(!inAmbientMode);
                    minutesPaint.setAntiAlias(!inAmbientMode);
                }
                invalidate();
            }
        }
    }
}
