package com.udacity.silver.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import info.movito.themoviedbapi.model.MovieDb;

/**
 * Created by silver on 8/23/15.
 */
public class MovieGridAdapter extends BaseAdapter {

    public static final String LOG_TAG = MovieGridAdapter.class.getName();

    private Context mContext;
    public ArrayList<MovieDb> movies;
    private int columnWidth;

    public void setMovies(ArrayList<MovieDb> movies) {
        this.movies = movies;
        this.notifyDataSetChanged();
    }

    public MovieGridAdapter(Context context, int columnWidth) {
        this.mContext = context;
        this.movies = new ArrayList<MovieDb>();
        this.columnWidth = columnWidth;
    }

    @Override
    public int getCount() {
        Log.d(LOG_TAG, "Got asked for a count, gave:" + movies.size());
        return movies.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ImageView imageView;
        if (view == null) {
            imageView = new ImageView(mContext);

        } else {
            imageView = (ImageView) view;
        }
        String baseUrl = mContext.getString(R.string.base_URL);
        String size = mContext.getString(R.string.thumb_size);
        String URL = baseUrl + size + movies.get(i).getPosterPath();
        Log.d(LOG_TAG, URL);
        imageView.setVisibility(View.INVISIBLE);
        Picasso.with(mContext).load(URL).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                imageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                imageView.setVisibility(View.VISIBLE);
            }
        });


//        float width = imageView.getMeasuredWidth();
//        float height = imageView.getMeasuredHeight();


//        imageView.setLayoutParams(new GridView.LayoutParams(columnWidth, 1000));

//        imageView.setLayoutParams(new GridView.LayoutParams());

        return imageView;
    }
}
