package ru.dpwg.itnews.mvp.presenter;

import javax.inject.Inject;

import moxy.MvpPresenter;
import ru.dpwg.itnews.mvp.view.LoginView;

public class LoginPresenter extends MvpPresenter<LoginView> {

    @Inject
    public LoginPresenter(){
    }
}
