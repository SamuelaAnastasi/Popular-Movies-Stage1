package com.example.android.popularmoviesstage1.tasks;

/**
 * Created by Samuela Anastasi on 21/03/2018.
 *
 * This project is part of Android Developer Nanodegree Scholarship Program by
 * Udacity and Google
 */

import android.os.AsyncTask;

import com.example.android.popularmoviesstage1.data.Movie;
import com.example.android.popularmoviesstage1.interfaces.MoviesFirstLoadAnimator;
import com.example.android.popularmoviesstage1.networking.JsonUtils;
import com.example.android.popularmoviesstage1.networking.NetworkUtils;

import java.io.IOException;
import java.util.List;

// AsyncTask will perform queries for either popular or top rated movies
public class MoviesAsyncTask extends AsyncTask<String, Void, List<Movie>> {

    private MoviesFirstLoadAnimator moviesFirstLoadAnimator;

    public MoviesAsyncTask(MoviesFirstLoadAnimator moviesFirstLoadAnimator) {
        this.moviesFirstLoadAnimator = moviesFirstLoadAnimator;
    }

    @Override
    protected List<Movie> doInBackground(String... params) {

        try {
            String response = NetworkUtils.getResponseFromUrl(params[0]);
            return JsonUtils.parseJsonString(response);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // List of movies delivered to a custom animator object
    // that will handle the initial items animation in recyclerView
    @Override
    protected void onPostExecute(List<Movie> movies) {
        moviesFirstLoadAnimator.onMoviesFirstLoad(movies);
    }
}

