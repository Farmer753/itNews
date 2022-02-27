package ru.dpwg.itnews.mvp.presenter;

import com.github.terrakok.cicerone.Router;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import moxy.MvpPresenter;
import ru.dpwg.itnews.Screens;
import ru.dpwg.itnews.domain.SessionRepository;
import ru.dpwg.itnews.domain.article.ArticleRepository;
import ru.dpwg.itnews.domain.article.DbArticleConverter;
import ru.dpwg.itnews.domain.article.UiArticleConverter;
import ru.dpwg.itnews.domain.article.nw.NwArticle;
import ru.dpwg.itnews.mvp.view.ArticleListView;
import timber.log.Timber;

public class ArticleListPresenter extends MvpPresenter<ArticleListView> {
    private Router router;
    private SessionRepository sessionRepository;
    private ArticleRepository articleRepository;
    private DbArticleConverter dbConverter;
    private UiArticleConverter uiConverter;
    final static int LIMIT = 10;
    boolean articlesExistsInDb;


    @Inject
    public ArticleListPresenter(
            Router router,
            SessionRepository sessionRepository,
            ArticleRepository articleRepository,
            DbArticleConverter converter, UiArticleConverter uiConverter) {
        this.router = router;
        this.sessionRepository = sessionRepository;
        this.articleRepository = articleRepository;
        this.dbConverter = converter;
        this.uiConverter = uiConverter;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadArticles(0);
        articleRepository
                .getArticles()
                .map(dbArticles -> uiConverter.convert(dbArticles))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uiArticles -> {
                    articlesExistsInDb = !uiArticles.isEmpty();
                    getViewState().showArticles(uiArticles);
                });

    }

    public void profileClick() {
        if (sessionRepository.getAccessToken() == null) {
            router.navigateTo(new Screens.LoginScreen());
        } else {
            router.navigateTo(new Screens.ProfileScreen());
        }
    }

    public void articleClick(int id) {
        router.navigateTo(new Screens.ArticleScreen(id));

    }

    public void loadArticles(int offset) {
        articleRepository
                .loadArticles(LIMIT, offset)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(nwArticles -> {
                    if (offset == 0) {
                        articleRepository.deleteAll();
                    }
                    articleRepository.insertArticles(dbConverter.convert(nwArticles));
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    if (offset == 0) {
                        getViewState().showSwipeRefreshLayout(true);
                    } else {
                        getViewState().showProgress(true);
                        getViewState().enableSwipeRefreshLayout(false);
                    }
                    getViewState().showButtonRetry(false);
                    getViewState().enableScrollListener(false);
                })
                .doOnEvent((tokenResponse, throwable) -> {
                    getViewState().showSwipeRefreshLayout(false);
                    getViewState().showProgress(false);
                    getViewState().enableScrollListener(true);
                    getViewState().enableSwipeRefreshLayout(true);
                })
                .subscribe(
                        loadArticles -> {
                        },
                        error -> {
                            Timber.e(error);
                            getViewState().showMessage(error.getMessage());
                            if (offset == 0 && !articlesExistsInDb) {
                                getViewState().showButtonRetry(true);
                            }
                        }
                );
    }
}
