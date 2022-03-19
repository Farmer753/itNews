package ru.dpwg.itnews.di.module;

import android.util.Base64;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.dpwg.itnews.domain.article.ArticleApi;
import ru.dpwg.itnews.domain.comment.CommentApi;
import ru.dpwg.itnews.domain.session.LoginApi;
import ru.dpwg.itnews.domain.session.SessionRepository;
import ru.dpwg.itnews.domain.session.TokenResponse;
import ru.dpwg.itnews.domain.user.UserApi;
import timber.log.Timber;
import toothpick.Toothpick;
import toothpick.config.Module;

import static ru.dpwg.itnews.di.Di.APP_SCOPE;

public class NetworkModule extends Module {
    public NetworkModule() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://dont-play-with-google.com:8443/api/")
                .build();
        ArticleApi articleApi = retrofit.create(ArticleApi.class);
        bind(ArticleApi.class).toInstance(articleApi);

        Interceptor authAddAccessTokenInterceptor = new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                String cred = "client_id" + ":" + "client_secret";
                Request request = chain
                        .request()
                        .newBuilder()
                        .header(
                                "Authorization",
                                "Basic " + Base64.encodeToString(
                                        cred.getBytes(),
                                        Base64.NO_WRAP
                                )
                        )
                        .build();

                return chain.proceed(request);
            }
        };

        OkHttpClient authClient = new OkHttpClient.Builder()
                .addInterceptor(authAddAccessTokenInterceptor)
                .addInterceptor(logging).build();
        Retrofit authRetrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .baseUrl("https://dont-play-with-google.com:8443/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(authClient)
                .build();
        LoginApi loginApi = authRetrofit.create(LoginApi.class);
        bind(LoginApi.class).toInstance(loginApi);
//        UserApi
        @SuppressWarnings("Convert2Lambda")
        Interceptor expiredAccessTokenInterceptor = new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                System.out.println("status code: " + response.code());
                if (response.code() == 401) {
                    SessionRepository sessionRepository = Toothpick
                            .openScope(APP_SCOPE)
                            .getInstance(SessionRepository.class);
                    LoginApi authApi = Toothpick
                            .openScope(APP_SCOPE)
                            .getInstance(LoginApi.class);
                    String refreshToken = sessionRepository.getRefreshToken();
                    TokenResponse tokenResponse = authApi
                            .refreshToken(refreshToken, "refresh_token")
                            .blockingGet();
                    sessionRepository.saveRefreshToken(tokenResponse.refreshToken);
                    sessionRepository.saveAccessToken(tokenResponse.accessToken);
                    Request newRequest = chain
                            .request()
                            .newBuilder()
                            .header("Authorization", "Bearer " + sessionRepository.getAccessToken())
                            .build();
                    response.close();
                    response = chain.proceed(newRequest);
                }
                return response;
            }
        };
        @SuppressWarnings("Convert2Lambda")
        Interceptor addAccessTokenInterceptor = new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                SessionRepository sessionRepository = Toothpick
                        .openScope(APP_SCOPE)
                        .getInstance(SessionRepository.class);
                Request.Builder requestBuilder = chain.request().newBuilder();
                Timber.d("AccessToken %s", sessionRepository.getAccessToken());
                if (sessionRepository.getAccessToken()  != null){
                    requestBuilder = requestBuilder
                            .header("Authorization", "Bearer " + sessionRepository.getAccessToken());
                }
                return chain.proceed(requestBuilder.build());
            }
        };
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(addAccessTokenInterceptor)
                .addInterceptor(expiredAccessTokenInterceptor)
                .addInterceptor(logging)
                .build();
        Retrofit userRetrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .baseUrl("https://dont-play-with-google.com:8443/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        UserApi userApi = userRetrofit.create(UserApi.class);
        bind(UserApi.class).toInstance(userApi);
        CommentApi commentApi = userRetrofit.create(CommentApi.class);
        bind(CommentApi.class).toInstance(commentApi);
//UserApi END
    }
}
