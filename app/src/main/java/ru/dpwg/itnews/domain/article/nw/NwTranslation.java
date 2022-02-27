package ru.dpwg.itnews.domain.article.nw;

import java.util.List;

public class NwTranslation {
    public int id;
    public int langId;
    public int articleId;
    public String title;
    public String shortDescription;
    public String imageUrl;
    public String publishedDate;
    public List<NwVersion> versions;
}
