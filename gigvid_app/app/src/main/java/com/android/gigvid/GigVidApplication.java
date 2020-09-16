package com.android.gigvid;

import android.app.Application;

import com.android.gigvid.BuildConfig;

import timber.log.Timber;

public class GigVidApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initializeTimber();
    }

    /**
     * Method: Initialize Timber Library
     */
    private void initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
