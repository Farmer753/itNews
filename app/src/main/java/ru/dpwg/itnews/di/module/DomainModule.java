package ru.dpwg.itnews.di.module;

import ru.dpwg.itnews.data.SessionRepositoryImpl;
import ru.dpwg.itnews.domain.SessionRepository;
import toothpick.config.Module;

public class DomainModule extends Module {
    public DomainModule() {
        bind(SessionRepository.class).to(SessionRepositoryImpl.class).singleton();
    }
}
