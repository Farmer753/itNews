package ru.dpwg.itnews.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

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
    View progressView;
    Button buttonRetry;
    Button buttonLoadMore;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout articleContainer;

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


        progressView = view.findViewById(R.id.progressView);
        swipeRefreshLayout = view.findViewById(R.id.swiperefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.loadArticles(0));
        buttonRetry = view.findViewById(R.id.buttonRetry);
        buttonRetry.setOnClickListener(v -> presenter.loadArticles(0));
        articleContainer = view.findViewById(R.id.articleContainer);
        buttonLoadMore = view.findViewById(R.id.buttonLoadMore);
        buttonLoadMore.setOnClickListener(v -> presenter.loadArticles(articleContainer.getChildCount()));
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
        articleContainer.removeAllViews();
        for (int i = 0; i < articles.size(); i++) {
            NwArticle article = articles.get(i);
            articleContainer.addView(createArticleView(article));
        }
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
    public void enableSwipeRefreshLayout(boolean enable) {
        swipeRefreshLayout.setEnabled(enable);
    }

    @Override
    public void showButtonRetry(boolean show) {
        if (show) {
            buttonRetry.setVisibility(View.VISIBLE);
        } else {
            buttonRetry.setVisibility(View.GONE);
        }
    }

    private View createArticleView(NwArticle nwArticle) {
        ImageView articleImageView;
        TextView titleTextView;
        TextView dateTextView;
        TextView articleTextView;

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.list_item_article, null, false);

        articleImageView = view.findViewById(R.id.articleImageView);
        titleTextView = view.findViewById(R.id.titleTextView);
        dateTextView = view.findViewById(R.id.dateTextView);
        articleTextView = view.findViewById(R.id.articleTextView);

        view.setOnClickListener(v -> presenter.articleClick(nwArticle.id));

        titleTextView.setText(nwArticle.translations.get(0).title);
        dateTextView.setText(nwArticle.publishedDate);
        articleTextView.setText(nwArticle.translations.get(0).shortDescription);
        Glide.with(articleImageView)
                .load("https://dont-play-with-google.com:8443/api/" + nwArticle.translations.get(0).imageUrl)
                .into(articleImageView);

        return view;
    }
}