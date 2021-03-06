package ru.dpwg.itnews.domain.article;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import ru.dpwg.itnews.domain.article.db.DbArticle;
import ru.dpwg.itnews.domain.article.nw.NwArticle;

public interface ArticleRepository {
    Single<NwArticle> loadArticleById(int id);
    Single<List<NwArticle>> loadArticles(int limit, int offset);
    Flowable<List<DbArticle>> getArticles();
    void insertArticles(List<DbArticle> dbArticles);
    void deleteAll();
    Single<DbArticle> getArticleById(int id);
    void insertArticle(DbArticle article);
}
