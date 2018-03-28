package com.udacity.norbi930523.builditbigger.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.udacity.norbi930523.builditbigger.R;
import com.udacity.norbi930523.builditbigger.jokedisplayer.activity.JokeDisplayActivity;
import com.udacity.norbi930523.builditbigger.loader.JokeLoader;
import com.udacity.norbi930523.builditbigger.testutil.SimpleIdlingResource;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private static final int JOKE_LOADER_ID = 100;

    private SimpleIdlingResource simpleIdlingResource;

    private ProgressBar loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingIndicator = findViewById(R.id.loadingIndicator);

    }

    public void tellJoke(View view) {
        if(isOnline()){
            loadingIndicator.setVisibility(View.VISIBLE);

            getSupportLoaderManager().initLoader(JOKE_LOADER_ID, null, this).forceLoad();

            if(simpleIdlingResource != null){
                simpleIdlingResource.setIdleState(false);
            }
        } else {
            Toast.makeText(this, R.string.message_offline, Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public Loader<String> onCreateLoader(int id, Bundle bundle) {
        return new JokeLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        loadingIndicator.setVisibility(View.GONE);

        if(data != null){
            Intent jokeDisplayIntent = JokeDisplayActivity.getIntent(this, data);
            startActivity(jokeDisplayIntent);

            if(simpleIdlingResource != null){
                simpleIdlingResource.setData(data);
                simpleIdlingResource.setIdleState(true);
            }
        } else {
            Toast.makeText(this, R.string.message_no_data, Toast.LENGTH_LONG).show();
        }

        /* https://stackoverflow.com/a/21419122 */
        getSupportLoaderManager().destroyLoader(JOKE_LOADER_ID);

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    /**
     * From https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out
     */
    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public SimpleIdlingResource getIdlingResource() {
        if (simpleIdlingResource == null) {
            simpleIdlingResource = new SimpleIdlingResource();
        }
        return simpleIdlingResource;
    }
}
