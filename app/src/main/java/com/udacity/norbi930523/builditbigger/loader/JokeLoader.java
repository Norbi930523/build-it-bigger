package com.udacity.norbi930523.builditbigger.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.jokesApi.JokesApi;
import com.udacity.norbi930523.builditbigger.BuildConfig;
import com.udacity.norbi930523.builditbigger.R;

import java.io.IOException;

/**
 * Created by Norbert Boros on 2018. 03. 27..
 */

public class JokeLoader extends AsyncTaskLoader<String> {

    private static final String TAG = JokeLoader.class.getSimpleName();

    private final JokesApi jokesApiService;

    public JokeLoader(Context context) {
        super(context);
        jokesApiService = buildApiService();
    }

    private JokesApi buildApiService(){
        JokesApi.Builder builder = new JokesApi.Builder(
                AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(),
                null);

        builder.setApplicationName(getContext().getString(R.string.app_name));

        // options for running against local devappserver
        // - 10.0.2.2 is localhost's IP address in Android emulator
        // - turn off compression when running against local devappserver
        builder.setRootUrl(BuildConfig.JOKE_ENDPOINT_URL)
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(BuildConfig.DEBUG);
                    }
                });
        // end options for devappserver

        return builder.build();
    }

    @Override
    public String loadInBackground() {
        try {
            return jokesApiService.joke().execute().getData();
        } catch (IOException e) {
            Log.e(TAG, "Failed to get joke from Jokes API", e);
            return "";
        }
    }
}
