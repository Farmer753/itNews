package ru.dpwg.itnews.di.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import toothpick.config.Module;

public class StorageModule extends Module {

    public StorageModule(Context context) {
        bind(SharedPreferences.class)
                .toInstance(PreferenceManager.getDefaultSharedPreferences(context));
    }
}
