package com.udacity.silver.sleep.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Calendar;


public final class SleepPreferenceUtils {


    private final static String SLEEP_KEY = "sleep";


    private SleepPreferenceUtils() {
    }

    public static void goToSleep(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(SLEEP_KEY, Calendar.getInstance().getTimeInMillis());
        editor.apply();
    }

    public static boolean isAsleep(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.contains(SLEEP_KEY);
    }

    public static void cancelSleep(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(SLEEP_KEY);
        editor.apply();
    }

    public static long getSleepTime(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getLong(SLEEP_KEY, 0);

    }

    public static void wakeUp(Context context) {
        long sleepTime = getSleepTime(context);
        cancelSleep(context);

        if (sleepTime != 0) {
            long wakeTime = Calendar.getInstance().getTimeInMillis();
            SleepContract.addNight(context, sleepTime, wakeTime);
        }


    }


}
