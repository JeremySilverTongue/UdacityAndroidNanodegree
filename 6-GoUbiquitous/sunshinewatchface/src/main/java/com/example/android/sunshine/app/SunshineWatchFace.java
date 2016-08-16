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

package com.example.android.sunshine.app;

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
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.support.wearable.watchface.WatchFaceStyle;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static android.support.wearable.watchface.WatchFaceStyle.PROTECT_WHOLE_SCREEN;

/**
 * Digital watch face with seconds. In ambient mode, the seconds aren't displayed. On devices with
 * low-bit ambient mode, the text is drawn without anti-aliasing in ambient mode.
 */
public class SunshineWatchFace extends CanvasWatchFaceService {


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


        private static final String WEATHER_KEY = "weather";
        private static final String HIGH_KEY = "high";
        private static final String LOW_KEY = "low";

        public final String TAG = Engine.class.getSimpleName();

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
        Calendar calendar;

        final BroadcastReceiver mTimeZoneReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                calendar.setTimeZone(TimeZone.getDefault());
                invalidate();
            }
        };

        float offsetX;
        float offsetY;

        int rawHighTemp = Integer.MAX_VALUE;
        int rawLowTemp = Integer.MIN_VALUE;
        int weatherType = -1;

        boolean mLowBitAmbient;

        GoogleApiClient client;


        private void checkWeather() {
            PendingResult<DataItemBuffer> results = Wearable.DataApi.getDataItems(client);
            results.setResultCallback(new ResultCallbacks<DataItemBuffer>() {
                @Override
                public void onSuccess(@NonNull DataItemBuffer dataItems) {
                    for (DataItem item : dataItems) {
                        if (item.getUri().getLastPathSegment().equals("weather")) {

                            DataMapItem dataMapItem = DataMapItem.fromDataItem(item);

                            DataMap dataMap = dataMapItem.getDataMap();

                            rawHighTemp = dataMap.getInt(HIGH_KEY);
                            rawLowTemp = dataMap.getInt(LOW_KEY);
                            weatherType = dataMap.getInt(WEATHER_KEY);
                            weatherIcon = BitmapFactory.decodeResource(getResources(), Utils.getIconResourceForWeatherCondition(weatherType));
                        }
                        Log.d(TAG, item.getUri().toString());
                    }

                    invalidate();
                }

                @Override
                public void onFailure(@NonNull Status status) {
                    Log.d(TAG, "Failed to get any data items");
                }
            });

        }

        @Override
        public void onCreate(SurfaceHolder holder) {
            super.onCreate(holder);

            client = new GoogleApiClient.Builder(getApplicationContext())
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle connectionHint) {
                            checkWeather();
                        }

                        @Override
                        public void onConnectionSuspended(int cause) {
                            Log.d(TAG, "onConnectionSuspended: " + cause);
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult result) {
                            Log.d(TAG, "onConnectionFailed: " + result);
                        }
                    }).addApi(Wearable.API).build();

            client.connect();


            setWatchFaceStyle(new WatchFaceStyle.Builder(SunshineWatchFace.this)
                    .setCardPeekMode(WatchFaceStyle.PEEK_MODE_VARIABLE)
                    .setBackgroundVisibility(WatchFaceStyle.BACKGROUND_VISIBILITY_INTERRUPTIVE)
                    .setShowSystemUiTime(false).
                            setStatusBarGravity(Gravity.START | Gravity.TOP)
                    .setHotwordIndicatorGravity(Gravity.START | Gravity.BOTTOM)
                    .setViewProtectionMode(PROTECT_WHOLE_SCREEN)
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

            highTempPaint = createTextPaint(Color.WHITE, 1, 30);
            highTempPaint.setTextAlign(Paint.Align.RIGHT);

            lowTempPaint = createTextPaint(Color.WHITE, 1, 30);
            lowTempPaint.setTextAlign(Paint.Align.LEFT);

            weatherIcon = BitmapFactory.decodeResource(getResources(), R.drawable.art_clear);

            calendar = Calendar.getInstance();


        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds) {


            if (isInAmbientMode()) {
                canvas.drawColor(Color.BLACK);
            } else {
                canvas.drawRect(0, 0, bounds.width(), bounds.height(), backgroundPaint);
            }


            long now = System.currentTimeMillis();
            calendar.setTimeZone(TimeZone.getDefault());
            calendar.setTimeInMillis(now);

            Locale loc = Locale.getDefault();

            String hours = String.format(loc, "%d", calendar.get(Calendar.HOUR));
            String minutes = String.format(loc, ":%02d", calendar.get(Calendar.MINUTE));
            String date = dateFormat.format(new Date());


            String highTemp = "--";
            if (rawHighTemp != Integer.MAX_VALUE) {
                highTemp = String.format(loc, "%d\u00b0 ", rawHighTemp);
            }


            String lowTemp = "--";

            if (rawLowTemp != Integer.MIN_VALUE) {
                lowTemp = String.format(loc, "%d\u00b0", rawLowTemp);
            }


            canvas.drawText(hours, bounds.width() / 2, bounds.height() * 3 / 8, hourPaint);
            canvas.drawText(minutes, bounds.width() / 2, bounds.height() * 3 / 8, minutesPaint);

            canvas.drawText(date, bounds.width() / 2, bounds.height() / 2, datePaint);

            canvas.drawText(highTemp, bounds.width() * 3 / 4, bounds.height() * 3 / 4, highTempPaint);
            canvas.drawText(lowTemp, bounds.width() * 3 / 4, bounds.height() * 3 / 4, lowTempPaint);
            canvas.drawBitmap(weatherIcon, bounds.width() * 2 / 8, bounds.height() * 5 / 8, null);

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
            checkWeather();
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
