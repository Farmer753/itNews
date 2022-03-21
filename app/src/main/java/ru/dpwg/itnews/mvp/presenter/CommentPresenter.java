package ru.dpwg.itnews.mvp.presenter;

import com.github.terrakok.cicerone.Router;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import moxy.MvpPresenter;
import ru.dpwg.itnews.Screens;
import ru.dpwg.itnews.domain.comment.CommentRepository;
import ru.dpwg.itnews.domain.comment.NwComment;
import ru.dpwg.itnews.domain.session.SessionRepository;
import ru.dpwg.itnews.domain.user.NwAuthority;
import ru.dpwg.itnews.domain.user.UserRepository;
import ru.dpwg.itnews.mvp.view.CommentView;
import timber.log.Timber;

public class CommentPresenter extends MvpPresenter<CommentView> {

    private Router router;
    private SessionRepository sessionRepository;
    private int idArticle;
    private String commentText;
    private CommentRepository commentRepository;
    private List<NwComment> comments = new ArrayList<>();
    private UserRepository userRepository;

    final static int LIMIT = 10;

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    @Inject
    public CommentPresenter(Router router, SessionRepository sessionRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.router = router;
        this.sessionRepository = sessionRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public void onBackClick() {
        router.exit();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Timber.d(idArticle + "");
        loadComment(0);
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
        commentRepository.addComment(idArticle, commentText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showProgress(true))
                .doOnEvent((tokenResponse, throwable) -> getViewState().showProgress(false))
                .subscribe(
                        nwComment -> {
                            Timber.d("Текст комментария отправлен");
                            getViewState().showMessage("комментарий отправлен");
                            loadComment(0);
                            getViewState().clearCommentInput();
                        },
                        error -> {
                            Timber.e(error);
                            getViewState().showMessage(error.getMessage());
                        }
                );
    }

    public void loadComment(int offset) {
        commentRepository.loadComment(offset, LIMIT, idArticle)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    getViewState().enableScrollListener(false);
                    getViewState().enableSwipeRefreshLayout(false);
                    getViewState().showButtonRetry(false);
                    if (offset != 0) {
                        getViewState().showProgress(true);
                    } else {
                        getViewState().showSwipeRefreshLayout(true);
                    }
                })
                .doOnEvent((tokenResponse, throwable) -> {
                    getViewState().showProgress(false);
                    getViewState().showSwipeRefreshLayout(false);
                    getViewState().enableScrollListener(true);
                    getViewState().enableSwipeRefreshLayout(true);
                })
                .subscribe(
                        nwComments -> {
                            if (offset == 0) {
                                comments.clear();
                                comments.addAll(nwComments);
                            } else {
                                comments.addAll(nwComments);
                            }
                            Timber.d("Текст комментария" + nwComments);
                            getViewState().showComments(comments);
                        },
                        error -> {
                            Timber.e(error);
                            getViewState().showMessage(error.getMessage());
                            if (offset == 0) {
                                getViewState().showButtonRetry(true);
                            }
                        }
                );
    }

    public void onDeleteCommentClick(NwComment comment) {
        userRepository.loadUser()
                .flatMap(nwUser -> {
                    if (nwUser.id == comment.authorId){
                        return commentRepository.deleteComment(comment.id);
                    } else {
                        boolean isAdmin = false;
                        for (NwAuthority nwAuthority:nwUser.authorities){
                            if (nwAuthority.authority.equals("ADMIN")){
                                isAdmin = true;
                                break;
                            }
                        }
                        if (isAdmin){
                           return commentRepository.deleteComment(comment.id);
                        } else {
                            return Single.error(new IllegalStateException("Вы не автор и не админ!"));
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        nwUser -> {
                            getViewState().showMessage("Комментарий удален");
                            loadComment(0);
                        },
                        throwable -> {
                            Timber.e(throwable);
                            getViewState().showMessage(throwable.getMessage());
                        }
                );
    }
}
