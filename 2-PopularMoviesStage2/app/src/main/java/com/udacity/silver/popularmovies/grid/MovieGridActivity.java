package com.udacity.silver.popularmovies.grid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.udacity.silver.popularmovies.R;
import com.udacity.silver.popularmovies.details.MovieDetailsActivity;
import com.udacity.silver.popularmovies.details.MovieDetailsFragment;
import com.udacity.silver.popularmovies.grid.MovieGridFragment.MovieSelectedListener;
import com.udacity.silver.popularmovies.prefs.MovieSettingsActivity;

import info.movito.themoviedbapi.model.MovieDb;

public class MovieGridActivity extends AppCompatActivity implements MovieSelectedListener {

    public static final String TAG = MovieGridActivity.class.getName();
    public static final String SELECTED_MOVIE_KEY = "Selected movie";

    private boolean twoPane;
    private MovieDb selectedMovie;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(SELECTED_MOVIE_KEY, selectedMovie);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_grid);

        if (findViewById(R.id.movie_details_container) != null) {
            twoPane = true;
            if (savedInstanceState != null) {
                MovieDb movie = (MovieDb) savedInstanceState.getSerializable(SELECTED_MOVIE_KEY);
                movieSelected(movie);
            }
        } else {
            twoPane = false;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_grid, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, MovieSettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void movieSelected(MovieDb movie) {
        if (movie != null) {
            if (twoPane) {
                selectedMovie = movie;
                Bundle arguments = new Bundle();
                arguments.putSerializable(MovieDetailsFragment.MOVIE_EXTRA, movie);
                MovieDetailsFragment fragment = new MovieDetailsFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction().replace(R.id.movie_details_container, fragment).commit();
            } else {
                Intent intent = new Intent(this, MovieDetailsActivity.class);
                intent.putExtra(MovieDetailsFragment.MOVIE_EXTRA, movie);
                startActivity(intent);
            }
        }
    }

}