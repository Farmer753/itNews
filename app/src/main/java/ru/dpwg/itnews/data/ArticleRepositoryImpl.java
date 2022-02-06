package ru.dpwg.itnews.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import ru.dpwg.itnews.domain.article.ArticleRepository;
import ru.dpwg.itnews.domain.article.NwArticle;
import ru.dpwg.itnews.domain.article.NwTranslation;
import ru.dpwg.itnews.domain.article.NwVersion;

public class ArticleRepositoryImpl implements ArticleRepository {
    @Inject
    public ArticleRepositoryImpl() {
    }

    @Override
    public Single<NwArticle> loadArticleById(int id) {
        return Single.create(emitter -> {
            Thread.sleep(2000);

            Random random = new Random();
            if (random.nextBoolean()) {
                emitter.onSuccess(generateArticle(id));
            } else {
                emitter.onError(new IllegalStateException("сообщение об ошибке"));
            }
        });
    }

    @Override
    public Single<List<NwArticle>> loadArticles(int limit, int offset) {
        return Single.create(emitter -> {
            Thread.sleep(2000);
            Random random = new Random();
            if (random.nextBoolean()) {
                List<NwArticle> articleList = new ArrayList<>();
                for (int i = 0; i < limit; i++){
                    articleList.add(generateArticle(i));
                }
                    emitter.onSuccess(articleList);
            } else {
                emitter.onError(new IllegalStateException("сообщение об ошибке"));
            }
        });
    }

    private NwArticle generateArticle(int id) {
        NwArticle nwArticle = new NwArticle();
        nwArticle.id = id;
        nwArticle.originalLangId = 2;
        nwArticle.sourceTitle = "My Google Play Publisher account with million downloads terminated after 5 years";
        nwArticle.sourceUrl = "https://www.reddit.com/r/androiddev/comments/skch98/my_google_play_publisher_account";
        nwArticle.sourceAuthorName = "Andrey Melnikov";
        nwArticle.publishedDate = "2022-02-04T16:14:00.000Z";
        nwArticle.translations = new ArrayList<>();
        NwTranslation nwTranslation = new NwTranslation();
        nwArticle.translations.add(nwTranslation);
        nwTranslation.articleId = id;
        nwTranslation.id = 1;
        nwTranslation.langId = 2;
        nwTranslation.title = "My Google Play Publisher account with million downloads terminated after 5 years";
        nwTranslation.shortDescription = "I have been developing android apps since 2017";
        nwTranslation.imageUrl = "image/10/jgfj3szt8tf81-1644059661975.jpg";
        nwTranslation.publishedDate = "2022-02-05T14:14:47.285Z";
        nwTranslation.versions = new ArrayList<>();
        NwVersion nwVersion = new NwVersion();
        nwTranslation.versions.add(nwVersion);
        nwVersion.id = 1;
        nwVersion.articleTranslationId = 1;
        nwVersion.text = "Текст статьи";
        nwVersion.publishedDate = "2022-02-05T14:14:47.285Z";
        return nwArticle;
    }
}
