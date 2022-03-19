package ru.dpwg.itnews.domain.comment;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface CommentRepository {

    Single<NwComment> addComment(int id, String comment);
    Single<List<NwComment>> loadComment(int offset,int limit, int id);
}