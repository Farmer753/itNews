package ru.dpwg.itnews.di.module;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.dpwg.itnews.domain.article.ArticleApi;
import ru.dpwg.itnews.domain.article.ui.CommentApi;
import toothpick.config.Module;

public class NetworkModule extends Module {
    public NetworkModule() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
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
    }
}
