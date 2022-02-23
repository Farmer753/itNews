package ru.dpwg.itnews.domain.article.db;

import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import ru.dpwg.itnews.domain.article.NwTranslation;

@Entity(tableName = "articles")
public class DbArticle {
    @PrimaryKey
    public int id;
    @ColumnInfo(name = "original_lang_id")
    public int originalLangId;
    @ColumnInfo(name = "source_title")
    public String sourceTitle;
    @ColumnInfo(name = "source_url")
    public String sourceUrl;
    @ColumnInfo(name = "source_author_name")
    public String sourceAuthorName;
    @ColumnInfo(name = "published_data")
    public String publishedDate;
    @Ignore
    public List<DbTranslation> translations;
}
