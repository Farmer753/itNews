package ru.dpwg.itnews.data;

import java.util.Random;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import ru.dpwg.itnews.domain.SessionRepository;
import ru.dpwg.itnews.domain.TokenResponse;

public class SessionRepositoryImpl implements SessionRepository {

    @Inject
    public SessionRepositoryImpl() {
    }

    @Override
    public Single<TokenResponse> login(String email, String password) {
        return Single.create(emitter -> {
            Thread.sleep(2000);
            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.accessToken = "строчка accessToken";
            tokenResponse.refreshToken = "строчка refreshToken";
            Random random = new Random();
            if (random.nextBoolean()){
                emitter.onSuccess(tokenResponse);
            }
            else {
                emitter.onError(new IllegalStateException("сообщение об ошибке"));
            }

        });
    }

    @Override
    public String getRefreshToken() {
        return null;
    }

    @Override
    public void saveRefreshToken(String refreshToken) {

    }

    @Override
    public String getAccessToken() {
        return null;
    }

    @Override
    public void saveAccessToken(String accessToken) {

    }
}
