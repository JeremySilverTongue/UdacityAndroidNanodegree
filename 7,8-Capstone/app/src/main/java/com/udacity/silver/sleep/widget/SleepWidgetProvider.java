package com.udacity.silver.sleep.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;

import com.udacity.silver.sleep.R;
import com.udacity.silver.sleep.data.SleepContract;
import com.udacity.silver.sleep.data.SleepPreferenceUtils;
import com.udacity.silver.sleep.ui.MainActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SleepWidgetProvider extends AppWidgetProvider {

    private static final String SLEEP_BUTTON_TAG = "myOnClickTag";

    public static void updateWidgets(Context context) {
        Intent intent = new Intent(context, SleepWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int ids[] = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, SleepWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        context.sendBroadcast(intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {


        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);


            Intent launchAppIntent = new Intent(context, MainActivity.class);
            PendingIntent launchAppPendingIntent = PendingIntent.getActivity(context, 0, launchAppIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.widget_header, launchAppPendingIntent);


            if (SleepPreferenceUtils.isAsleep(context)) {
                views.setTextViewText(R.id.widget_sleep_button, context.getString(R.string.wake_up));
            } else {
                views.setTextViewText(R.id.widget_sleep_button, context.getString(R.string.sleep_now));
            }

            Intent intent = new Intent(context, getClass());
            intent.setAction(SLEEP_BUTTON_TAG);
            views.setOnClickPendingIntent(R.id.widget_sleep_button, PendingIntent.getBroadcast(context, 0, intent, 0));


            Cursor cursor = SleepContract.getMostRecentNight(context);


            DateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.US);

            if (cursor.moveToFirst()) {
                long rawSleep = cursor.getLong(SleepContract.POSITION_SLEEP);
                long rawWake = cursor.getLong(SleepContract.POSITION_WAKE);
                double rawDuration = cursor.getDouble(SleepContract.POSITION_DURATION);

                String sleep = timeFormat.format(new Date(rawSleep));
                String wake = timeFormat.format(new Date(rawWake));
                String duration = String.format(Locale.getDefault(), "%01.1f", rawDuration);

                views.setTextViewText(R.id.widget_hours, String.format(Locale.getDefault(), context.getString(R.string.duration), duration));
                views.setTextViewText(R.id.widget_times, String.format(Locale.getDefault(), context.getString(R.string.times), sleep, wake));

            } else {
                views.setTextViewText(R.id.widget_hours, "--");
                views.setTextViewText(R.id.widget_times, "--");
            }

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (SLEEP_BUTTON_TAG.equals(intent.getAction())) {
            if (SleepPreferenceUtils.isAsleep(context)) {
                SleepPreferenceUtils.wakeUp(context);
            } else {
                SleepPreferenceUtils.goToSleep(context);
            }
        }
    }
}
