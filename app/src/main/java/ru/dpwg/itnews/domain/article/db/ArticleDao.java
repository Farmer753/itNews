package ru.dpwg.itnews.domain.article.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public abstract class ArticleDao {
    @Insert
    abstract void insertTextVersions(List<DbVersion> dbVersions);

    @Insert
    abstract void insertArticle(DbArticle dbArticle);

    @Insert
    abstract void insertTranslation(DbTranslation dbTranslation);

}
