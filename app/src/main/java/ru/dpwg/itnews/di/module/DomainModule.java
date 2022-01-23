package ru.dpwg.itnews.di.module;

import ru.dpwg.itnews.data.SessionRepositoryImpl;
import ru.dpwg.itnews.data.UserRepositoryImpl;
import ru.dpwg.itnews.domain.SessionRepository;
import ru.dpwg.itnews.domain.user.UserRepository;
import toothpick.config.Module;

public class DomainModule extends Module {
    public DomainModule() {
        bind(SessionRepository.class).to(SessionRepositoryImpl.class).singleton();
        bind(UserRepository.class).to(UserRepositoryImpl.class).singleton();
    }
}
