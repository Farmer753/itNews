package ru.dpwg.itnews.di.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import ru.dpwg.itnews.domain.ItNewsDatabase;
import ru.dpwg.itnews.domain.article.db.ArticleDao;
import toothpick.config.Module;

public class StorageModule extends Module {

    public StorageModule(Context context) {
        bind(SharedPreferences.class)
                .toInstance(PreferenceManager.getDefaultSharedPreferences(context));
        ItNewsDatabase itNewsDatabase = Room.databaseBuilder(
                context, ItNewsDatabase.class, "it_news_database"
        ).allowMainThreadQueries().build();
        bind(ArticleDao.class).toInstance(itNewsDatabase.getArticleDao());
    }
}
