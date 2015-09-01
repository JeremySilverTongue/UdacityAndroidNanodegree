package com.udacity.silver.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import info.movito.themoviedbapi.model.MovieDb;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsActivityFragment extends Fragment {

    public static final String LOG_TAG = MovieDetailsActivityFragment.class.getName();

    MovieDb movie;
    private TextView titleView;
    private TextView releaseDateView;
    private ImageView poster;
    private RatingBar rating;
    private TextView plot;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String baseUrl = getContext().getString(R.string.base_URL);
        String size = getContext().getString(R.string.thumb_size_large);
        String URL = baseUrl + size + movie.getPosterPath();
        Glide.with(getContext()).load(URL).into(poster);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null){
            movie = (MovieDb) arguments.getSerializable(MovieDetailsActivity.MOVIE_EXTRA);
        }

        View root = inflater.inflate(R.layout.fragment_movie_details, container, false);

        titleView = (TextView) root.findViewById(R.id.title_and_date);
        releaseDateView = (TextView) root.findViewById(R.id.release_date);
        poster = (ImageView) root.findViewById(R.id.poster_view);
        rating = (RatingBar) root.findViewById(R.id.ratingBar);
        plot = (TextView) root.findViewById(R.id.textView2);

        titleView.setText(movie.getTitle());
        releaseDateView.setText(movie.getReleaseDate());

        rating.setIsIndicator(true);
        rating.setRating(movie.getVoteAverage());
        plot.setText(movie.getOverview());

        return root;
    }
}
