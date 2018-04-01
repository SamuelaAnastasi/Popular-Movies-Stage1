package com.example.android.popularmoviesstage1.networking;

/**
 * Created by Samuela Anastasi on 20/03/2018.
 *
 * This project is part of Android Developer Nanodegree Scholarship Program by
 * Udacity and Google
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    private static final String MOVIES_BASE_URL = "https://api.themoviedb.org/3/movie";
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";

    // TODO Enter your API KEY here
    private static final String API_KEY_STRING = "";

    private static final String QUERY_PARAM_API_KEY = "api_key";
    private static final String QUERY_PARAM_LANGUAGE = "language";
    private static final String QUERY_PARAM_PAGE_LIMIT = "page";
    private static final String LANGUAGE_VALUE = "en-US";
    private static final String PAGE_LIMIT_VALUE = "5";

    // Private constructor to avoid instantiation. The class contains only static methods
    private NetworkUtils() {}

    public static String getResponseFromUrl(String navSelectedOption) throws IOException {

        URL url = buildQueryUrl(navSelectedOption);

        if (url == null) {
            return null;
        }

        HttpURLConnection urlConnection = null;
        InputStream streamIn = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            streamIn = urlConnection.getInputStream();
            Scanner scanner = new Scanner(streamIn);
            scanner.useDelimiter("\\A");
            if (scanner.hasNext()) {
                return scanner.next();
            } else return null;

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (streamIn != null) {
                streamIn.close();
            }
        }
    }

    /**
     * Build queryURL by adding query parameters to the API
     * @param navSelectedOption dynamically chosen path String to append to the base Url String
     * @return final query URL for API endpoint
     */
    private static URL buildQueryUrl (String navSelectedOption) {

        Uri uri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                .appendPath(navSelectedOption)
                .appendQueryParameter(QUERY_PARAM_API_KEY, API_KEY_STRING)
                .appendQueryParameter(QUERY_PARAM_LANGUAGE, LANGUAGE_VALUE)
                .appendQueryParameter(QUERY_PARAM_PAGE_LIMIT, PAGE_LIMIT_VALUE)
                .build();

        return createURL(uri.toString());
    }

    /**
     * Create URL object from plain String
     * Method is called by {@link}buildQueryUrl
     * @param urlString query string with embedded query params
     * @return final query URL for API endpoint
     */
    private static URL createURL(String urlString) {
        URL url = null;
        try
        {
            if (!(urlString.isEmpty())) {
                url = new URL(urlString);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * Build Url string for poster path or backdrop path
     * @param imageSizeString values are Poster: "w500" - Backdrop: "w780"
     * @param imagePath last segment of poster/backdrop path
     * @return path string for poster/backdrop
     */
    public static String buildImageUrl(String imageSizeString, String imagePath) {
        String imageUrl = IMAGE_BASE_URL + imageSizeString + imagePath;
        Log.v(LOG_TAG, imageUrl);
        return imageUrl;
    }

    // Method used in MainActivity and DetailActivity to check the device connectivity
    public static boolean isConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnected();
        }
        return false;
    }
}
