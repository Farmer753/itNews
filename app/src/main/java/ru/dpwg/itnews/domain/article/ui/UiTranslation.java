package ru.dpwg.itnews.domain.article.ui;

import java.util.List;

public class UiTranslation {
    public int id;
    public int langId;
    public int articleId;
    public String title;
    public String shortDescription;
    public String imageUrl;
    public String publishedDate;
    public List<UiVersion> versions;
}
