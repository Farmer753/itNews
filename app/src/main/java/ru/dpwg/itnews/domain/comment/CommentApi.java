package ru.dpwg.itnews.domain.comment;

import java.util.List;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.dpwg.itnews.domain.comment.NwComment;

public interface CommentApi {
    @GET("comment/all")
    Single<List<NwComment>> loadComments(
            @Query("limit") int limit,
            @Query("offset") int offset,
            @Query("articleId") int articleId
    );

    @POST("comment/add")
    @FormUrlEncoded
    Single<NwComment> addComment(
            @Field("articleId") int articleId,
            @Field("text") String text
    );
}
