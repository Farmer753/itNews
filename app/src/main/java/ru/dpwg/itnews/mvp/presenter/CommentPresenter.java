package ru.dpwg.itnews.mvp.presenter;

import com.github.terrakok.cicerone.Router;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import moxy.MvpPresenter;
import ru.dpwg.itnews.Screens;
import ru.dpwg.itnews.domain.CommentRepository;
import ru.dpwg.itnews.domain.SessionRepository;
import ru.dpwg.itnews.mvp.view.CommentView;
import timber.log.Timber;

public class CommentPresenter extends MvpPresenter<CommentView> {

    private Router router;
    private SessionRepository sessionRepository;
    private int id;
    private String commentText;
    private CommentRepository commentRepository;

    public void setId(int id) {
        this.id = id;
    }

    @Inject
    public CommentPresenter(Router router, SessionRepository sessionRepository, CommentRepository commentRepository) {
        this.router = router;
        this.sessionRepository = sessionRepository;
        this.commentRepository = commentRepository;
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
        this.commentText = comment;
    }

    public void sendClick() {
        Timber.d("send comment " + commentText);
        commentRepository.add(id, commentText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showProgress(true))
                .doOnEvent((tokenResponse, throwable) -> getViewState().showProgress(false))
                .subscribe(
                        nwComment -> {
                            Timber.d("Текст комментария отправлен");
                            getViewState().showMessage("комментарий отправлен");
                        },
                        error -> {
                            Timber.e(error);
                            getViewState().showMessage(error.getMessage());
                        }
                );
    }
}
