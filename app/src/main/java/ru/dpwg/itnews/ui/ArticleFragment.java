package ru.dpwg.itnews.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import io.noties.markwon.Markwon;
import io.noties.markwon.image.ImagesPlugin;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.dpwg.itnews.R;
import ru.dpwg.itnews.Util;
import ru.dpwg.itnews.di.Di;
import ru.dpwg.itnews.domain.article.nw.NwArticle;
import ru.dpwg.itnews.domain.article.ui.UiArticle;
import ru.dpwg.itnews.domain.article.ui.UiTranslation;
import ru.dpwg.itnews.mvp.presenter.ArticlePresenter;
import ru.dpwg.itnews.mvp.view.ArticleView;
import timber.log.Timber;
import toothpick.Toothpick;

public class ArticleFragment extends MvpAppCompatFragment implements ArticleView {
    @InjectPresenter
    ArticlePresenter presenter;

    Toolbar toolbar;
    TextView textView;
    Button commentButton;
    View progressView;
    Button buttonRetry;
    LinearLayout translationContainer;


    @ProvidePresenter
    ArticlePresenter getPresenter() {
        ArticlePresenter aPresenter = Toothpick.openScope(Di.APP_SCOPE).getInstance(ArticlePresenter.class);
        int id = getArguments().getInt("id");
        aPresenter.setId(id);
        return aPresenter;
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
        textView = view.findViewById(R.id.article);
        progressView = view.findViewById(R.id.progressView);
        buttonRetry = view.findViewById(R.id.buttonRetry);
        buttonRetry.setOnClickListener(v -> presenter.getArticle());
        translationContainer = view.findViewById(R.id.translationContainer);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> presenter.onBackClick());
        toolbar.inflateMenu(R.menu.menu_profile);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.profile) {
                Timber.d("Профиль нажат");
                presenter.profileClick();

            }
            return false;
        });
        commentButton = view.findViewById(R.id.commentButton);
        commentButton.setOnClickListener(v -> presenter.commentClick());
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
    public void showArticle(UiArticle uiArticle) {
//        textView.setText(uiArticle.translations.get(0).versions.get(0).text);
        final Markwon markwon = Markwon.builder(textView.getContext())
                .usePlugin(ImagesPlugin.create())
                .build();
        markwon.setMarkdown(textView, uiArticle.translations.get(0).versions.get(0).text);

        toolbar.setTitle(uiArticle.translations.get(0).title);
        translationContainer.removeAllViews();
        for (UiTranslation translation : uiArticle.translations) {
            ImageView view = (ImageView) getLayoutInflater().inflate(
                    R.layout.view_translation_language, translationContainer, false
            );
            view.setImageResource(Util.getFlagByLangId(translation.langId));
            view.setOnClickListener(view1 -> markwon.setMarkdown(textView,translation.versions.get(0).text));
            translationContainer.addView(view);
        }
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