package ru.dpwg.itnews;

import android.app.Application;

import timber.log.Timber;

public class ItNewsApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
