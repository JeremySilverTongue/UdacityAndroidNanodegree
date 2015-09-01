package com.udacity.silver.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.silver.popularmovies.MovieGridAdapter.MovieViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import info.movito.themoviedbapi.model.MovieDb;

public class MovieGridAdapter extends RecyclerView.Adapter<MovieViewHolder> implements OnSharedPreferenceChangeListener {

    public static final String LOG_TAG = MovieGridAdapter.class.getName();
    Context context;
    ArrayList<MovieDb> movies;
    private int width;
    private int height;

    public ArrayList<MovieDb> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<MovieDb> movies) {
        checkSortOrder();
        this.movies = movies;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Log.d(LOG_TAG, "Preferences updated");
        checkSortOrder();
    }

    public static final class MovieViewHolder extends RecyclerView.ViewHolder {
        public ImageView moviePoster;
        public Context context;
        public MovieViewHolder(View view, Context context){
            super(view);
            moviePoster = (ImageView) view.findViewById(R.id.poster);
            this.context = context;
        }
    }

    public MovieGridAdapter(Context context, int width, int height){
        Log.d(LOG_TAG, "Can you see my log messages?");
        this.context = context;
        this.width = width;
        this.height = height;
        movies = new ArrayList<>();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie, parent, false);
        return new MovieViewHolder(v,parent.getContext());
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        String baseUrl = holder.context.getString(R.string.base_URL);
        String size = holder.context.getString(R.string.thumb_size);
        String URL = baseUrl + size + movies.get(position).getPosterPath();

        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra(MovieDetailsActivity.MOVIE_EXTRA, movies.get(position));
                context.startActivity(intent);

            }
        });
//        final ImageView poster = holder.moviePoster;
//        holder.moviePoster.setImageResource(R.drawable.marked_placeholder);


//        holder.moviePoster.setVisibility(View.INVISIBLE);
//        Glide.clear(holder.moviePoster);
        Picasso.with(holder.context).load(URL).placeholder(R.drawable.placeholder).into(holder.moviePoster);

//        Glide.with(holder.context).load(URL).into(holder.moviePoster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    private void checkSortOrder(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String sortOrder = preferences.getString(
                context.getString(R.string.pref_sort_order_key),
                context.getString(R.string.pref_most_popular)
        );
        if (sortOrder.equals(context.getString(R.string.pref_most_popular))) {
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
}
