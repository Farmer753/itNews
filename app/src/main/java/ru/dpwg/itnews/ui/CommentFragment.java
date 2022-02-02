package ru.dpwg.itnews.ui;

import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import timber.log.Timber;
import toothpick.Toothpick;

public class CommentFragment extends MvpAppCompatFragment implements CommentView {

    @InjectPresenter
    CommentPresenter presenter;

    Toolbar toolbar;
    EditText commentEditText;
    Button buttonLogin;
    View progressView;
    ImageView sendComment;
    View commentInput;


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
        commentInput = view.findViewById(R.id.commentInput);
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
        buttonLogin = view.findViewById(R.id.buttonLogin);
        progressView = view.findViewById(R.id.progressView);
        commentEditText = view.findViewById(R.id.commentEditText);
        commentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.onCommentChange(s.toString());

            }
        });
        buttonLogin.setOnClickListener(v -> presenter.onLoginClick());
        sendComment = view.findViewById(R.id.sendComment);
        sendComment.setOnClickListener(v -> presenter.sendClick());
    }

    @Override
    public void showButtonLogin(boolean show) {
        if (show) {
            buttonLogin.setVisibility(View.VISIBLE);
        } else {
            buttonLogin.setVisibility(View.GONE);
        }
    }

    @Override
    public void showInput(boolean show) {
        if (show) {
            commentInput.setVisibility(View.VISIBLE);
            sendComment.setVisibility(View.VISIBLE);
        } else {
            commentInput.setVisibility(View.GONE);
            sendComment.setVisibility(View.GONE);
        }
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
    public void enableSendButton(boolean enable) {
        sendComment.setEnabled(enable);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void enableInput(boolean enable) {
        commentEditText.setEnabled(enable);
    }
}
