package ru.dpwg.itnews.domain.article;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface ArticleRepository {
    Single<NwArticle> loadArticleById(int id);
    Single<List<NwArticle>> loadArticles(int limit, int offset);
}
