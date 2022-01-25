package ru.dpwg.itnews;

import com.github.terrakok.cicerone.Screen;
import com.github.terrakok.cicerone.androidx.FragmentScreen;

import org.jetbrains.annotations.NotNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import ru.dpwg.itnews.ui.ArticleFragment;
import ru.dpwg.itnews.ui.ArticleListFragment;
import ru.dpwg.itnews.ui.LoginFragment;
import ru.dpwg.itnews.ui.OnboardingFragment;
import ru.dpwg.itnews.ui.ProfileFragment;

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
    public static class ArticleListScreen implements FragmentScreen {


        @NotNull
        @Override
        public Fragment createFragment(@NotNull FragmentFactory fragmentFactory) {
            return new ArticleListFragment();
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

    public static class LoginScreen implements FragmentScreen {


        @NotNull
        @Override
        public Fragment createFragment(@NotNull FragmentFactory fragmentFactory) {
            return new LoginFragment();
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

    public static class ProfileScreen implements FragmentScreen {


        @NotNull
        @Override
        public Fragment createFragment(@NotNull FragmentFactory fragmentFactory) {
            return new ProfileFragment();
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

    public static class ArticleScreen implements FragmentScreen {
        @NotNull
        @Override
        public Fragment createFragment(@NotNull FragmentFactory fragmentFactory) {
            return new ArticleFragment();
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
