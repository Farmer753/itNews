package ru.dpwg.itnews.domain;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import ru.dpwg.itnews.domain.article.db.ArticleDao;
import ru.dpwg.itnews.domain.article.db.DbArticle;
import ru.dpwg.itnews.domain.article.db.DbTranslation;
import ru.dpwg.itnews.domain.article.db.DbVersion;

@Database(entities = {DbArticle.class, DbTranslation.class, DbVersion.class}, version = 1)
public abstract class ItNewsDatabase extends RoomDatabase {

    public abstract ArticleDao getArticleDao();
}
