package com.example.android.sunshine.wear;


public class WatchWeatherModel {

    public final int high;
    public final int low;
    public final int conditionDrawable;

    public WatchWeatherModel(int high, int low, int conditionDrawable) {
        this.high = high;
        this.low = low;
        this.conditionDrawable = conditionDrawable;
    }
}
