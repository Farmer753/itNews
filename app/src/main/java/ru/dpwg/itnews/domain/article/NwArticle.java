package ru.dpwg.itnews.domain.article;

import java.util.List;

public class NwArticle {
    public int id;
    public int originalLangId;
    public String sourceTitle;
    public String sourceUrl;
    public String sourceAuthorName;
    public String publishedDate;
    public List<NwTranslation> translations;


}
