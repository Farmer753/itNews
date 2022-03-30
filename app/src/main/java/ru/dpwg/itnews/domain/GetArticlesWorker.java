package ru.dpwg.itnews.domain;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import ru.dpwg.itnews.di.Di;
import ru.dpwg.itnews.domain.article.ArticleApi;
import ru.dpwg.itnews.domain.article.DbArticleConverter;
import ru.dpwg.itnews.domain.article.db.ArticleDao;
import ru.dpwg.itnews.domain.article.db.DbArticle;
import ru.dpwg.itnews.ui.util.NotificationUtil;
import timber.log.Timber;
import toothpick.Toothpick;

public class GetArticlesWorker extends Worker {
    @Inject
    ArticleApi api;

    @Inject
    ArticleDao articleDao;

    @Inject
    NotificationUtil notificationUtil;


    public GetArticlesWorker(@NonNull @NotNull Context context,
                             @NonNull @NotNull WorkerParameters workerParams
    ) {
        super(context, workerParams);
        Toothpick.openScope(Di.APP_SCOPE).inject(this);
    }

    @NonNull
    @NotNull
    @Override
    public Result doWork() {
        Timber.d("Работа началась");
//        без гарантий, можно заменить на false
        List<DbArticle> dbArticlesFromServer = api.loadArticles(5, 0, true)
                .map(articlesResponse -> articlesResponse.articles)
                .map(nwArticles -> new DbArticleConverter().convert(nwArticles))
                .blockingGet();
        Timber.d(dbArticlesFromServer + "");
        Timber.d("Работает");
        List<DbArticle> dbArticles = (List<DbArticle>) articleDao.findAllArticlesFull();
        List<DbArticle> newArticles = new ArrayList<>();
        for (DbArticle dbArticleFromServer : dbArticlesFromServer) {
            boolean contains = dbArticles.contains(dbArticleFromServer);
            //            if (!contains){
            if (contains == false) {
                newArticles.add(dbArticleFromServer);
            }
            Timber.d("Статья с заголовком "
                    + dbArticleFromServer.sourceTitle
                    + " содержится в базе"
                    + contains);
        }
        Timber.d("New articles " + newArticles);
        if (newArticles.isEmpty() == false) {
            String text = "";
            for (DbArticle dbArticle : newArticles) {
                text += dbArticle.sourceTitle + "\n";
            }
            notificationUtil.showNotification(1, "Новые статьи", text);
        }

        articleDao.insertArticlesFull(newArticles);
        return Result.success();
    }
}
