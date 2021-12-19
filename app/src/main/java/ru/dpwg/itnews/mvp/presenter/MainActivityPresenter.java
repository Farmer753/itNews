package ru.dpwg.itnews.mvp.presenter;

import android.content.SharedPreferences;

import javax.inject.Inject;

import moxy.MvpPresenter;
import ru.dpwg.itnews.mvp.view.MainActivityView;
import timber.log.Timber;

public class MainActivityPresenter extends MvpPresenter<MainActivityView> {
    private SharedPreferences sharedPreferences;

    @Inject
    public MainActivityPresenter(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        boolean isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true);
        if (isFirstLaunch) {
            Timber.d("is First Launch");
            sharedPreferences.edit().putBoolean("isFirstLaunch", false).apply();
        } else {
            Timber.d("is not First Launch");
        }
    }
}
