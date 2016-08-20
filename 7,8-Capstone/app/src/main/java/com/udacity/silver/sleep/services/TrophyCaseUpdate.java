package com.udacity.silver.sleep.services;

import android.app.IntentService;
import android.content.Intent;

import com.udacity.silver.sleep.R;
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

        List<String> keys = Arrays.asList(getResources().getStringArray(R.array.trophy_case_keys));

        Set<String> alreadyAchieved = SleepPreferenceUtils.getAchievements(this);


        List<String> notYetAchieved = new ArrayList<>();

        for (String key : keys) {
            if (!alreadyAchieved.contains(key)) {
                notYetAchieved.add(key);
            }
        }


        for (String key : notYetAchieved) {

            boolean achieved = false;

            achieved = checkFirstNight(key, achieved);
            achieved = checkEightHours(key, achieved);
            achieved = checkFirstWeek(key, achieved);

            if (achieved) {
                SleepPreferenceUtils.addAchievement(this, key);
            }
        }


    }

    private boolean checkFirstNight(String key, boolean achieved) {
        if (key.equals(getString(R.string.trophy_first_night_key))) {
            return true;
        }
        return achieved;
    }

    private boolean checkEightHours(String key, boolean achieved) {
        if (key.equals(getString(R.string.trophy_eight_hours_key))) {
            return false;
        }
        return achieved;
    }

    private boolean checkFirstWeek(String key, boolean achieved) {
        if (key.equals(getString(R.string.trophy_first_week_key))) {
            return true;
        }
        return achieved;
    }


}
