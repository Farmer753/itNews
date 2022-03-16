package ru.dpwg.itnews.domain.user;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface UserApi {
    @GET("users/me")
    Single<NwUser> getUser();
}
