package com.udacity.silver.sleep.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.udacity.silver.sleep.services.TrophyCaseUpdate;
import com.udacity.silver.sleep.utilities.Utilities;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;


public final class SleepPreferenceUtils {


    public final static String SLEEP_KEY = "sleep";
    public final static String ACHIEVEMENT_KEY = "cheevos";


    private SleepPreferenceUtils() {
    }

    public static void goToSleep(Context context) {
        Utilities.createNotification(context);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(SLEEP_KEY, Calendar.getInstance().getTimeInMillis());
        editor.apply();
    }

    public static boolean isAsleep(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.contains(SLEEP_KEY);
    }

    private static void cancelSleep(Context context) {
        Utilities.cancelNotification(context);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(SLEEP_KEY);
        editor.apply();
    }

    private static long getSleepTime(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getLong(SLEEP_KEY, 0);

    }

    public static void wakeUp(Context context) {
        long sleepTime = getSleepTime(context);
        cancelSleep(context);

        if (sleepTime != 0) {
            long wakeTime = Calendar.getInstance().getTimeInMillis();
            SleepContract.addNight(context, sleepTime, wakeTime);
            context.startService(new Intent(context, TrophyCaseUpdate.class));
        }
    }

    public static Set<String> getAchievements(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getStringSet(ACHIEVEMENT_KEY, new HashSet<String>());
    }

    public static void clearAchievements(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(ACHIEVEMENT_KEY);
        editor.apply();
    }

    public static void addAchievement(Context context, String achievement) {

        Set<String> achievements = getAchievements(context);
        achievements.add(achievement);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(ACHIEVEMENT_KEY, achievements);
        editor.apply();
    }


}
