package ru.dpwg.itnews.data;

import java.util.ArrayList;
import java.util.Random;

import io.reactivex.rxjava3.core.Single;
import ru.dpwg.itnews.domain.TokenResponse;
import ru.dpwg.itnews.domain.user.NwAuthority;
import ru.dpwg.itnews.domain.user.NwUser;
import ru.dpwg.itnews.domain.user.UserRepository;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public Single<NwUser> loadUser() {
        return Single.create(emitter -> {
            Thread.sleep(2000);
            NwUser nwUser = new NwUser();
            nwUser.authorities = new ArrayList<>();
            NwAuthority authority = new NwAuthority();
            tokenResponse.refreshToken = "строчка refreshToken";
            Random random = new Random();
            if (random.nextBoolean()) {
                emitter.onSuccess(tokenResponse);
            } else {
                emitter.onError(new IllegalStateException("сообщение об ошибке"));
            }

        });
    }
}
