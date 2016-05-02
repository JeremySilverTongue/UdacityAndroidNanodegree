package com.udacity.silver.popularmovies.details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.udacity.silver.popularmovies.BuildConfig;
import com.udacity.silver.popularmovies.R;
import com.udacity.silver.popularmovies.prefs.MoviePrefs;
import com.udacity.silver.popularmovies.tasks.GetReviewsAndVideosTask;
import com.udacity.silver.popularmovies.tasks.GetReviewsAndVideosTask.ReviewsAndVideosReceiver;

import info.movito.themoviedbapi.model.MovieDb;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsFragment extends Fragment implements ReviewsAndVideosReceiver {

    public static final String LOG_TAG = MovieDetailsFragment.class.getName();

    public static final String MOVIE_EXTRA = "Movie Extra";

    MovieDb movie;

    private LinearLayout mainLayout;
    private CheckBox favoriteButton;
    private TextView titleView;
    private TextView releaseDateView;
    private ImageView poster;
    private RatingBar rating;
    private TextView plot;


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void receiveMovie(MovieDb movie) {
        titleView.setText(movie.getTitle());
        releaseDateView.setText(movie.getReleaseDate());


        rating.setRating(movie.getVoteAverage());
        plot.setText(movie.getOverview());

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        String baseUrl = getContext().getString(R.string.base_URL);
        String size = getContext().getString(R.string.thumb_size_large);
        if (movie != null) {
            String URL = baseUrl + size + movie.getPosterPath();
            Glide.with(getContext()).load(URL).into(poster);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_movie_details, container, false);

        Bundle arguments = getArguments();

        if (arguments != null) {
            movie = (MovieDb) arguments.getSerializable(MOVIE_EXTRA);
        } else {
            Log.d(LOG_TAG, "How the hell did you get created with no arguments?");
        }

        GetReviewsAndVideosTask reviewsAndVideosTask = new GetReviewsAndVideosTask(this, BuildConfig.MOVIE_DB_API_KEY);

        reviewsAndVideosTask.execute(movie.getId());


        mainLayout = (LinearLayout) root.findViewById(R.id.main_linear_layout);
        favoriteButton = (CheckBox) root.findViewById(R.id.favorite_button);
        titleView = (TextView) root.findViewById(R.id.title_and_date);
        releaseDateView = (TextView) root.findViewById(R.id.release_date);
        poster = (ImageView) root.findViewById(R.id.poster_view);
        rating = (RatingBar) root.findViewById(R.id.ratingBar);
        rating.setIsIndicator(true);
        plot = (TextView) root.findViewById(R.id.plot);

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoviePrefs.toggleFavorite(getContext(), movie.getId());
                favoriteButton.setChecked(MoviePrefs.isFavorite(getContext(), movie.getId()));
            }
        });


        favoriteButton.setChecked(MoviePrefs.isFavorite(getContext(), movie.getId()));


        return root;
    }
}
