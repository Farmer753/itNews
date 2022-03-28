package ru.dpwg.itnews.di.module;

import android.content.Context;

import ru.dpwg.itnews.ui.util.NotificationUtil;
import toothpick.config.Module;

public class ApplicationModule extends Module {

    public ApplicationModule(Context context) {
        bind(Context.class).toInstance(context);
        bind(NotificationUtil.class).toInstance(new NotificationUtil(context));
    }
}
