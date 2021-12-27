package ru.dpwg.itnews.mvp.presenter;

import com.github.terrakok.cicerone.Router;

import javax.inject.Inject;

import moxy.MvpPresenter;
import ru.dpwg.itnews.Screens;
import ru.dpwg.itnews.mvp.view.ArticleListView;

public class ArticleListPresenter extends MvpPresenter<ArticleListView> {
    private Router router;

    @Inject
    public ArticleListPresenter(Router router) {
        this.router = router;
    }

    public void profileClick() {
        router.navigateTo(new Screens.LoginScreen());
    }
}
