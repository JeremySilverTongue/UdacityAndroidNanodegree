package com.udacity.silver.sleep;

import android.app.Application;

import timber.log.Timber;

public class WinksApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        Timber.uprootAll();
        Timber.plant(new Timber.DebugTree());


        Timber.d("This thing on?");
    }
}
