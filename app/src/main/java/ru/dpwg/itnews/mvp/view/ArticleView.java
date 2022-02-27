package ru.dpwg.itnews.mvp.view;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEndSingle;
import ru.dpwg.itnews.domain.article.nw.NwArticle;


public interface ArticleView extends MvpView {


    @AddToEndSingle
    void showProgress(boolean show);

    @AddToEndSingle
    void showButtonRetry(boolean show);

    @AddToEndSingle
    void showMessage(String message);

    @AddToEndSingle
    void showArticle(NwArticle nwArticle);

}
