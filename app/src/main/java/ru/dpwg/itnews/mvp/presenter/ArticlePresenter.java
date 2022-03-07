package ru.dpwg.itnews.mvp.presenter;

import com.github.terrakok.cicerone.Router;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import moxy.MvpPresenter;
import ru.dpwg.itnews.Screens;
import ru.dpwg.itnews.domain.SessionRepository;
import ru.dpwg.itnews.domain.article.ArticleRepository;
import ru.dpwg.itnews.domain.article.DbArticleConverter;
import ru.dpwg.itnews.domain.article.UiArticleConverter;
import ru.dpwg.itnews.domain.article.db.DbArticle;
import ru.dpwg.itnews.mvp.view.ArticleView;
import timber.log.Timber;

public class ArticlePresenter extends MvpPresenter<ArticleView> {
    private Router router;
    private SessionRepository sessionRepository;
    private ArticleRepository articleRepository;
    private DbArticleConverter dbConverter;
    private UiArticleConverter uiConverter;

    private int id;

    @Inject
    public ArticlePresenter(Router router, SessionRepository sessionRepository, ArticleRepository articleRepository, DbArticleConverter dbConverter, UiArticleConverter uiConverter) {
        this.router = router;
        this.sessionRepository = sessionRepository;
        this.articleRepository = articleRepository;
        this.dbConverter = dbConverter;
        this.uiConverter = uiConverter;
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
        getArticle();
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

    public void getArticle() {
        articleRepository.getArticleById(id)
                .flatMap(dbArticle -> {
                    if (dbArticle.translations.get(0).versions.isEmpty()) {
                        Timber.d("Версии текста для статьи нет");
                        return articleRepository.loadArticleById(id)
                                .doOnSuccess(nwArticle -> {
                                    DbArticle article = dbConverter.convert(nwArticle);
                                    articleRepository.insertArticle(article);
                                })
                                .flatMap(nwArticle -> articleRepository.getArticleById(id));
                    } else {
                        Timber.d("Версии текста для статьи есть");
                        return Single.just(dbArticle);
                    }
                })
                .map(dbArticle -> uiConverter.convert(dbArticle))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    getViewState().showProgress(true);
                    getViewState().showButtonRetry(false);
                })
                .doOnEvent((tokenResponse, throwable) -> getViewState().showProgress(false))
                .subscribe(
                        uiArticle -> {
                            Timber.d("Версия текста первого перевода статьи из БД "
                                    + uiArticle.translations.get(0).versions.get(0).text);
                            getViewState().showArticle(uiArticle);
                            Timber.d("размер списка переводов статьи "
                                    + uiArticle.translations.size());
                        },
                        throwable -> {
                            Timber.e(throwable);
                            getViewState().showMessage(throwable.getMessage());
                            getViewState().showButtonRetry(true);
                        }
                );
    }
}
