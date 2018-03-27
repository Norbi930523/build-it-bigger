package com.udacity.norbi930523.builditbigger.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.udacity.norbi930523.builditbigger.R;
import com.udacity.norbi930523.builditbigger.jokedisplayer.activity.JokeDisplayActivity;
import com.udacity.norbi930523.builditbigger.loader.JokeLoader;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private static final int JOKE_LOADER_ID = 100;

    private ProgressBar loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingIndicator = findViewById(R.id.loadingIndicator);

    }

    public void tellJoke(View view) {
        loadingIndicator.setVisibility(View.VISIBLE);

        getSupportLoaderManager().initLoader(JOKE_LOADER_ID, null, this).forceLoad();

    }


    @Override
    public Loader<String> onCreateLoader(int id, Bundle bundle) {
        return new JokeLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        loadingIndicator.setVisibility(View.GONE);

        Intent jokeDisplayIntent = JokeDisplayActivity.getIntent(this, data);
        startActivity(jokeDisplayIntent);

        /* https://stackoverflow.com/a/21419122 */
        getSupportLoaderManager().destroyLoader(JOKE_LOADER_ID);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
