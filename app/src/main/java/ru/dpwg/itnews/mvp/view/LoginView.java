package ru.dpwg.itnews.mvp.view;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEndSingle;

public interface LoginView extends MvpView {

    @AddToEndSingle
    void enableLoginButton(boolean formValid);
}
