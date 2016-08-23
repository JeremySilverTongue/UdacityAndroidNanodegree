package com.udacity.silver.sleep.utilities;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.udacity.silver.sleep.R;
import com.udacity.silver.sleep.data.SleepContract;
import com.udacity.silver.sleep.services.WakeUpService;

import java.util.Calendar;

public final class Utilities {

    public static final int NOTIFICATION_ID = 1234;

    private final static long MILLIS_PER_HOUR = 1000 * 60 * 60;
    private final static long MILLIS_PER_DAY = MILLIS_PER_HOUR * 24;

    private Utilities() {
    }

    public static long normalizedDay(long millis) {

        return millis / MILLIS_PER_DAY;
    }

    public static double sleepHours(long sleep, long wake) {
        return (float) (wake - sleep) / MILLIS_PER_HOUR;
    }

    public static void cancelNotification(Context context) {
        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        manager.cancel(NOTIFICATION_ID);
    }


    public static void createNotification(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("You're sleeping!");
        builder.setSmallIcon(R.drawable.ic_stat_zzz);
        builder.setOngoing(true);

        Intent wakeUpIntent = new Intent(context, WakeUpService.class);

        PendingIntent pendingIntent = PendingIntent.getService(
                context,
                0,
                wakeUpIntent,
                PendingIntent.FLAG_ONE_SHOT
        );

        builder.setContentIntent(pendingIntent);


        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        manager.notify(NOTIFICATION_ID, builder.build());

    }

    public static void addFakeData(Context context) {

        Calendar sleepTime = Calendar.getInstance();
        Calendar wakeTime = Calendar.getInstance();


        sleepTime.set(2016, 7, 18, 23, 11);
        wakeTime.set(2016, 7, 19, 7, 11);
        SleepContract.addNight(context, sleepTime.getTimeInMillis(), wakeTime.getTimeInMillis());

        sleepTime.set(2016, 7, 19, 23, 11);
        wakeTime.set(2016, 7, 20, 6, 11);
        SleepContract.addNight(context, sleepTime.getTimeInMillis(), wakeTime.getTimeInMillis());

        sleepTime.set(2016, 7, 20, 23, 11);
        wakeTime.set(2016, 7, 21, 5, 11);
        SleepContract.addNight(context, sleepTime.getTimeInMillis(), wakeTime.getTimeInMillis());

        sleepTime.set(2016, 7, 22, 1, 11);
        wakeTime.set(2016, 7, 22, 7, 11);
        SleepContract.addNight(context, sleepTime.getTimeInMillis(), wakeTime.getTimeInMillis());
    }

}
