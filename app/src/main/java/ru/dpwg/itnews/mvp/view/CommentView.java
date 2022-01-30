package ru.dpwg.itnews.mvp.view;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEndSingle;

public interface CommentView extends MvpView{

    @AddToEndSingle
    void showButtonLogin(boolean show);

    @AddToEndSingle
    void showProgress(boolean show);

    @AddToEndSingle
    void enableSendButton(boolean enable);

    @AddToEndSingle
    void showMessage(String message);

    @AddToEndSingle
    void enableInput(boolean enable);
}
