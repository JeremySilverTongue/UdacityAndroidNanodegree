package com.udacity.silver.popularmovies.grid;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.silver.popularmovies.BuildConfig;
import com.udacity.silver.popularmovies.R;
import com.udacity.silver.popularmovies.prefs.MoviePrefs;
import com.udacity.silver.popularmovies.tasks.GetFavoritesTask;
import com.udacity.silver.popularmovies.tasks.GetNowPlayingTask;

import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;

public class MovieGridFragment extends Fragment implements MovieListReceiver {

    public static final String LOG_TAG = MovieGridFragment.class.getName();
    private static final int MIN_COLUMN_WIDTH = 200;
    private static final String SCROLL_POSITION_KEY = "scroll";

    private TextView errorTextView;
    private RecyclerView recyclerView;

    private MovieGridAdapter movieGridAdapter;
    private GridLayoutManager layoutManager;
    private int scrollPosition = 0;


    @Override
    public void onResume() {
        super.onResume();

        errorTextView.setText("");

        String apiKey = BuildConfig.MOVIE_DB_API_KEY;
        if (MoviePrefs.showFavorites(getContext())) {
            new GetFavoritesTask(getContext(), this).execute(apiKey);
        } else {
            new GetNowPlayingTask(this).execute(apiKey);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movie_grid, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.rv);
        errorTextView = (TextView) root.findViewById(R.id.error);

        if (savedInstanceState != null) {
            scrollPosition = savedInstanceState.getInt(SCROLL_POSITION_KEY, 0);
        }


        
        int columns =  root.getWidth() / MIN_COLUMN_WIDTH;
        columns = Math.max(columns, 2);




        movieGridAdapter = new MovieGridAdapter(getContext(), (MovieSelectedListener) getContext());


        recyclerView.setAdapter(movieGridAdapter);

        layoutManager = new GridLayoutManager(getContext(), columns);
        recyclerView.setLayoutManager(layoutManager);



        PreferenceManager.getDefaultSharedPreferences(getContext()).registerOnSharedPreferenceChangeListener(movieGridAdapter);
        return root;
    }


    public void receiveMovies(List<MovieDb> nowPlaying) {



            movieGridAdapter.setMovies(nowPlaying);
            movieGridAdapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(scrollPosition);

        if (nowPlaying.size() == 0 && MoviePrefs.showFavorites(getContext())) {
            errorTextView.setText(getString(R.string.error_no_favorites));
        }
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

