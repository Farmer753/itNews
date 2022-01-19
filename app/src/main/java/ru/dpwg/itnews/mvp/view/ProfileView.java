package ru.dpwg.itnews.mvp.view;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEndSingle;

public interface ProfileView extends MvpView {

    @AddToEndSingle
    void enableLogoutButton(boolean logout);
}
