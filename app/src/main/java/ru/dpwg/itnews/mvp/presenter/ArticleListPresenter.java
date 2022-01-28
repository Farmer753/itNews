package ru.dpwg.itnews.mvp.presenter;

import com.github.terrakok.cicerone.Router;

import javax.inject.Inject;

import moxy.MvpPresenter;
import ru.dpwg.itnews.Screens;
import ru.dpwg.itnews.domain.SessionRepository;
import ru.dpwg.itnews.mvp.view.ArticleListView;

public class ArticleListPresenter extends MvpPresenter<ArticleListView> {
    private Router router;
    private SessionRepository sessionRepository;

    @Inject
    public ArticleListPresenter(Router router, SessionRepository sessionRepository) {
        this.router = router;
        this.sessionRepository = sessionRepository;
    }

    public void profileClick() {
        if (sessionRepository.getAccessToken() == null) {
            router.navigateTo(new Screens.LoginScreen());
        } else {
            router.navigateTo(new Screens.ProfileScreen());
        }
    }

    public void articleClick(int id){
        router.navigateTo(new Screens.ArticleScreen(id));

    }
}
