package com.udacity.silver.popularmovies.tasks;

import android.os.AsyncTask;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbMovies.MovieMethod;
import info.movito.themoviedbapi.model.MovieDb;

public class GetReviewsAndVideosTask extends AsyncTask<Integer, Void, MovieDb> {

    private final ReviewsAndVideosReceiver receiver;
    private final String apiKey;


    public GetReviewsAndVideosTask(ReviewsAndVideosReceiver receiver, String apiKey) {
        this.receiver = receiver;
        this.apiKey = apiKey;
    }

    @Override
    protected MovieDb doInBackground(Integer... ids) {

        TmdbMovies moviesApi = new TmdbApi(apiKey).getMovies();


        return moviesApi.getMovie(ids[0], "en", MovieMethod.reviews, MovieMethod.videos);
    }


    @Override
    protected void onPostExecute(MovieDb movieDb) {
        receiver.receiveMovie(movieDb);
    }

    public interface ReviewsAndVideosReceiver {
        void receiveMovie(MovieDb movie);
    }
}
