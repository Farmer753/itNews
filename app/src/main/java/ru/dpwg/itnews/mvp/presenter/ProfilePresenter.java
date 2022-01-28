package ru.dpwg.itnews.mvp.presenter;

import com.github.terrakok.cicerone.Router;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import moxy.MvpPresenter;
import ru.dpwg.itnews.Screens;
import ru.dpwg.itnews.domain.SessionRepository;
import ru.dpwg.itnews.domain.user.UserRepository;
import ru.dpwg.itnews.mvp.view.ProfileView;
import timber.log.Timber;

public class ProfilePresenter extends MvpPresenter<ProfileView> {

    private SessionRepository sessionRepository;
    private Router router;
    private UserRepository userRepository;

    @Inject
    public ProfilePresenter(SessionRepository sessionRepository, Router router, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.router = router;
        this.userRepository = userRepository;
    }

    public void onLogoutClick() {
        sessionRepository.saveRefreshToken(null);
        sessionRepository.saveAccessToken(null);
        router.exit();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadUser();

    }

    public void loadUser() {
        userRepository
                .loadUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    getViewState().showProgress(true);
                    getViewState().showButtonRetry(false);
                })
                .doOnEvent((tokenResponse, throwable) -> getViewState().showProgress(false))
                .subscribe(
                        nwUser -> {
                            Timber.d(nwUser.email);
                            getViewState().showUser(nwUser);
                        },
                        error -> {
                            Timber.e(error);
                            getViewState().showMessage(error.getMessage());
                            getViewState().showButtonRetry(true);
                        }
                );


    }

    public void onBackClick() {
        router.exit();
    }
}
