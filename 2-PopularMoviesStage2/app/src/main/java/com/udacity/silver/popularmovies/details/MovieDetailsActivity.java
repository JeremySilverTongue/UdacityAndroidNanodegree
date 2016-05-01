package com.udacity.silver.popularmovies.details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.udacity.silver.popularmovies.R;

import info.movito.themoviedbapi.model.MovieDb;

public class MovieDetailsActivity extends AppCompatActivity {

    public final static String TAG = MovieDetailsActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            MovieDb movie = (MovieDb) getIntent().getSerializableExtra(MovieDetailsFragment.MOVIE_EXTRA);
            arguments.putSerializable(MovieDetailsFragment.MOVIE_EXTRA, movie);
            MovieDetailsFragment fragment = new MovieDetailsFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.movie_details_container, fragment).commit();
        }
    }
}
