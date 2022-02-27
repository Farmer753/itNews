package ru.dpwg.itnews.mvp.view;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEndSingle;
import ru.dpwg.itnews.domain.article.nw.NwArticle;

public interface ArticleListView extends MvpView {

    @AddToEndSingle
    void showProgress(boolean show);

    @AddToEndSingle
    void showButtonRetry(boolean show);

    @AddToEndSingle
    void showMessage(String message);

    @AddToEndSingle
    void showArticles(List<NwArticle> articles);

    @AddToEndSingle
    void showSwipeRefreshLayout(boolean show);

    @AddToEndSingle
    void enableScrollListener(boolean enable);

    @AddToEndSingle
    void enableSwipeRefreshLayout(boolean enable);

}
