package ru.dpwg.itnews.mvp.presenter;

import com.github.terrakok.cicerone.Router;

import javax.inject.Inject;

import moxy.MvpPresenter;
import ru.dpwg.itnews.domain.SessionRepository;
import ru.dpwg.itnews.mvp.view.ProfileView;

public class ProfilePresenter extends MvpPresenter<ProfileView> {

    private SessionRepository sessionRepository;
    private Router router;

    @Inject
    public ProfilePresenter(SessionRepository sessionRepository, Router router) {
        this.sessionRepository = sessionRepository;
        this.router = router;
    }

    public void onLogoutClick() {
        sessionRepository.saveRefreshToken(null);
        sessionRepository.saveAccessToken(null);
        router.exit();
    }

}
