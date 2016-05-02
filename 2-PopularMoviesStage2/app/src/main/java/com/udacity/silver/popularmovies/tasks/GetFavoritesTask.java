package com.udacity.silver.popularmovies.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.udacity.silver.popularmovies.grid.MovieListReceiver;
import com.udacity.silver.popularmovies.prefs.MoviePrefs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;


public class GetFavoritesTask extends AsyncTask<String, Void, List<MovieDb>> {

    public static final String LOG_TAG = GetFavoritesTask.class.getName();
    private Context context;
    private MovieListReceiver receiver;

    public GetFavoritesTask(Context context, MovieListReceiver receiver) {
        this.context = context;
        this.receiver = receiver;
    }

    @Override
    protected List<MovieDb> doInBackground(String... strings) {
        if (strings[0].isEmpty()) {
            Log.e(LOG_TAG, "No API key");
            return null;
        }
        Log.d(LOG_TAG, strings[0]);

        TmdbMovies moviesApi = new TmdbApi(strings[0]).getMovies();

        ArrayList<MovieDb> movies = new ArrayList<>();

        Set<Integer> favs = MoviePrefs.getFavorites(context);

        for (Integer fav : favs) {
            movies.add(moviesApi.getMovie(fav, ""));
        }

        return movies;
    }

    @Override
    protected void onPostExecute(List<MovieDb> results) {
        receiver.receiveMovies(results);
    }
}
