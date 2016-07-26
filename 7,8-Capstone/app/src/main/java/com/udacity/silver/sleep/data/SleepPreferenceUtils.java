package com.udacity.silver.sleep.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Calendar;


public final class SleepPreferenceUtils {


    private final static String SLEEP_KEY = "sleep";


    public void goToSleep(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(SLEEP_KEY, Calendar.getInstance().getTimeInMillis());
        editor.apply();
    }

    public long wakeUp(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        long sleepTime = sharedPreferences.getLong(SLEEP_KEY, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(SLEEP_KEY);
        editor.apply();
        return sleepTime;
    }


}
