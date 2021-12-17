package ru.dpwg.itnews;

import androidx.appcompat.app.AppCompatActivity;
import ru.dpwg.itnews.di.Di;
import timber.log.Timber;
import toothpick.Toothpick;

import android.content.SharedPreferences;
import android.os.Bundle;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timber.d("что угодно");
        Toothpick.openScope(Di.APP_SCOPE).inject(this);
    }

}