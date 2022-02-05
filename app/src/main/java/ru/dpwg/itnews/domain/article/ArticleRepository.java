package ru.dpwg.itnews.domain.article;

import io.reactivex.rxjava3.core.Single;

public interface ArticleRepository {
    Single<NwArticle> loadArticleById(int id);
}
