package com.udacity.norbi930523.builditbigger.jokegenerator;

import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class JokeGenerator {

    private static final Gson GSON = new Gson();

    public String getJoke(){
        String jokesJson = getJokesJson();

        String[] jokesArray = GSON.fromJson(jokesJson, String[].class);

        int jokeIndex = (int) (Math.random() * 100) % jokesArray.length;

        return jokesArray[jokeIndex];
    }

    private String getJokesJson() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("jokes.json");

        try {
            return IOUtils.toString(is, Charset.forName("UTF-8"));
        } catch (IOException e) {
            return "[]";
        }
    }

}
