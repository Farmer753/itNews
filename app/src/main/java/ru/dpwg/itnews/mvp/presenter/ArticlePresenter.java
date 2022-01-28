package ru.dpwg.itnews.mvp.presenter;

import com.github.terrakok.cicerone.Router;

import javax.inject.Inject;

import moxy.MvpPresenter;
import ru.dpwg.itnews.Screens;
import ru.dpwg.itnews.domain.SessionRepository;
import ru.dpwg.itnews.mvp.view.ArticleListView;
import ru.dpwg.itnews.mvp.view.ArticleView;
import timber.log.Timber;

public class ArticlePresenter extends MvpPresenter<ArticleView> {
    private Router router;
    private SessionRepository sessionRepository;
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    @Inject
    public ArticlePresenter(Router router, SessionRepository sessionRepository) {
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

    public void commentClick(){
        router.navigateTo(new Screens.CommentScreen(id));

    }

    public void profileClick() {
        if (sessionRepository.getAccessToken() == null) {
            router.navigateTo(new Screens.LoginScreen());
        } else {
            router.navigateTo(new Screens.ProfileScreen());
        }
    }
}
