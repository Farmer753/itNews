package ru.dpwg.itnews.domain.article.db;

import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import ru.dpwg.itnews.domain.article.NwVersion;

@Entity(tableName = "translations")
public class DbTranslation {
    @PrimaryKey
    public int id;
    @ColumnInfo(name = "lang_id")
    public int langId;
    @ColumnInfo(name = "article_id")
    public int articleId;
    public String title;
    @ColumnInfo(name = "short_description")
    public String shortDescription;
    @ColumnInfo(name = "image_url")
    public String imageUrl;
    @ColumnInfo(name = "published_date")
    public String publishedDate;
    @Ignore
    public List<DbVersion> versions;
}
