package ru.dpwg.itnews.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import ru.dpwg.itnews.domain.article.ArticleApi;
import ru.dpwg.itnews.domain.article.ArticleRepository;
import ru.dpwg.itnews.domain.article.nw.NwArticle;
import ru.dpwg.itnews.domain.article.nw.NwTranslation;
import ru.dpwg.itnews.domain.article.nw.NwVersion;
import ru.dpwg.itnews.domain.article.db.ArticleDao;
import ru.dpwg.itnews.domain.article.db.DbArticle;

public class ArticleRepositoryImpl implements ArticleRepository {

    private ArticleDao articleDao;
    private ArticleApi articleApi;

    @Inject
    public ArticleRepositoryImpl(ArticleDao articleDao, ArticleApi articleApi) {
        this.articleDao = articleDao;
        this.articleApi = articleApi;
    }

    @Override
    public Single<NwArticle> loadArticleById(int id) {
//        return Single.create(emitter -> {
//            Thread.sleep(2000);
//
//            Random random = new Random();
//            if (random.nextBoolean()) {
//                emitter.onSuccess(generateArticle(id, true));
//            } else {
//                emitter.onError(new IllegalStateException("сообщение об ошибке"));
//            }
//        });

        return articleApi.loadArticleById(id);
    }

    @Override
    public Single<List<NwArticle>> loadArticles(int limit, int offset) {
//        return Single.create(emitter -> {
//            Thread.sleep(2000);
//            Random random = new Random();
//            if (random.nextBoolean()) {
//                List<NwArticle> articleList = new ArrayList<>();
//                for (int i = 0; i < limit; i++) {
//                    articleList.add(generateArticle(
//                            i + offset,
//                            (i + offset) == 0)
//                    );
//                }
//                emitter.onSuccess(articleList);
//            } else {
//                emitter.onError(new IllegalStateException("сообщение об ошибке"));
//            }
//        });
        return articleApi.loadArticles(limit, offset, true)
                .map(articlesResponse -> articlesResponse.articles);
    }

    @Override
    public Flowable<List<DbArticle>> getArticles() {
        return articleDao.findAllArticlesFull();
    }

    @Override
    public void insertArticles(List<DbArticle> dbArticles) {
        articleDao.insertArticlesFull(dbArticles);
    }

    @Override
    public void deleteAll() {
        articleDao.deleteAll();
    }

    @Override
    public Single<DbArticle> getArticleById(int id) {
        return articleDao.getArticleById(id);
    }

    @Override
    public void insertArticle(DbArticle article) {
        articleDao.insertArticleFull(article);
    }

    private NwArticle generateArticle(int id, boolean generateTextVersion) {
        NwArticle nwArticle = new NwArticle();
        nwArticle.id = id;
        nwArticle.originalLangId = 2;
        nwArticle.sourceTitle = "Title " + id;
        nwArticle.sourceUrl = "https://www.reddit.com/r/androiddev/comments/skch98/my_google_play_publisher_account";
        nwArticle.sourceAuthorName = "Andrey Melnikov";
        nwArticle.publishedDate = "2022-02-04T16:14:00.000Z";
        nwArticle.translations = new ArrayList<>();
        NwTranslation nwTranslation = new NwTranslation();
        nwArticle.translations.add(nwTranslation);
        nwTranslation.articleId = id;
        nwTranslation.id = id * 10;
        nwTranslation.langId = 2;
        nwTranslation.title = "Title " + id;
        nwTranslation.shortDescription = "I have been developing android apps since 2017";
        nwTranslation.imageUrl = "image/10/jgfj3szt8tf81-1644059661975.jpg";
        nwTranslation.publishedDate = "2022-02-05T14:14:47.285Z";
        nwTranslation.versions = new ArrayList<>();
        if (generateTextVersion) {
            NwVersion nwVersion = new NwVersion();
            nwTranslation.versions.add(nwVersion);
            nwVersion.id = id * 100;
            nwVersion.articleTranslationId = id * 10;
            nwVersion.text = "Текст статьи";
            nwVersion.publishedDate = "2022-02-05T14:14:47.285Z";
        }
        if (generateTextVersion) {
            NwTranslation nwTranslation1 = new NwTranslation();
            nwArticle.translations.add(nwTranslation1);
            nwTranslation1.articleId = id;
            nwTranslation1.id = id * 10 + 1;
            nwTranslation1.langId = 3;
            nwTranslation1.title = "Title " + id;
            nwTranslation1.shortDescription = "I have been developing android apps since 2017";
            nwTranslation1.imageUrl = "image/10/jgfj3szt8tf81-1644059661975.jpg";
            nwTranslation1.publishedDate = "2022-02-05T14:14:47.285Z";
            nwTranslation1.versions = new ArrayList<>();
            NwVersion nwVersion = new NwVersion();
            nwTranslation1.versions.add(nwVersion);
            nwVersion.id = id * 100 + 1;
            nwVersion.articleTranslationId = id * 10 + 1;
            nwVersion.text = "Текст статьи 1";
            nwVersion.publishedDate = "2022-02-05T14:14:47.285Z";
        }
        return nwArticle;
    }
}
