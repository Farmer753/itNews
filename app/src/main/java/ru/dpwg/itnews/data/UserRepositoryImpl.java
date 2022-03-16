package ru.dpwg.itnews.data;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import ru.dpwg.itnews.domain.user.NwUser;
import ru.dpwg.itnews.domain.user.UserApi;
import ru.dpwg.itnews.domain.user.UserRepository;

public class UserRepositoryImpl implements UserRepository {
    private UserApi userApi;

    @Inject
    public UserRepositoryImpl(UserApi userApi) {
        this.userApi = userApi;
    }

    @Override
    public Single<NwUser> loadUser() {
//        return Single.create(emitter -> {
//            Thread.sleep(2000);
//            NwUser nwUser = new NwUser();
//            nwUser.authorities = new ArrayList<>();
//            NwAuthority authority = new NwAuthority();
//            authority.authority = "USER";
//            NwAuthority authorityAdmin = new NwAuthority();
//            authorityAdmin.authority = "ADMIN";
//            nwUser.authorities.add(authority);
//            nwUser.authorities.add(authorityAdmin);
//            nwUser.email = "liudmilalyagina@gmail.com";
//            nwUser.fullName = "Людмила Шевчук";
//            nwUser.avatar = "https://lh3.googleusercontent.com/a-/AOh14Gj3ukh3ZjYqzHljegP2jYkCm0FF3w0zSAFa9lVD=s96-c";
//
//            Random random = new Random();
//            if (random.nextBoolean()) {
//                emitter.onSuccess(nwUser);
//            } else {
//                emitter.onError(new IllegalStateException("сообщение об ошибке"));
//            }
//
//        });
        return userApi.getUser();
    }
}
