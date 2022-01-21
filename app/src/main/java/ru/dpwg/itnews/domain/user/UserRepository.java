package ru.dpwg.itnews.domain.user;

import io.reactivex.rxjava3.core.Single;

public interface UserRepository {

    Single<NwUser> loadUser();
}
