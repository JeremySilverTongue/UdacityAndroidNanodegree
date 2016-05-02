package com.udacity.silver.popularmovies.grid;

import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;

public interface MovieListReceiver {
    void receiveMovies(List<MovieDb> movies);
}
