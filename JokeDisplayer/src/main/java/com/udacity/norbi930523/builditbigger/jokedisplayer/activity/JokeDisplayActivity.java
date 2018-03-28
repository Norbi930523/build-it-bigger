package com.udacity.norbi930523.builditbigger.jokedisplayer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.norbi930523.builditbigger.jokedisplayer.R;

public class JokeDisplayActivity extends AppCompatActivity {

    private static final String JOKE_TEXT_PARAM = "jokeText";

    public static Intent getIntent(Context context, String jokeText){
        Intent intent = new Intent(context, JokeDisplayActivity.class);
        intent.putExtra(JOKE_TEXT_PARAM, jokeText);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Bundle extras = getIntent().getExtras();

        if(extras == null){
            Toast.makeText(this, R.string.error_joke_not_found, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        String jokeTextStr = extras.getString(JOKE_TEXT_PARAM);

        if(jokeTextStr == null){
            Toast.makeText(this, R.string.error_joke_not_found, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        TextView jokeText = findViewById(R.id.jokeText);
        jokeText.setText(jokeTextStr);
    }

    /* From https://stackoverflow.com/a/37185334 */
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
