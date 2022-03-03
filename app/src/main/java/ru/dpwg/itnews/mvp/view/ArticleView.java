package ru.dpwg.itnews.mvp.view;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEndSingle;
import ru.dpwg.itnews.domain.article.nw.NwArticle;
import ru.dpwg.itnews.domain.article.ui.UiArticle;


public interface ArticleView extends MvpView {


    @AddToEndSingle
    void showProgress(boolean show);

    @AddToEndSingle
    void showButtonRetry(boolean show);

    @AddToEndSingle
    void showMessage(String message);

    @AddToEndSingle
    void showArticle(UiArticle uiArticle);

}
