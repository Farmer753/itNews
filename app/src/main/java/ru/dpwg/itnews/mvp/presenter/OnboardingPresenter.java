package ru.dpwg.itnews.mvp.presenter;

import android.content.SharedPreferences;

import javax.inject.Inject;

import moxy.MvpPresenter;
import ru.dpwg.itnews.mvp.view.MainActivityView;
import ru.dpwg.itnews.mvp.view.OnboardingView;
import timber.log.Timber;

public class OnboardingPresenter extends MvpPresenter<OnboardingView> {


    public void onNextButtonClick() {
        Timber.d("onNextButtonClick");
    }
}
