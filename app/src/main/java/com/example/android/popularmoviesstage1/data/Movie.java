package com.example.android.popularmoviesstage1.data;

/**
 * Created by Samuela Anastasi on 18/03/2018.
 *
 * This project is part of Android Developer Nanodegree Scholarship Program by
 * Udacity and Google
 */

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private long movieId;
    private String posterPath;
    private String backdropPath;
    private String movieTitle;
    private float voteAverage;
    private String releaseDate;
    private String overview;
    private String genres;

    // Default constructor
    public Movie() {
    }

    private Movie(Parcel in) {
        movieId = in.readLong();
        posterPath = in.readString();
        backdropPath = in.readString();
        movieTitle = in.readString();
        voteAverage = in.readFloat();
        releaseDate = in.readString();
        overview = in.readString();
        genres = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    // Getter methods *******************************************************************

    public long getMovieId() {
        return movieId;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public String getGenres() {
        return genres;
    }

    // Setter methods *****************************************************************

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setGenreIds(String genres) {
        this.genres = genres;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(movieId);
        parcel.writeString(posterPath);
        parcel.writeString(backdropPath);
        parcel.writeString(movieTitle);
        parcel.writeFloat(voteAverage);
        parcel.writeString(releaseDate);
        parcel.writeString(overview);
        parcel.writeString(genres);
    }
}
