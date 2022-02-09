package ru.dpwg.itnews.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.dpwg.itnews.R;
import ru.dpwg.itnews.di.Di;
import ru.dpwg.itnews.domain.article.NwArticle;
import ru.dpwg.itnews.mvp.presenter.ArticleListPresenter;
import ru.dpwg.itnews.mvp.view.ArticleListView;
import timber.log.Timber;
import toothpick.Toothpick;

public class ArticleListFragment extends MvpAppCompatFragment implements ArticleListView {
    @InjectPresenter
    ArticleListPresenter presenter;

    Toolbar toolbar;
    TextView textView;
    View progressView;
    Button buttonRetry;
    Button buttonLoadMore;
    SwipeRefreshLayout swipeRefreshLayout;

    @ProvidePresenter
    ArticleListPresenter getPresenter() {
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);
        textView = view.findViewById(R.id.article);
        textView.setOnClickListener(v -> {
            Random random = new Random();
            presenter.articleClick(random.nextInt());
        });
        progressView = view.findViewById(R.id.progressView);
        swipeRefreshLayout = view.findViewById(R.id.swiperefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.loadArticles(0));
        buttonRetry = view.findViewById(R.id.buttonRetry);
        buttonRetry.setOnClickListener(v -> presenter.loadArticles(0));
        buttonLoadMore = view.findViewById(R.id.buttonLoadMore);
        buttonLoadMore.setOnClickListener(v -> presenter.loadArticles(textView.getLineCount()));
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_profile);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.profile) {
                Timber.d("Профиль нажат");
                presenter.profileClick();

            }
            return false;
        });
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            progressView.setVisibility(View.VISIBLE);
        } else {
            progressView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showArticles(List<NwArticle> articles) {
        String text = "";
//        for (NwArticle article : articles) {
        for (int i = 0; i < articles.size(); i++) {
            NwArticle article = articles.get(i);
            text = text + article.translations.get(0).title;
            if (i < articles.size() - 1) {
                text = text + "\n";
            }
        }
        textView.setText(text);
    }

    @Override
    public void showSwipeRefreshLayout(boolean show) {
        swipeRefreshLayout.setRefreshing(show);
    }

    @Override
    public void enableButtonLoadMore(boolean enable) {
        buttonLoadMore.setEnabled(enable);
    }


    @Override
    public void showButtonRetry(boolean show) {
        if (show) {
            buttonRetry.setVisibility(View.VISIBLE);
        } else {
            buttonRetry.setVisibility(View.GONE);
        }
    }
}