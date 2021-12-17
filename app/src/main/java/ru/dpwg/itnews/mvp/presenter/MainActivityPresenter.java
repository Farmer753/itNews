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

    public void test(){
        Timber.d("работает");
    }
}
