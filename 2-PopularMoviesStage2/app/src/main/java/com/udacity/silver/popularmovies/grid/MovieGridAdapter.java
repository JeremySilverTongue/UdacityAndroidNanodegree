package com.udacity.silver.popularmovies.grid;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.silver.popularmovies.R;
import com.udacity.silver.popularmovies.grid.MovieGridAdapter.MovieViewHolder;
import com.udacity.silver.popularmovies.grid.MovieGridFragment.MovieSelectedListener;
import com.udacity.silver.popularmovies.prefs.MoviePrefs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;

public class MovieGridAdapter extends RecyclerView.Adapter<MovieViewHolder> implements OnSharedPreferenceChangeListener {

    public static final String LOG_TAG = MovieGridAdapter.class.getName();
    final MovieSelectedListener movieSelectedListener;
    Context context;
    private List<MovieDb> movies;


    public MovieGridAdapter(Context context, MovieSelectedListener movieSelectedListener) {
        this.context = context;
        this.movieSelectedListener = movieSelectedListener;
        movies = new ArrayList<>();
    }

    public List<MovieDb> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieDb> movies) {
        checkSortOrder();
        this.movies = movies;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Log.d(LOG_TAG, "Preferences updated");
        notifyDataSetChanged();
        checkSortOrder();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.movie, parent, false);
        return new MovieViewHolder(v, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, final int position) {
        String baseUrl = context.getString(R.string.base_URL);
        String size = context.getString(R.string.thumb_size);
        String URL = baseUrl + size + movies.get(position).getPosterPath();
        final int id = getMovies().get(holder.getAdapterPosition()).getId();

        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                movieSelectedListener.movieSelected(movies.get(holder.getAdapterPosition()));

            }
        });


        holder.favoriteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MoviePrefs.toggleFavorite(context, id);
                holder.favoriteButton.setChecked(MoviePrefs.isFavorite(context, id));
            }
        });


        holder.favoriteButton.setChecked(MoviePrefs.isFavorite(context, id));


        Picasso.with(context).load(URL).placeholder(R.drawable.placeholder).into(holder.moviePoster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    private void checkSortOrder() {
        if (MoviePrefs.sortOrder(context).equals(context.getString(R.string.pref_sort_order_popular_key))) {
            Log.d(LOG_TAG, "Sorting by most popular");
            Collections.sort(movies, new Comparator<MovieDb>() {
                @Override
                public int compare(MovieDb movie1, MovieDb movie2) {
                    return movie1.getPopularity() > movie2.getPopularity() ? -1 : 1;
                }
            });
        } else {
            Log.d(LOG_TAG, "Sorting by vote average");
            Collections.sort(movies, new Comparator<MovieDb>() {
                @Override
                public int compare(MovieDb movie1, MovieDb movie2) {
                    return movie1.getVoteAverage() > movie2.getVoteAverage() ? -1 : 1;
                }
            });
        }
        notifyDataSetChanged();

    }

    public static final class MovieViewHolder extends RecyclerView.ViewHolder {

        public ImageView moviePoster;
        public CheckBox favoriteButton;


        public MovieViewHolder(View view, Context context) {
            super(view);
            moviePoster = (ImageView) view.findViewById(R.id.poster);
            favoriteButton = (CheckBox) view.findViewById(R.id.favorite_button);
        }
    }
}
