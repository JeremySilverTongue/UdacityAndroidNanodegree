package com.udacity.silver.popularmovies.grid;

import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;

/**
 * Created by silver on 5/1/16.
 */
public interface MovieListReceiver {
    void receiveMovies(List<MovieDb> movies);
}
