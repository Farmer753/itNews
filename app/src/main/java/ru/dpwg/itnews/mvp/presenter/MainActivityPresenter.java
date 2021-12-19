package ru.dpwg.itnews.mvp.presenter;

import android.content.SharedPreferences;

import com.github.terrakok.cicerone.Router;

import javax.inject.Inject;

import moxy.MvpPresenter;
import ru.dpwg.itnews.Screens;
import ru.dpwg.itnews.mvp.view.MainActivityView;
import timber.log.Timber;

public class MainActivityPresenter extends MvpPresenter<MainActivityView> {
    private SharedPreferences sharedPreferences;

    private Router router;

    @Inject
    public MainActivityPresenter(SharedPreferences sharedPreferences, Router router) {
        this.sharedPreferences = sharedPreferences;
        this.router = router;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        boolean isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true);
        if (isFirstLaunch) {
            Timber.d("is First Launch");
            sharedPreferences.edit().putBoolean("isFirstLaunch", false).apply();
            router.newRootScreen(new Screens.OnboardingScreen());
        } else {
            Timber.d("is not First Launch");
        }
    }
}
