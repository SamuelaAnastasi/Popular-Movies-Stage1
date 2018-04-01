package com.example.android.popularmoviesstage1.networking;

/**
 * Created by Samuela Anastasi on 21/03/2018.
 *
 * This project is part of Android Developer Nanodegree Scholarship Program by
 * Udacity and Google
 */

import android.text.TextUtils;
import android.util.Log;

import com.example.android.popularmoviesstage1.data.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String LOG_TAG = JsonUtils.class.getSimpleName();

    // Error status key
    private static final String KEY_STATUS_CODE = "status_code";
    private static final String KEY_STATUS_MESSAGE = "status_message";

    // Valid for movies queries and reviews queries
    private static final String KEY_RESULTS_ALL = "results";

    private static final String KEY_MOVIE_ID = "id";
    private static final String KEY_VOTE_AVERAGE = "vote_average";
    private static final String KEY_MOVIE_TITLE = "title";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_RELEASE_DATE = "release_date";
    private static final String KEY_GENRE_IDS = "genre_ids";

    // Images Json keys
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_BACKDROP_PATH = "backdrop_path";

    // Strings for movie genres
    private static final String GENRE_ACTION = "Action";
    private static final String GENRE_ADVENTURE = "Adventure";
    private static final String GENRE_ANIMATION = "Animation";
    private static final String GENRE_COMEDY = "Comedy";
    private static final String GENRE_CRIME = "Crime";
    private static final String GENRE_DOCUMENTARY = "Documentary";
    private static final String GENRE_DRAMA = "Drama";
    private static final String GENRE_FAMILY = "Family";
    private static final String GENRE_FANTASY = "Fantasy";
    private static final String GENRE_HISTORY = "History";
    private static final String GENRE_HORROR = "Horror";
    private static final String GENRE_MUSIC = "Music";
    private static final String GENRE_MYSTERY = "Mystery";
    private static final String GENRE_ROMANCE = "Romance";
    private static final String GENRE_SCIENCE = "Science Fiction";
    private static final String GENRE_TV = "TV Movie";
    private static final String GENRE_THRILLER = "Thriller";
    private static final String GENRE_WAR = "War";
    private static final String GENRE_WESTERN = "Western";
    private static final String GENRE_DEFAULT = "";


    // Private constructor to avoid instantiation. The class contains only static methods
    private JsonUtils() {}

    public static List<Movie> parseJsonString(String jsonString){
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        List<Movie> movies = new ArrayList<>();

        try {

            JSONObject rootJsonObject = new JSONObject(jsonString);

            // Check if there is an error
            if (rootJsonObject.has(KEY_STATUS_CODE)) {
                Log.d(LOG_TAG, rootJsonObject.optString(KEY_STATUS_MESSAGE));
                return null;
            }

            JSONArray resultsMoviesArray = rootJsonObject.optJSONArray(KEY_RESULTS_ALL);
            if (resultsMoviesArray.length() > 0) {
                for (int i = 0; i < resultsMoviesArray.length(); i++) {
                    JSONObject currentMovie = resultsMoviesArray.optJSONObject(i);

                    long movieId = currentMovie.optInt(KEY_MOVIE_ID);
                    float voteAverage = (float) currentMovie.optDouble(KEY_VOTE_AVERAGE);
                    String title = currentMovie.optString(KEY_MOVIE_TITLE);
                    String overview = currentMovie.optString(KEY_OVERVIEW);
                    String releaseDate = currentMovie.optString(KEY_RELEASE_DATE);
                    String posterPath = currentMovie.optString(KEY_POSTER_PATH);
                    String backdropPath = currentMovie.optString(KEY_BACKDROP_PATH);
                    JSONArray genreIds = currentMovie.optJSONArray(KEY_GENRE_IDS);

                    List<Integer> genreIdsList = new ArrayList<>();
                    if (genreIds.length() > 0) {
                        for (int j = 0; j < genreIds.length(); j++) {
                            genreIdsList.add(genreIds.optInt(j));
                        }
                    }

                    String genresString = buildGenresString(genreIdsList);

                    Movie movie = new Movie();

                    // Set Movie instance fields
                    movie.setMovieId(movieId);
                    movie.setVoteAverage(voteAverage);
                    movie.setMovieTitle(title);
                    movie.setOverview(overview);
                    movie.setReleaseDate(releaseDate);
                    movie.setPosterPath(posterPath);
                    movie.setBackdropPath(backdropPath);
                    movie.setGenreIds(genresString);

                    // Add movie to List
                    movies.add(movie);
                }
            } else {
                return null;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }

    // Convert genresIDs list into a string
    private static String buildGenresString(List<Integer> genreIds) {

        List<String> genresStringList = new ArrayList<>();

        for (Integer genreId : genreIds) {
            switch (genreId) {
                case 28:
                    genresStringList.add(GENRE_ACTION);
                    break;
                case 12:
                    genresStringList.add(GENRE_ADVENTURE);
                    break;
                case 16:
                   genresStringList.add(GENRE_ANIMATION);
                   break;
                case 35:
                    genresStringList.add(GENRE_COMEDY);
                    break;
                case 80:
                    genresStringList.add(GENRE_CRIME);
                    break;
                case 99:
                    genresStringList.add(GENRE_DOCUMENTARY);
                    break;
                case 18:
                    genresStringList.add(GENRE_DRAMA);
                    break;
                case 10751:
                    genresStringList.add(GENRE_FAMILY);
                    break;
                case 14:
                    genresStringList.add(GENRE_FANTASY);
                    break;
                case 36:
                    genresStringList.add(GENRE_HISTORY);
                    break;
                case 27:
                    genresStringList.add(GENRE_HORROR);
                    break;
                case 10402:
                    genresStringList.add(GENRE_MUSIC);
                    break;
                case 9648:
                    genresStringList.add(GENRE_MYSTERY);
                    break;
                case 10749:
                    genresStringList.add(GENRE_ROMANCE);
                    break;
                case 878:
                    genresStringList.add(GENRE_SCIENCE);
                    break;
                case 10770:
                    genresStringList.add(GENRE_TV);
                    break;
                case 53:
                    genresStringList.add(GENRE_THRILLER);
                    break;
                case 10752:
                    genresStringList.add(GENRE_WAR);
                    break;
                case 37:
                    genresStringList.add(GENRE_WESTERN);
                    break;
                default:
                    genresStringList.add(GENRE_DEFAULT);
                    break;            }
        }
        return TextUtils.join(", ", genresStringList);
    }
}



