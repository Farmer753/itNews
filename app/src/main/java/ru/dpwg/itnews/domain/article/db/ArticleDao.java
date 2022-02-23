package ru.dpwg.itnews.domain.article.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public abstract class ArticleDao {
    @Insert
    public abstract void insertTextVersions(List<DbVersion> dbVersions);

    @Insert
    public abstract void insertArticle(DbArticle dbArticle);

    @Insert
    public abstract void insertTranslation(DbTranslation dbTranslation);

    @Query("Select * from ARTICLES where id = :id")
    public abstract Flowable<DbArticle> findById(int id);
}
