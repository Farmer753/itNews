package ru.dpwg.itnews.mvp.presenter;

import android.widget.Button;

import javax.inject.Inject;

import moxy.MvpPresenter;
import ru.dpwg.itnews.mvp.view.LoginView;
import ru.dpwg.itnews.mvp.view.ProfileView;
import timber.log.Timber;

public class ProfilePresenter extends MvpPresenter<ProfileView> {
    Button logout;

    @Inject
    public ProfilePresenter() {
    }

    public void onLogoutClick() {

    }

}
