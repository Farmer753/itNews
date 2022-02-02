package ru.dpwg.itnews.data;

import android.content.SharedPreferences;

import com.jakewharton.rxrelay3.BehaviorRelay;

import java.util.Random;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import ru.dpwg.itnews.domain.SessionRepository;
import ru.dpwg.itnews.domain.TokenResponse;

public class SessionRepositoryImpl implements SessionRepository {

    private SharedPreferences sharedPreferences;
    private BehaviorRelay<Boolean> loginState;


    @Inject
    public SessionRepositoryImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        loginState = BehaviorRelay.createDefault(getAccessToken() != null);
    }

    @Override
    public Single<TokenResponse> login(String email, String password) {
        return Single.create(emitter -> {
            Thread.sleep(2000);
            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.accessToken = "строчка accessToken";
            tokenResponse.refreshToken = "строчка refreshToken";
            Random random = new Random();
            if (random.nextBoolean()) {
                emitter.onSuccess(tokenResponse);
            } else {
                emitter.onError(new IllegalStateException("сообщение об ошибке"));
            }

        });
    }

    @Override
    public String getRefreshToken() {

        return sharedPreferences.getString("refreshToken", null);
    }

    @Override
    public void saveRefreshToken(String refreshToken) {
        sharedPreferences.edit().putString("refreshToken", refreshToken).apply();
    }

    @Override
    public String getAccessToken() {

        return sharedPreferences.getString("accessToken", null);

    }

    @Override
    public void saveAccessToken(String accessToken) {
        sharedPreferences.edit().putString("accessToken", accessToken).apply();
        loginState.accept(accessToken != null);

    }

    @Override
    public Observable<Boolean> loginState() {
        return loginState;
    }
}
