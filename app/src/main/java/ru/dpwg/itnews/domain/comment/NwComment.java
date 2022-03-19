package ru.dpwg.itnews.domain.comment;

import ru.dpwg.itnews.domain.user.NwUser;

public class NwComment {

    public int id;
    public String text;
    public int authorId;
    public int articleId;
    public String created;
    public NwUser author;

}
