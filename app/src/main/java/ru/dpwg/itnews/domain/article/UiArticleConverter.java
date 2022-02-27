package ru.dpwg.itnews.domain.article;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.dpwg.itnews.domain.article.db.DbArticle;
import ru.dpwg.itnews.domain.article.db.DbTranslation;
import ru.dpwg.itnews.domain.article.db.DbVersion;
import ru.dpwg.itnews.domain.article.nw.NwArticle;
import ru.dpwg.itnews.domain.article.ui.UiArticle;
import ru.dpwg.itnews.domain.article.ui.UiTranslation;
import ru.dpwg.itnews.domain.article.ui.UiVersion;


public class UiArticleConverter {

    @Inject
    public UiArticleConverter() {
    }

    public UiArticle convert(DbArticle data) {
        UiArticle uiArticle = new UiArticle();
        uiArticle.id = data.id;
        uiArticle.originalLangId = data.originalLangId;
        uiArticle.publishedDate = data.publishedDate;
        uiArticle.sourceAuthorName = data.sourceAuthorName;
        uiArticle.sourceTitle = data.sourceTitle;
        uiArticle.sourceUrl = data.sourceUrl;
        uiArticle.translations = new ArrayList<>();
        for (DbTranslation nwTranslation : data.translations) {
            uiArticle.translations.add(convert(nwTranslation));
        }
        return uiArticle;
    }

    public UiTranslation convert(DbTranslation data) {
        UiTranslation uiTranslation = new UiTranslation();
        uiTranslation.articleId = data.articleId;
        uiTranslation.id = data.id;
        uiTranslation.imageUrl = data.imageUrl;
        uiTranslation.langId = data.langId;
        uiTranslation.publishedDate = data.publishedDate;
        uiTranslation.shortDescription = data.shortDescription;
        uiTranslation.title = data.title;
        uiTranslation.versions = new ArrayList<>();
        for (DbVersion nwVersion : data.versions) {
            uiTranslation.versions.add(convert(nwVersion));
        }
        return uiTranslation;
    }

    public UiVersion convert(DbVersion data) {
        UiVersion uiVersion = new UiVersion();
        uiVersion.articleTranslationId = data.articleTranslationId;
        uiVersion.id = data.id;
        uiVersion.publishedDate = data.publishedDate;
        uiVersion.text = data.text;
        return uiVersion;
    }

    public List<UiArticle> convert (List<DbArticle> data){
        List<UiArticle> uiArticles = new ArrayList<>();
        for (DbArticle dbArticle: data){
            uiArticles.add(convert(dbArticle));
        }
        return uiArticles;
    }
}
