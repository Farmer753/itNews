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
import ru.dpwg.itnews.domain.article.ui.CommentApi;
import ru.dpwg.itnews.domain.session.LoginApi;
import toothpick.config.Module;

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
        CommentApi commentApi = retrofit.create(CommentApi.class);
        bind(CommentApi.class).toInstance(commentApi);

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
    }
}
