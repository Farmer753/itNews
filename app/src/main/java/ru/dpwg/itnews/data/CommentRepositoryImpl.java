package ru.dpwg.itnews.data;

import java.util.ArrayList;
import java.util.Random;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import ru.dpwg.itnews.domain.CommentRepository;
import ru.dpwg.itnews.domain.NwComment;
import ru.dpwg.itnews.domain.TokenResponse;
import ru.dpwg.itnews.domain.user.NwAuthority;
import ru.dpwg.itnews.domain.user.NwUser;

public class CommentRepositoryImpl implements CommentRepository {

    @Inject
    public CommentRepositoryImpl() {
    }

    @Override
    public Single<NwComment> add(int id, String text) {
        return Single.create(emitter -> {
            Thread.sleep(2000);
            NwComment comment = new NwComment();
            comment.articleId = id;
            NwUser nwUser = new NwUser();
            nwUser.authorities = new ArrayList<>();
            NwAuthority authority = new NwAuthority();
            authority.authority = "USER";
            NwAuthority authorityAdmin = new NwAuthority();
            authorityAdmin.authority = "ADMIN";
            nwUser.authorities.add(authority);
            nwUser.authorities.add(authorityAdmin);
            nwUser.email = "liudmilalyagina@gmail.com";
            nwUser.fullName = "Людмила Шевчук";
            nwUser.avatar = "https://lh3.googleusercontent.com/a-/AOh14Gj3ukh3ZjYqzHljegP2jYkCm0FF3w0zSAFa9lVD=s96-c";
            comment.author = nwUser;
            comment.created = "2022-02-02T23:35:59.251Z";
            comment.authorId = 1;
            comment.text = text;
            Random random = new Random();
            if (random.nextBoolean()) {
                emitter.onSuccess(comment);
            } else {
                emitter.onError(new IllegalStateException("сообщение об ошибке"));
            }

        });
    }
}
