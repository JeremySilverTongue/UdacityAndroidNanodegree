package com.udacity.silver.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.udacity.silver.popularmovies.GetNowPlayingTask.NowPlayingReceiver;

import java.util.ArrayList;
import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieGridActivityFragment extends Fragment implements NowPlayingReceiver {

    public static final String LOG_TAG = MovieGridActivityFragment.class.getName();
    ArrayList<MovieDb> nowPlaying;
    private MovieGridAdapter movieGridAdapter;

    private GridView mGridView;

    public MovieGridActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movie_grid, container, false);
        mGridView = (GridView) root.findViewById(R.id.gridview);
        movieGridAdapter = new MovieGridAdapter(getContext(), mGridView.getColumnWidth());
        mGridView.setAdapter(movieGridAdapter);

        String apiKey = getActivity().getString(R.string.mdbApiKey);
        GetNowPlayingTask moviesTask = new GetNowPlayingTask(this);
        moviesTask.execute(apiKey);
        return root;
    }

    @Override
    public void receiveNowPlaying(List<MovieDb> nowPlaying) {
        this.nowPlaying = new ArrayList<MovieDb>(nowPlaying);
        this.movieGridAdapter.movies = this.nowPlaying;
        this.movieGridAdapter.notifyDataSetChanged();
//        this.nowPlaying = nowPlaying;
        for (MovieDb movie : nowPlaying){
            Log.d(LOG_TAG, movie.getTitle());
            Log.d(LOG_TAG, movie.getPosterPath());
        }
    }

    private void refreshPosters(){

    }
}
