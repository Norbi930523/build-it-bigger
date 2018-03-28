package com.udacity.gradle.builditbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.udacity.norbi930523.builditbigger.jokegenerator.JokeGenerator;

import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(
        name = "jokesApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.udacity.com",
                ownerName = "backend.builditbigger.gradle.udacity.com",
                packagePath = ""
        )
)
public class JokesEndpoint {

    private static final JokeGenerator JOKE_GENERATOR = new JokeGenerator();

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "sayHi")
    public SimpleResponse sayHi(@Named("name") String name) {
        SimpleResponse response = new SimpleResponse();
        response.setData("Hi, " + name);

        return response;
    }

    @ApiMethod(name = "joke", httpMethod = "GET")
    public SimpleResponse joke() {
        SimpleResponse response = new SimpleResponse();
        response.setData(JOKE_GENERATOR.getJoke());
        return response;
    }

}
