package ru.dpwg.itnews.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.dpwg.itnews.R;
import ru.dpwg.itnews.di.Di;
import ru.dpwg.itnews.mvp.presenter.CommentPresenter;
import ru.dpwg.itnews.mvp.view.CommentView;
import toothpick.Toothpick;

public class CommentFragment extends MvpAppCompatFragment implements CommentView {

    @InjectPresenter
    CommentPresenter presenter;

    Toolbar toolbar;


    @ProvidePresenter
    CommentPresenter getPresenter() {
        CommentPresenter cPresenter = Toothpick.openScope(Di.APP_SCOPE).getInstance(CommentPresenter.class);
        int id = getArguments().getInt("id");
        cPresenter.setId(id);
        return cPresenter;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            @org.jetbrains.annotations.Nullable ViewGroup container,
            @org.jetbrains.annotations.Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }

    @Override
    public void onViewCreated(
            @NonNull @NotNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> presenter.onBackClick());
    }
}
