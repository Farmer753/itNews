package ru.dpwg.itnews.domain.article.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "versions")
public class DbVersion {
    @PrimaryKey
    public int id;
    @ColumnInfo(name = "article_translation_id")
    public int articleTranslationId;
    public String text;
    @ColumnInfo(name = "published_date")
    public String publishedDate;
}
