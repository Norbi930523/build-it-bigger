package com.udacity.norbi930523.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.udacity.norbi930523.builditbigger.jokedisplayer.activity.JokeDisplayActivity;
import com.udacity.norbi930523.builditbigger.jokegenerator.JokeGenerator;


public class MainActivity extends AppCompatActivity {

    private static final JokeGenerator JOKE_GENERATOR = new JokeGenerator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void tellJoke(View view) {
        Intent jokeDisplayIntent = JokeDisplayActivity.getIntent(this, JOKE_GENERATOR.getJoke());
        startActivity(jokeDisplayIntent);
    }


}
