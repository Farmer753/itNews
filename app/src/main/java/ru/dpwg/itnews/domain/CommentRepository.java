package ru.dpwg.itnews.domain;

import io.reactivex.rxjava3.core.Single;

public interface CommentRepository {

    Single<NwComment> add(int id, String comment);
}
