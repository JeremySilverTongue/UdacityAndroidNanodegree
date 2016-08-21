package com.udacity.silver.sleep.utilities;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.udacity.silver.sleep.R;
import com.udacity.silver.sleep.services.WakeUpService;

public final class Utilities {

    public static final int NOTIFICATION_ID = 1234;

    private final static long MILLIS_PER_HOUR = 1000 * 60 * 60;
    private final static long MILLIS_PER_DAY = MILLIS_PER_HOUR * 24;

    private Utilities() {
    }

    public static long normalizedDay(long millis) {

        return millis / MILLIS_PER_DAY * MILLIS_PER_DAY;
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
}
