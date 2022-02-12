package ru.dpwg.itnews.mvp.view;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEndSingle;
import ru.dpwg.itnews.domain.NwComment;
import ru.dpwg.itnews.domain.article.NwArticle;

public interface CommentView extends MvpView{

    @AddToEndSingle
    void showButtonLogin(boolean show);

    @AddToEndSingle
    void showProgress(boolean show);

    @AddToEndSingle
    void enableSendButton(boolean enable);

    @AddToEndSingle
    void showMessage(String message);

    @AddToEndSingle
    void enableInput(boolean enable);

    @AddToEndSingle
    void showInput(boolean show);

    @AddToEndSingle
    void enableSwipeRefreshLayout(boolean enable);

    @AddToEndSingle
    void enableButtonLoadMore(boolean enable);

    @AddToEndSingle
    void showButtonRetry(boolean show);

    @AddToEndSingle
    void showComments(List<NwComment> comments);

    @AddToEndSingle
    void showSwipeRefreshLayout(boolean show);

}
