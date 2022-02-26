package ru.dpwg.itnews.domain.article.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.rxjava3.core.Flowable;
import timber.log.Timber;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public abstract class ArticleDao {
    @Insert(onConflict = REPLACE)
    public abstract void insertTextVersions(List<DbVersion> dbVersions);

    @Insert(onConflict = REPLACE)
    public abstract void insertArticle(DbArticle dbArticle);

    @Insert(onConflict = REPLACE)
    public abstract void insertTranslation(DbTranslation dbTranslation);

    @Query("Select * from ARTICLES where id = :id")
    public abstract Flowable<DbArticle> findById(int id);

    @Query("select * from articles order by published_date desc")
    public abstract Flowable<List<DbArticle>> findAllArticles();

    public Flowable<List<DbArticle>> findAllArticlesFull() {
        return findAllArticles()
                .map(dbArticles -> {
                    for (DbArticle dbArticle : dbArticles) {
                        List<DbTranslation> dbTranslations
                                = findAllTranslationByArticleId(dbArticle.id);
                        dbArticle.translations = dbTranslations;
                        for (DbTranslation dbTranslation : dbTranslations) {
                            List<DbVersion> dbVersions
                                    = findAllVersionByTranslationId(dbTranslation.id);
                            dbTranslation.versions = dbVersions;
                        }
                    }
                    return dbArticles;
                });
    }

    @Query("select * from versions where article_translation_id = :translationId")
    public abstract List<DbVersion> findAllVersionByTranslationId(int translationId);

    @Query("select * from translations where article_id = :articleId")
    public abstract List<DbTranslation> findAllTranslationByArticleId(int articleId);


    public void insertArticleFull(DbArticle dbArticle) {
        insertArticle(dbArticle);
        for (DbTranslation dbTranslation : dbArticle.translations) {
            insertTranslation(dbTranslation);
            insertTextVersions(dbTranslation.versions);
        }
    }

    public void insertArticlesFull(List<DbArticle> dbArticles) {
        for (DbArticle dbArticle : dbArticles) {
            insertArticleFull(dbArticle);
        }
    }


}
