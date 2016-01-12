package com.udacity.silver.popularmovies.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;


public class GetNowPlayingTask extends AsyncTask<String, Void, List<MovieDb>> {

    public static final String LOG_TAG = GetNowPlayingTask.class.getName();
    private NowPlayingReceiver receiver;

    public GetNowPlayingTask(NowPlayingReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    protected List<MovieDb> doInBackground(String... strings) {
        if (strings[0].isEmpty()){
            Log.e(LOG_TAG, "No API key");
            return null;
        }
        Log.d(LOG_TAG,strings[0]);

        TmdbApi api = new TmdbApi(strings[0]);

        TmdbMovies movies = api.getMovies();
        MovieResultsPage nowPlaying = movies.getPopularMovieList("", 1);
        return nowPlaying.getResults();
    }

    @Override
    protected void onPostExecute(List<MovieDb> results) {
        receiver.receiveNowPlaying(results);
    }

    public interface NowPlayingReceiver {
        void receiveNowPlaying(List<MovieDb> nowPlaying);
    }
}
