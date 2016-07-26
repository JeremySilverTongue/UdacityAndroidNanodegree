package com.udacity.silver.sleep.data;

import android.net.Uri;
import android.provider.BaseColumns;


public final class SleepContract implements BaseColumns {

    public static final String AUTHORITY = "com.udacity.silver.sleep";

    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_SLEEP = "sleep";

    public static final Uri SLEEP_URI = BASE_URI.buildUpon().appendPath(PATH_SLEEP).build();

    public static final String PATH_SLEEP_FOR_DATE = "sleep/#";

    public static final String TABLE_NAME = "quotes";

    public static final String COLUMN_DAY = "day";
    public static final String COLUMN_SLEEP = "sleep";
    public static final String COLUMN_WAKE = "wake";
    public static final String COLUMN_DURATION = "duration";


    public static final int POSITION_ID = 0;
    public static final int POSITION_DAY = 1;
    public static final int POSITION_SLEEP = 2;
    public static final int POSITION_WAKE = 3;
    public static final int POSITION_DURATION = 4;

    public static final String[] COLUMNS = {
            _ID,
            COLUMN_DAY,
            COLUMN_SLEEP,
            COLUMN_WAKE,
            COLUMN_DURATION
    };

    public static Uri makeUriForDay(long date) {
        return SLEEP_URI.buildUpon().appendPath(Long.toString(date)).build();
    }

    public static String getDayFromUri(Uri uri) {
        return uri.getLastPathSegment();
    }


}
