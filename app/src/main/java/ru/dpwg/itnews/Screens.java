package ru.dpwg.itnews;

import com.github.terrakok.cicerone.androidx.FragmentScreen;

import org.jetbrains.annotations.NotNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import ru.dpwg.itnews.ui.OnboardingFragment;

public class Screens {


    public static class OnboardingScreen implements FragmentScreen {


        @NotNull
        @Override
        public Fragment createFragment(@NotNull FragmentFactory fragmentFactory) {
            return new OnboardingFragment();
        }

        @Override
        public boolean getClearContainer() {
            return true;
        }

        @NotNull
        @Override
        public String getScreenKey() {
            return this.getClass().getName();
        }
    }

}
