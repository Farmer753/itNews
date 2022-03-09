package ru.dpwg.itnews.domain.article;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.dpwg.itnews.domain.article.nw.ArticlesResponse;


public interface ArticleApi {
    @GET("article/all")
    Single<ArticlesResponse> loadArticles(
            @Query("limit") int limit,
            @Query("offset") int offset,
            @Query("onlyForCurrentDate") boolean onlyForCurrentDate
    );
}
