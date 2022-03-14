package ru.dpwg.itnews.data;

import android.content.SharedPreferences;

import com.jakewharton.rxrelay3.BehaviorRelay;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import ru.dpwg.itnews.domain.session.LoginApi;
import ru.dpwg.itnews.domain.session.SessionRepository;
import ru.dpwg.itnews.domain.session.TokenResponse;

public class SessionRepositoryImpl implements SessionRepository {

    private SharedPreferences sharedPreferences;
    private BehaviorRelay<Boolean> loginState;
    private LoginApi loginApi;


    @Inject
    public SessionRepositoryImpl(SharedPreferences sharedPreferences, LoginApi loginApi) {
        this.sharedPreferences = sharedPreferences;
        this.loginApi = loginApi;
        loginState = BehaviorRelay.createDefault(getAccessToken() != null);
    }

    @Override
    public Single<TokenResponse> login(String email, String password) {
//        return Single.create(emitter -> {
//            Thread.sleep(2000);
//            TokenResponse tokenResponse = new TokenResponse();
//            tokenResponse.accessToken = "строчка accessToken";
//            tokenResponse.refreshToken = "строчка refreshToken";
//            Random random = new Random();
//            if (random.nextBoolean()) {
//                emitter.onSuccess(tokenResponse);
//            } else {
//                emitter.onError(new IllegalStateException("сообщение об ошибке"));
//            }
//
//        });
        return loginApi.token(email, password,"password");
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
