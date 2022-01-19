package ru.dpwg.itnews;

import android.os.Bundle;

import com.github.terrakok.cicerone.Navigator;
import com.github.terrakok.cicerone.NavigatorHolder;
import com.github.terrakok.cicerone.androidx.AppNavigator;

import javax.inject.Inject;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.dpwg.itnews.di.Di;
import ru.dpwg.itnews.mvp.presenter.MainActivityPresenter;
import ru.dpwg.itnews.mvp.view.MainActivityView;
import timber.log.Timber;
import toothpick.Toothpick;

public class MainActivity extends MvpAppCompatActivity implements MainActivityView {


    @Inject
    NavigatorHolder navigatorHolder;
    Navigator navigator = new AppNavigator(this, R.id.container);

    @InjectPresenter
    MainActivityPresenter mainActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timber.d("что угодно");
        Toothpick.openScope(Di.APP_SCOPE).inject(this);
    }

    @ProvidePresenter
    MainActivityPresenter getPresenter() {
        return Toothpick.openScope(Di.APP_SCOPE).getInstance(MainActivityPresenter.class);
    }

    @Override
    protected void onPause() {
        navigatorHolder.removeNavigator();
        super.onPause();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        navigatorHolder.setNavigator(navigator);
    }

}