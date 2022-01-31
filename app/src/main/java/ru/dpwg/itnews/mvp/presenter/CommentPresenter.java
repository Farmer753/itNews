package ru.dpwg.itnews.mvp.presenter;

import com.github.terrakok.cicerone.Router;

import javax.inject.Inject;

import moxy.MvpPresenter;
import ru.dpwg.itnews.Screens;
import ru.dpwg.itnews.domain.SessionRepository;
import ru.dpwg.itnews.mvp.view.CommentView;
import timber.log.Timber;

public class CommentPresenter extends MvpPresenter<CommentView> {

    private Router router;
    private SessionRepository sessionRepository;
    private int id;
    private String comment;

    public void setId(int id) {
        this.id = id;
    }

    @Inject
    public CommentPresenter(Router router, SessionRepository sessionRepository) {
        this.router = router;
        this.sessionRepository = sessionRepository;
    }

    public void onBackClick() {
        router.exit();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Timber.d(id + "");
        sessionRepository.loginState().subscribe(isLogined -> {
            getViewState().showButtonLogin(!isLogined);
            getViewState().showInput(isLogined);
        });
    }

    public void profileClick() {
        if (sessionRepository.getAccessToken() == null) {
            router.navigateTo(new Screens.LoginScreen());
        } else {
            router.navigateTo(new Screens.ProfileScreen());
        }
    }

    public void onLoginClick() {
        router.navigateTo(new Screens.LoginScreen());
    }

    public void onCommentChange(String comment) {
        this.comment = comment;
    }

    public void sendClick() {
        Timber.d("send comment " + comment);
    }
}
