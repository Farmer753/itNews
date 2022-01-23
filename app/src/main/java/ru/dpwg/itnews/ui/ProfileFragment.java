package ru.dpwg.itnews.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.dpwg.itnews.R;
import ru.dpwg.itnews.di.Di;
import ru.dpwg.itnews.domain.user.NwUser;
import ru.dpwg.itnews.mvp.presenter.ProfilePresenter;
import ru.dpwg.itnews.mvp.view.ProfileView;
import toothpick.Toothpick;

public class ProfileFragment extends MvpAppCompatFragment implements ProfileView {

    Button logout;
    View progressView;
    TextView emailTextView;
    TextView nameTextView;
    Button buttonRetry;


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
        logout.setOnClickListener(v -> profilePresenter.onLogoutClick());
        emailTextView = view.findViewById(R.id.emailTextView);
        nameTextView = view.findViewById(R.id.nameTextView);
        progressView = view.findViewById(R.id.progressView);
        buttonRetry = view.findViewById(R.id.buttonRetry);
        buttonRetry.setOnClickListener(v -> profilePresenter.loadUser());

    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            progressView.setVisibility(View.VISIBLE);
        } else {
            progressView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showUser(NwUser nwUser) {
        emailTextView.setText(nwUser.email);
        nameTextView.setText(nwUser.fullName);
    }

    @Override
    public void showButtonRetry(boolean show) {
        if (show) {
            buttonRetry.setVisibility(View.VISIBLE);
        } else {
            buttonRetry.setVisibility(View.GONE);
        }
    }


}
