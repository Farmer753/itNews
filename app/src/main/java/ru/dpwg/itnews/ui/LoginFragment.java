package ru.dpwg.itnews.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.dpwg.itnews.R;
import ru.dpwg.itnews.di.Di;
import ru.dpwg.itnews.mvp.presenter.LoginPresenter;
import ru.dpwg.itnews.mvp.view.LoginView;
import timber.log.Timber;
import toothpick.Toothpick;

public class LoginFragment extends MvpAppCompatFragment implements LoginView {
    Button buttonLogin;
    Toolbar toolbar;
    EditText emailEditText;
    EditText passwordEditText;
    View progressView;

    @InjectPresenter
    LoginPresenter presenter;

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

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @androidx.annotation.Nullable @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonLogin = view.findViewById(R.id.buttonLogin);
        toolbar = view.findViewById(R.id.toolbar);
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        progressView = view.findViewById(R.id.progressView);

        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.onEmailChange(s.toString());
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.onPasswordChange(s.toString());
            }
        });
        buttonLogin.setOnClickListener(v -> presenter.onLoginClick());
    }

    @Override
    public void enableLoginButton(boolean formValid) {
        buttonLogin.setEnabled(formValid);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgress(boolean show) {
        if (show){
            progressView.setVisibility(View.VISIBLE);
        }
        else {
            progressView.setVisibility(View.GONE);
        }
    }
}
