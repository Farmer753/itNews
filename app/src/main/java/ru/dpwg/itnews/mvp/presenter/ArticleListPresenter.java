package ru.dpwg.itnews.mvp.presenter;

import javax.inject.Inject;

import moxy.MvpPresenter;
import ru.dpwg.itnews.mvp.view.ArticleListView;

public class ArticleListPresenter extends MvpPresenter<ArticleListView> {

    @Inject
    public ArticleListPresenter() {
    }
}
