package ru.dpwg.itnews;

import androidx.appcompat.app.AppCompatActivity;
import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.dpwg.itnews.di.Di;
import ru.dpwg.itnews.mvp.presenter.MainActivityPresenter;
import ru.dpwg.itnews.mvp.view.MainActivityView;
import timber.log.Timber;
import toothpick.Toothpick;

import android.content.SharedPreferences;
import android.os.Bundle;

import javax.inject.Inject;

public class MainActivity extends MvpAppCompatActivity implements MainActivityView {

    @Inject
    SharedPreferences sharedPreferences;

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


}