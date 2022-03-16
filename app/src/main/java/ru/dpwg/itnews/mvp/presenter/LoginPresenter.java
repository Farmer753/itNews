package ru.dpwg.itnews.mvp.presenter;

import android.text.TextUtils;
import android.util.Patterns;

import com.github.terrakok.cicerone.Router;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import moxy.MvpPresenter;
import ru.dpwg.itnews.Screens;
import ru.dpwg.itnews.domain.session.SessionRepository;
import ru.dpwg.itnews.mvp.view.LoginView;
import timber.log.Timber;

public class LoginPresenter extends MvpPresenter<LoginView> {
    String password;
    String email;
    private SessionRepository sessionRepository;
    private Router router;


    @Inject
    public LoginPresenter(SessionRepository sessionRepository, Router router) {
        this.sessionRepository = sessionRepository;
        this.router = router;
    }

    public void onPasswordChange(String password) {
        this.password = password;
        checkForm();
    }

    private void checkForm() {
        boolean formValid = !TextUtils.isEmpty(email)
                && Patterns.EMAIL_ADDRESS.matcher(email).matches()
                && !TextUtils.isEmpty(password)
                && password.length() >= 8;
        getViewState().enableLoginButton(formValid);
        Timber.d("password %s, email %s", password, email);
    }

    public void onEmailChange(String email) {
        this.email = email;
        checkForm();
    }

    public void onLoginClick() {
        Timber.d("Кнопка нажата");
        sessionRepository
                .login(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showProgress(true))
                .doOnEvent((tokenResponse, throwable) -> getViewState().showProgress(false))
                .subscribe(
                        tokenResponse -> {
                            Timber.d("tokenResponse.accessToken %s", tokenResponse.accessToken);
                            sessionRepository.saveAccessToken(tokenResponse.accessToken);
                            sessionRepository.saveRefreshToken(tokenResponse.refreshToken);
                            router.replaceScreen(new Screens.ProfileScreen());
                        },
                        error -> {
                            Timber.e(error);
                            getViewState().showMessage(error.getMessage());
                        }
                );

    }

    public void onBackClick() {
        router.exit();
    }
}
