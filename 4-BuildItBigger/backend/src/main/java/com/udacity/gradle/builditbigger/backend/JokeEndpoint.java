package com.udacity.gradle.builditbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.udacity.gradle.builditbigger.jokesource.JokeSource;

@Api(name = "myApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "backend.builditbigger.gradle.udacity.com", ownerName = "backend.builditbigger.gradle.udacity.com", packagePath = ""))
public class JokeEndpoint {

    @ApiMethod(name = "tellJoke")
    public MyBean tellJoke() {
        MyBean response = new MyBean();
        JokeSource jokeSource = new JokeSource();
        String joke = jokeSource.getJoke();
        response.setData(joke);
        return response;
    }
}

