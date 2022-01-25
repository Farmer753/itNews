package ru.dpwg.itnews.mvp.presenter;

import com.github.terrakok.cicerone.Router;

import javax.inject.Inject;

import moxy.MvpPresenter;
import ru.dpwg.itnews.Screens;
import ru.dpwg.itnews.domain.SessionRepository;
import ru.dpwg.itnews.mvp.view.ArticleListView;
import ru.dpwg.itnews.mvp.view.ArticleView;

public class ArticlePresenter extends MvpPresenter<ArticleView> {
    private Router router;
    private SessionRepository sessionRepository;

    @Inject
    public ArticlePresenter(Router router, SessionRepository sessionRepository) {
        this.router = router;
        this.sessionRepository = sessionRepository;
    }

}
