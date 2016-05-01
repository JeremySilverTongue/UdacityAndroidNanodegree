package com.udacity.silver.popularmovies.grid;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.silver.popularmovies.BuildConfig;
import com.udacity.silver.popularmovies.R;
import com.udacity.silver.popularmovies.tasks.GetNowPlayingTask;
import com.udacity.silver.popularmovies.tasks.GetNowPlayingTask.NowPlayingReceiver;

import java.util.ArrayList;
import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;

public class MovieGridFragment extends Fragment implements NowPlayingReceiver {

    public static final String LOG_TAG = MovieGridFragment.class.getName();
    private static final int MIN_COLUMN_WIDTH = 200;
    private static final String SCROLL_POSITION_KEY = "scroll";
    ArrayList<MovieDb> nowPlaying;
    private MovieGridAdapter movieGridAdapter;
    private GridLayoutManager layoutManager;
    private int scrollPosition = 0;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movie_grid, container, false);

        if (savedInstanceState != null) {
            scrollPosition = savedInstanceState.getInt(SCROLL_POSITION_KEY, 0);
        }

        String apiKey = BuildConfig.MOVIE_DB_API_KEY;
        GetNowPlayingTask moviesTask = new GetNowPlayingTask(this);
        moviesTask.execute(apiKey);
        try {
            moviesTask.wait();
        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString());
        }

        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int columns = (int) dpWidth / MIN_COLUMN_WIDTH;
        columns = Math.max(columns, 2);


        Log.d(LOG_TAG, "We're making a movie grid adapter for some damn reason");
        movieGridAdapter = new MovieGridAdapter(getContext(), (MovieSelectedListener) getContext());


        mRecyclerView = (RecyclerView) root.findViewById(R.id.rv);
        mRecyclerView.setAdapter(movieGridAdapter);

        layoutManager = new GridLayoutManager(getContext(), columns);
        mRecyclerView.setLayoutManager(layoutManager);


        PreferenceManager.getDefaultSharedPreferences(getContext()).registerOnSharedPreferenceChangeListener(movieGridAdapter);
        return root;
    }

    @Override
    public void receiveNowPlaying(List<MovieDb> nowPlaying) {
        this.nowPlaying = new ArrayList<MovieDb>(nowPlaying);
        this.movieGridAdapter.setMovies(this.nowPlaying);
        this.movieGridAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SCROLL_POSITION_KEY, layoutManager.findFirstVisibleItemPosition());
    }

    public interface MovieSelectedListener {
        void movieSelected(MovieDb movie);
    }
}
