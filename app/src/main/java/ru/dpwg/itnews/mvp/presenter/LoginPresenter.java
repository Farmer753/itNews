package ru.dpwg.itnews.mvp.presenter;

import android.text.TextUtils;
import android.util.Patterns;

import javax.inject.Inject;

import moxy.MvpPresenter;
import ru.dpwg.itnews.mvp.view.LoginView;
import timber.log.Timber;

public class LoginPresenter extends MvpPresenter<LoginView> {
    String password;
    String email;

    @Inject
    public LoginPresenter() {
    }

    public void onPasswordChange(String password) {
        this.password = password;
        checkForm();
    }

    private void checkForm() {
        boolean formValid = !TextUtils.isEmpty(email)
                && Patterns.EMAIL_ADDRESS.matcher(email).matches()
                && !TextUtils.isEmpty(password)
                && password.length()>=8;
        getViewState().enableLoginButton(formValid);
        Timber.d("password %s, email %s",password ,email);
    }

    public void onEmailChange(String email) {
        this.email = email;
        checkForm();
    }
}
