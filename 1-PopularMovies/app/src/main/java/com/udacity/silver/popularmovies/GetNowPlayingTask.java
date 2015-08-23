package com.udacity.silver.popularmovies;

import android.os.AsyncTask;
import android.util.Log;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

/**
 * Created by silver on 8/23/15.
 */
public class GetNowPlayingTask extends AsyncTask<String, Void, MovieResultsPage> {

    public static final String LOG_TAG = GetNowPlayingTask.class.getName();

    private NowPlayingReceiver receiver;

    public GetNowPlayingTask(NowPlayingReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    protected MovieResultsPage doInBackground(String... strings) {

        if (strings[0].isEmpty()){
            Log.e(LOG_TAG, "No API key");
            return null;
        }
        Log.d(LOG_TAG,strings[0]);
        TmdbMovies movies = new TmdbApi(strings[0]).getMovies();
        MovieResultsPage nowPlaying = movies.getNowPlayingMovies("", 1);
        return nowPlaying;
    }

    @Override
    protected void onPostExecute(MovieResultsPage resultsPage) {
        receiver.receiveNowPlaying(resultsPage);
    }

    interface NowPlayingReceiver {
        void receiveNowPlaying(MovieResultsPage nowPlaying);
    }
}
