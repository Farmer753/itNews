package ru.dpwg.itnews.mvp.presenter;

import com.github.terrakok.cicerone.Router;
import javax.inject.Inject;
import moxy.MvpPresenter;
import ru.dpwg.itnews.Screens;
import ru.dpwg.itnews.mvp.view.OnboardingView;
import timber.log.Timber;

public class OnboardingPresenter extends MvpPresenter<OnboardingView> {

    private Router router;

    @Inject
    public OnboardingPresenter(Router router) {
        this.router = router;
    }

    public void onNextButtonClick() {
        Timber.d("onNextButtonClick");
        router.newRootScreen(new Screens.ArticleListScreen());
    }
}
