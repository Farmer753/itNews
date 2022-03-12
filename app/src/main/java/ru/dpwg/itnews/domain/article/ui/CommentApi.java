package ru.dpwg.itnews.domain.article.ui;

import java.util.List;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.dpwg.itnews.domain.NwComment;

public interface CommentApi {
    @GET("comment/all")
    Single<List<NwComment>> loadComments(
            @Query("limit") int limit,
            @Query("offset") int offset,
            @Query("articleId") int articleId
    );
}
