package ru.dpwg.itnews.domain.article.db;

import java.util.List;
import java.util.Objects;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

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
    @ColumnInfo(name = "published_date")
    public String publishedDate;
    @Ignore
    public List<DbTranslation> translations;

    @Override
    public String toString() {
        return "DbArticle{" +
                "id=" + id +
                ", originalLangId=" + originalLangId +
                ", sourceTitle='" + sourceTitle + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", sourceAuthorName='" + sourceAuthorName + '\'' +
                ", publishedDate='" + publishedDate + '\'' +
                ", translations=" + translations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DbArticle dbArticle = (DbArticle) o;
        return Objects.equals(id, dbArticle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
