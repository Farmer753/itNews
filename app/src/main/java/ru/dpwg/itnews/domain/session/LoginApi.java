package ru.dpwg.itnews.domain.session;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginApi {
    @POST("oauth/token")
    @FormUrlEncoded
    Single<TokenResponse> token(
            @Field("username") String userName,
            @Field("password") String password,
            @Field("grant_type") String grantType
    );

    @POST("oauth/token")
    @FormUrlEncoded
    Single<TokenResponse> refreshToken(
            @Field("refresh_token") String refreshToken,
            @Field("grant_type") String grantType
    );
}
