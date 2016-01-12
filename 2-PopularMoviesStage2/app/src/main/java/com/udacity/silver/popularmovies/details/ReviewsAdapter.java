package com.udacity.silver.popularmovies.details;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.silver.popularmovies.R;
import com.udacity.silver.popularmovies.details.ReviewsAdapter.ReviewViewHolder;

import java.util.ArrayList;
import java.util.List;

import info.movito.themoviedbapi.model.Reviews;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

    public static final String LOG_TAG = ReviewsAdapter.class.getName();

    private List<Reviews> reviews;

    public ReviewsAdapter() {
        reviews = new ArrayList<>();
    }

    public void setReviews(List<Reviews> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_review, parent, false);
        return new ReviewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        Log.d(LOG_TAG, "Populating position: " + position);
        holder.author.setText(reviews.get(position).getAuthor());
        holder.content.setText(reviews.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
//        return 10;
    }

    public static final class ReviewViewHolder extends RecyclerView.ViewHolder {
        public TextView author;
        public TextView content;


        public ReviewViewHolder(View view) {
            super(view);
            author = (TextView) view.findViewById(R.id.review_author);
            content = (TextView) view.findViewById(R.id.review_content);
        }
    }
}
