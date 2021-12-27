package ru.dpwg.itnews.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.dpwg.itnews.R;
import ru.dpwg.itnews.di.Di;
import ru.dpwg.itnews.mvp.presenter.LoginPresenter;
import ru.dpwg.itnews.mvp.view.LoginView;
import toothpick.Toothpick;

public class LoginFragment extends MvpAppCompatFragment implements LoginView {
    @InjectPresenter
    LoginPresenter loginPresenter;

    @ProvidePresenter
    LoginPresenter getLoginPresenter() {
        return Toothpick.openScope(Di.APP_SCOPE).getInstance(LoginPresenter.class);
    }

    @Nullable
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }
}
