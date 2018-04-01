package com.example.android.popularmoviesstage1.interfaces;

/**
 * Created by Samuela Anastasi on 21/03/2018.
 *
 * This project is part of Android Developer Nanodegree Scholarship Program by
 * Udacity and Google
 */

import com.example.android.popularmoviesstage1.data.Movie;

import java.util.List;

/**
 * Interface will make sure the initial animation of recycler view items
 * after the background thread has finished fetching data from the API.
 * Using interface avoids the background thread to hold references to the UI thread context.
 */
public interface MoviesFirstLoadAnimator {
    void onMoviesFirstLoad(List<Movie> movies);
}
