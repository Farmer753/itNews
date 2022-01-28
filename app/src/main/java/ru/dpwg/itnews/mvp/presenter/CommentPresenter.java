package ru.dpwg.itnews.mvp.presenter;

import com.github.terrakok.cicerone.Router;

import javax.inject.Inject;

import moxy.MvpPresenter;
import ru.dpwg.itnews.domain.SessionRepository;
import ru.dpwg.itnews.mvp.view.CommentView;
import timber.log.Timber;

public class CommentPresenter extends MvpPresenter<CommentView> {

    private Router router;
    private SessionRepository sessionRepository;
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    @Inject
    public CommentPresenter(Router router, SessionRepository sessionRepository) {
        this.router = router;
        this.sessionRepository = sessionRepository;
    }

    public void onBackClick() {
        router.exit();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Timber.d(id + "");
    }
}
