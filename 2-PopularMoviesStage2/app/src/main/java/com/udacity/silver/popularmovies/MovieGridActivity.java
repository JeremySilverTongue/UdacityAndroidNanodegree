package com.udacity.silver.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.udacity.silver.popularmovies.MovieGridActivityFragment.MovieSelectedListener;

import info.movito.themoviedbapi.model.MovieDb;

public class MovieGridActivity extends AppCompatActivity implements MovieSelectedListener {

    public static final String TAG = MovieGridActivity.class.getName();

    boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_grid);

        if (findViewById(R.id.movie_details_container) != null) {
            twoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_details_container, new MovieDetailsFragment())
                        .commit();
            }
        } else {
            twoPane = false;
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_grid, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, MovieSettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void movieSelected(MovieDb movie) {
        Log.d(TAG, "I sure would like to help you learn more about that movie");
        if (movie != null) {
            if (twoPane) {
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
        } else {
            Log.d(TAG, "How the crap did this get called with a null movie?");
        }
    }
}
