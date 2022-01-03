package ru.dpwg.itnews.mvp.presenter;

import javax.inject.Inject;

import moxy.MvpPresenter;
import ru.dpwg.itnews.mvp.view.LoginView;
import ru.dpwg.itnews.mvp.view.ProfileView;

public class ProfilePresenter extends MvpPresenter<ProfileView> {

    @Inject
    public ProfilePresenter(){
    }
}
