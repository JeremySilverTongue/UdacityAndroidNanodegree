package com.udacity.silver.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.silver.popularmovies.GetNowPlayingTask.NowPlayingReceiver;

import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieGridActivityFragment extends Fragment implements NowPlayingReceiver {

    public static final String LOG_TAG = MovieGridActivityFragment.class.getName();
    MovieResultsPage nowPlaying;

    public MovieGridActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        String apiKey = getActivity().getString(R.string.mdbApiKey);
        GetNowPlayingTask moviesTask = new GetNowPlayingTask(this);
        moviesTask.execute(apiKey);
        return inflater.inflate(R.layout.fragment_movie_grid, container, false);
    }

    @Override
    public void receiveNowPlaying(MovieResultsPage nowPlaying) {
        for (MovieDb movie : nowPlaying){
            Log.d(LOG_TAG, movie.getTitle());
        }
    }
}
