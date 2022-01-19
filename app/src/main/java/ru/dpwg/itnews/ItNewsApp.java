package ru.dpwg.itnews;

import android.app.Application;

import ru.dpwg.itnews.di.module.ApplicationModule;
import ru.dpwg.itnews.di.module.DomainModule;
import ru.dpwg.itnews.di.module.NavigationModule;
import ru.dpwg.itnews.di.module.StorageModule;
import timber.log.Timber;
import toothpick.Toothpick;

import static ru.dpwg.itnews.di.Di.APP_SCOPE;

public class ItNewsApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        Toothpick.openScope(APP_SCOPE)
                .installModules(
                        new ApplicationModule(this),
                        new StorageModule(this),
                        new NavigationModule(),
                        new DomainModule()
                );
    }
}
