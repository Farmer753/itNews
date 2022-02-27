package ru.dpwg.itnews.domain.article.ui;

import java.util.List;

public class UiArticle {
    public int id;
    public int originalLangId;
    public String sourceTitle;
    public String sourceUrl;
    public String sourceAuthorName;
    public String publishedDate;
    public List<UiTranslation> translations;
}
