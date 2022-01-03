package ru.dpwg.itnews.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.dpwg.itnews.R;
import ru.dpwg.itnews.di.Di;
import ru.dpwg.itnews.mvp.presenter.LoginPresenter;
import ru.dpwg.itnews.mvp.presenter.ProfilePresenter;
import ru.dpwg.itnews.mvp.view.LoginView;
import ru.dpwg.itnews.mvp.view.ProfileView;
import toothpick.Toothpick;

public class ProfileFragment extends MvpAppCompatFragment implements ProfileView {
    @InjectPresenter
    ProfilePresenter profilePresenter;

    @ProvidePresenter
    ProfilePresenter getProfilePresenter() {
        return Toothpick.openScope(Di.APP_SCOPE).getInstance(ProfilePresenter.class);
    }

    @Nullable
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}
