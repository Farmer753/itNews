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
import ru.dpwg.itnews.domain.article.NwArticle;
import ru.dpwg.itnews.mvp.view.ArticleListView;
import timber.log.Timber;

public class ArticleListPresenter extends MvpPresenter<ArticleListView> {
    private Router router;
    private SessionRepository sessionRepository;
    private ArticleRepository articleRepository;
    final static int LIMIT = 10;
    private List<NwArticle> articles = new ArrayList<>();


    @Inject
    public ArticleListPresenter(
            Router router,
            SessionRepository sessionRepository,
            ArticleRepository articleRepository
    ) {
        this.router = router;
        this.sessionRepository = sessionRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadArticles(0);
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
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    getViewState().showProgress(true);
                    getViewState().showButtonRetry(false);
                })
                .doOnEvent((tokenResponse, throwable) -> getViewState().showProgress(false))
                .subscribe(
                        loadArticles -> {
//                            Timber.d(loadArticles.get(0).translations.get(0).versions.get(0).text);
                            if (offset == 0) {
                                articles.clear();
                                articles.addAll(loadArticles);
                            } else {
                                articles.addAll(loadArticles);
                            }
                            getViewState().showArticles(articles);
                        },
                        error -> {
                            Timber.e(error);
                            getViewState().showMessage(error.getMessage());
                            getViewState().showButtonRetry(true);
                        }
                );


    }
}
