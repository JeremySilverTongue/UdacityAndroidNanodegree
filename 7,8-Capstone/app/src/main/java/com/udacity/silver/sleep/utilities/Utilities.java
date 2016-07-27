package com.udacity.silver.sleep.utilities;


public final class Utilities {


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

}
