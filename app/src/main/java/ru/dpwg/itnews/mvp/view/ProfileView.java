package ru.dpwg.itnews.mvp.view;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEndSingle;
import ru.dpwg.itnews.domain.user.NwUser;

public interface ProfileView extends MvpView {

    @AddToEndSingle
    void showProgress(boolean show);

    @AddToEndSingle
    void showMessage(String message);

    @AddToEndSingle
    void showUser(NwUser nwUser);

    @AddToEndSingle
    void showButtonRetry(boolean show);
}
