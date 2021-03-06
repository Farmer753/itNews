package ru.dpwg.itnews.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.dpwg.itnews.R;
import ru.dpwg.itnews.domain.GetArticlesWorker;
import ru.dpwg.itnews.domain.article.ui.UiArticle;
import ru.dpwg.itnews.mvp.presenter.ArticleListPresenter;
import ru.dpwg.itnews.mvp.view.ArticleListView;
import ru.dpwg.itnews.ui.util.EndlessRecyclerViewScrollListener;
import ru.dpwg.itnews.ui.util.NotificationUtil;
import timber.log.Timber;
import toothpick.Toothpick;

import static ru.dpwg.itnews.di.Di.APP_SCOPE;

public class ArticleListFragment extends MvpAppCompatFragment implements ArticleListView {
    @InjectPresenter
    ArticleListPresenter presenter;

    ArticlesAdapter adapter;

    @Inject
    NotificationUtil notificationUtil;

    Toolbar toolbar;
    View progressView;
    Button buttonRetry;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;

    @ProvidePresenter
    ArticleListPresenter getPresenter() {
        return Toothpick.openScope(APP_SCOPE).getInstance(ArticleListPresenter.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toothpick.openScope(APP_SCOPE).inject(this);
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
        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new ArticlesAdapter(article -> presenter.articleClick(article.id));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_profile);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.profile) {
                Timber.d("?????????????? ??????????");
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
    public void showArticles(List<UiArticle> articles) {
        adapter.setArticles(articles);
    }

    @Override
    public void showSwipeRefreshLayout(boolean show) {
        swipeRefreshLayout.setRefreshing(show);
    }

    @Override
    public void enableScrollListener(boolean enable) {
        if (enable) {
            recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener() {
                @Override
                public void onLoadMore(int page, int totalItemsCount) {
                    Timber.d("onLoadMore " + page + " " + totalItemsCount);
                    recyclerView.clearOnScrollListeners();
                    presenter.loadArticles(totalItemsCount);
                }
            });
        } else {
            recyclerView.clearOnScrollListeners();
        }
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
}