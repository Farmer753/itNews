package ru.dpwg.itnews.domain;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface SessionRepository {

    Single<TokenResponse> login(String email, String password);

    String getRefreshToken();
    void saveRefreshToken(String refreshToken);

    String getAccessToken();
    void saveAccessToken(String accessToken);

    Observable<Boolean> loginState();

}
