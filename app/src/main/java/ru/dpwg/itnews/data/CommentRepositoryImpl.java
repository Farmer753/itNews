package ru.dpwg.itnews.data;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import ru.dpwg.itnews.domain.comment.CommentApi;
import ru.dpwg.itnews.domain.comment.CommentRepository;
import ru.dpwg.itnews.domain.comment.NwComment;

public class CommentRepositoryImpl implements CommentRepository {
    private CommentApi commentApi;
    @Inject
    public CommentRepositoryImpl(CommentApi commentApi) {
        this.commentApi = commentApi;
    }

    @Override
    public Single<NwComment> addComment(int id, String text) {
        return commentApi.addComment(id,text);
    }

    @Override
    public Single<List<NwComment>> loadComment(int offset, int limit, int id) {
        return commentApi.loadComments(limit, offset, id);
    }

    @Override
    public Single<Boolean> deleteComment(int idComment) {
        return commentApi.deleteComment(idComment);
    }


}
