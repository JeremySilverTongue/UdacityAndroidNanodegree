package com.udacity.silver.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import info.movito.themoviedbapi.model.MovieDb;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String MOVIE_EXTRA = "Movie Extra";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        if (savedInstanceState == null){
            Bundle arguments = new Bundle();

            MovieDb movie = (MovieDb) getIntent().getSerializableExtra(MOVIE_EXTRA);

            arguments.putSerializable(MOVIE_EXTRA, movie);


            MovieDetailsActivityFragment fragment = new MovieDetailsActivityFragment();
            fragment.setArguments(arguments);


            getSupportFragmentManager().beginTransaction().add(R.id.movie_details_container,fragment).commit();

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_details, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
