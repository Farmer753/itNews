package ru.dpwg.itnews.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import androidx.annotation.NonNull;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.dpwg.itnews.R;
import ru.dpwg.itnews.di.Di;
import ru.dpwg.itnews.mvp.presenter.MainActivityPresenter;
import ru.dpwg.itnews.mvp.presenter.OnboardingPresenter;
import ru.dpwg.itnews.mvp.view.OnboardingView;
import toothpick.Toothpick;

public class OnboardingFragment extends MvpAppCompatFragment implements OnboardingView {

    @InjectPresenter
    OnboardingPresenter onboardingPresenter;

    Button button;

    @ProvidePresenter
    OnboardingPresenter getOnboardingPresenter() {
        return Toothpick.openScope(Di.APP_SCOPE).getInstance(OnboardingPresenter.class);
    }

    @Nullable
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_onboarding, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @androidx.annotation.Nullable @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button = view.findViewById(R.id.button);

        button.setOnClickListener(button -> {
            onboardingPresenter.onNextButtonClick();
        });
    }
}
