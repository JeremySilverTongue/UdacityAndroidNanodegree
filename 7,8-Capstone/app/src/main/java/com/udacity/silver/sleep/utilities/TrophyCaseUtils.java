package com.udacity.silver.sleep.utilities;

import android.content.Context;

import com.udacity.silver.sleep.R;


final class TrophyCaseUtils {


    private TrophyCaseUtils() {
    }

    public static void updateTrophyCaseContents(Context context) {

        context.getResources().getStringArray(R.array.trophy_case_keys);


    }


}
