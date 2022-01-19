package ru.dpwg.itnews.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;

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

    Button logout;

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

    @Override
    public void onViewCreated(
            @NonNull @NotNull View view,
            @androidx.annotation.Nullable @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);
        logout = view.findViewById(R.id.buttonLogout);

    }

    @Override
    public void enableLogoutButton(boolean logout) {

    }
}
