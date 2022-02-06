package ru.dpwg.itnews.mvp.presenter;

import com.github.terrakok.cicerone.Router;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import moxy.MvpPresenter;
import ru.dpwg.itnews.Screens;
import ru.dpwg.itnews.domain.SessionRepository;
import ru.dpwg.itnews.domain.article.ArticleRepository;
import ru.dpwg.itnews.mvp.view.ArticleView;
import timber.log.Timber;

public class ArticlePresenter extends MvpPresenter<ArticleView> {
    private Router router;
    private SessionRepository sessionRepository;
    private ArticleRepository articleRepository;

    private int id;

    @Inject
    public ArticlePresenter(Router router, SessionRepository sessionRepository, ArticleRepository articleRepository) {
        this.router = router;
        this.sessionRepository = sessionRepository;
        this.articleRepository = articleRepository;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void onBackClick() {
        router.exit();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Timber.d(id + "");
        loadArticle();
    }
    public void loadArticle() {
        articleRepository
                .loadArticleById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    getViewState().showProgress(true);
                    getViewState().showButtonRetry(false);
                })
                .doOnEvent((tokenResponse, throwable) -> getViewState().showProgress(false))
                .subscribe(
                        nwArticle -> {
                            Timber.d(nwArticle.translations.get(0).versions.get(0).text);
                            getViewState().showArticle(nwArticle);
                        },
                        error -> {
                            Timber.e(error);
                            getViewState().showMessage(error.getMessage());
                            getViewState().showButtonRetry(true);
                        }
                );


    }
    public void commentClick() {
        router.navigateTo(new Screens.CommentScreen(id));

    }

    public void profileClick() {
        if (sessionRepository.getAccessToken() == null) {
            router.navigateTo(new Screens.LoginScreen());
        } else {
            router.navigateTo(new Screens.ProfileScreen());
        }
    }
}
