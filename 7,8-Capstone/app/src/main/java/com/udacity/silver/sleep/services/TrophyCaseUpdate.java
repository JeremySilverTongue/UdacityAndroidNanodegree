package com.udacity.silver.sleep.services;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.udacity.silver.sleep.R;
import com.udacity.silver.sleep.data.SleepContract;
import com.udacity.silver.sleep.data.SleepPreferenceUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class TrophyCaseUpdate extends IntentService {

    private static final String SERVICE_NAME = "Trophy Case Update";

    public TrophyCaseUpdate() {
        super(SERVICE_NAME);

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        List<String> keys = Arrays.asList(getResources().getStringArray(R.array.trophy_case_keys));

        Set<String> alreadyAchieved = SleepPreferenceUtils.getAchievements(this);


        List<String> notYetAchieved = new ArrayList<>();

        for (String key : keys) {
            if (!alreadyAchieved.contains(key)) {
                notYetAchieved.add(key);
            }
        }

        for (String key : notYetAchieved) {


            boolean achieved;

            achieved = checkFirstNight(key, false);
            achieved = checkEightHours(key, achieved);
            achieved = checkFirstWeek(key, achieved);

            if (achieved) {

                SleepPreferenceUtils.addAchievement(this, key);


                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ACHIEVEMENT_ID, key);
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.UNLOCK_ACHIEVEMENT, bundle);
            }
        }


    }

    private boolean checkFirstNight(String key, boolean achieved) {
        if (key.equals(getString(R.string.trophy_first_night_key))) {
            Cursor cursor = SleepContract.getMostRecentNight(this);
            return cursor.moveToFirst();
        }
        return achieved;
    }

    private boolean checkEightHours(String key, boolean achieved) {
        if (key.equals(getString(R.string.trophy_eight_hours_key))) {

            Cursor cursor = SleepContract.getAllNights(this);
            if (cursor.moveToFirst()) {
                while (cursor.moveToNext()) {
                    if (cursor.getFloat(SleepContract.POSITION_DURATION) >= 7.95) {
                        return true;
                    }
                }
            }

            return false;
        }
        return achieved;
    }

    private boolean checkFirstWeek(String key, boolean achieved) {
        if (key.equals(getString(R.string.trophy_first_week_key))) {
            Cursor cursor = SleepContract.getAllNights(this);
            if (cursor.moveToFirst()) {

                int day = cursor.getInt(SleepContract.POSITION_DAY);
                int streak = 1;


                while (cursor.moveToNext()) {
                    int newDay = cursor.getInt(SleepContract.POSITION_DAY);
                    if (newDay == day - 1) {
                        streak++;
                    } else {
                        streak = 0;
                    }

                    if (streak >= 7) {
                        return true;
                    }

                    day = newDay;


                }
            }


            return false;
        }
        return achieved;
    }


}
