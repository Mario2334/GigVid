package com.android.gigvid;

import android.app.Application;
import android.content.Context;

import timber.log.Timber;

public class GigVidApplication extends Application {

    private static Context appCtx;
    @Override
    public void onCreate() {
        super.onCreate();
        appCtx = getApplicationContext();
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

    public static Context getGigVidAppContext(){
        return appCtx;
    }
}
