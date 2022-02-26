package ru.dpwg.itnews.domain.article;

import java.util.ArrayList;

import javax.inject.Inject;

import ru.dpwg.itnews.domain.article.db.DbArticle;
import ru.dpwg.itnews.domain.article.db.DbTranslation;
import ru.dpwg.itnews.domain.article.db.DbVersion;

public class DbArticleConverter {

    @Inject
    public DbArticleConverter() {
    }

    public DbArticle convert(NwArticle data) {
        DbArticle dbArticle = new DbArticle();
        dbArticle.id = data.id;
        dbArticle.originalLangId = data.originalLangId;
        dbArticle.publishedDate = data.publishedDate;
        dbArticle.sourceAuthorName = data.sourceAuthorName;
        dbArticle.sourceTitle = data.sourceTitle;
        dbArticle.sourceUrl = data.sourceUrl;
        dbArticle.translations = new ArrayList<>();
        for (NwTranslation nwTranslation : data.translations) {
            dbArticle.translations.add(convert(nwTranslation));
        }
        return dbArticle;
    }

    public DbTranslation convert(NwTranslation data) {
        DbTranslation dbTranslation = new DbTranslation();
        dbTranslation.articleId = data.articleId;
        dbTranslation.id = data.id;
        dbTranslation.imageUrl = data.imageUrl;
        dbTranslation.langId = data.langId;
        dbTranslation.publishedDate = data.publishedDate;
        dbTranslation.shortDescription = data.shortDescription;
        dbTranslation.title = data.title;
        dbTranslation.versions = new ArrayList<>();
        for (NwVersion nwVersion : data.versions) {
            dbTranslation.versions.add(convert(nwVersion));
        }
        return dbTranslation;
    }

    public DbVersion convert(NwVersion data) {
        DbVersion dbVersion = new DbVersion();
        dbVersion.articleTranslationId = data.articleTranslationId;
        dbVersion.id = data.id;
        dbVersion.publishedDate = data.publishedDate;
        dbVersion.text = data.text;
        return dbVersion;
    }
}
