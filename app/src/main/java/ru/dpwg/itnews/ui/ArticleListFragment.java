package ru.dpwg.itnews.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

import moxy.MvpAppCompatFragment;
import moxy.presenter.ProvidePresenter;
import ru.dpwg.itnews.R;
import ru.dpwg.itnews.di.Di;
import ru.dpwg.itnews.mvp.presenter.ArticleListPresenter;
import ru.dpwg.itnews.mvp.view.ArticleListView;
import toothpick.Toothpick;

public class ArticleListFragment extends MvpAppCompatFragment implements ArticleListView {
    @Inject
    ArticleListPresenter articleListPresenter;

    @ProvidePresenter
    ArticleListPresenter getArticleListPresenter() {
        return Toothpick.openScope(Di.APP_SCOPE).getInstance(ArticleListPresenter.class);
    }
    @Nullable
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_article_list, container, false);
    }
}
