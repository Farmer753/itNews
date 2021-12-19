package ru.dpwg.itnews.di.module;

import com.github.terrakok.cicerone.Cicerone;
import com.github.terrakok.cicerone.NavigatorHolder;
import com.github.terrakok.cicerone.Router;

import toothpick.config.Module;

public class NavigationModule extends Module {
    public NavigationModule() {
        Cicerone<Router> cicerone = Cicerone.create();
        NavigatorHolder navigatorHolder = cicerone.getNavigatorHolder();
        Router router = cicerone.getRouter();

        bind(NavigatorHolder.class).toInstance(navigatorHolder);
        bind(Router.class).toInstance(router);

    }
}
