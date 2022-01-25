package ru.dpwg.itnews.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.dpwg.itnews.R;
import ru.dpwg.itnews.di.Di;
import ru.dpwg.itnews.mvp.presenter.ArticleListPresenter;
import ru.dpwg.itnews.mvp.presenter.ArticlePresenter;
import ru.dpwg.itnews.mvp.view.ArticleListView;
import ru.dpwg.itnews.mvp.view.ArticleView;
import timber.log.Timber;
import toothpick.Toothpick;

public class ArticleFragment extends MvpAppCompatFragment implements ArticleView {
    @InjectPresenter
    ArticlePresenter presenter;

    Toolbar toolbar;

    @ProvidePresenter
    ArticlePresenter getPresenter() {
        return Toothpick.openScope(Di.APP_SCOPE).getInstance(ArticlePresenter.class);
    }

    @Nullable
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_article, container, false);
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);

        toolbar = view.findViewById(R.id.toolbar);
toolbar.setNavigationOnClickListener(v -> presenter.onBackClick());
    }
}