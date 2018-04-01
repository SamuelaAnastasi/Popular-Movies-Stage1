package com.example.android.popularmoviesstage1.ui;

/**
 * Created by Samuela Anastasi on 18/03/2018.
 *
 * This project is part of Android Developer Nanodegree Scholarship Program by
 * Udacity and Google
 */

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.android.popularmoviesstage1.R;
import com.example.android.popularmoviesstage1.data.Movie;
import com.example.android.popularmoviesstage1.networking.NetworkUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.popularmoviesstage1.networking.NetworkUtils.isConnected;

public class DetailsActivity extends AppCompatActivity {

    private static final String LOG_TAG = DetailsActivity.class.getSimpleName();

    // Bind views
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @BindView(R.id.backdrop_image)
    ImageView backdropImage;

    @BindView(R.id.movie_title)
    TextView movieTitle;

    @BindView(R.id.tv_details_overview) TextView overview;

    @BindView(R.id.tv_release_date) TextView releaseDate;

    @BindView(R.id.tv_genres_content) TextView genres;

    @BindView(R.id.rb_rating_bar)
    RatingBar ratingBar;

    @BindView(R.id.details_coordinator)
    CoordinatorLayout detailsCoordinator;

    // Bind resources
    @BindColor(R.color.colorAccentFavorite)
    int snackbarBackgroundColor;

    @BindString(R.string.details_toolbar_title)
    String detailsToolbarTitle;

    @BindColor(R.color.primaryTextColor)
    int snackbarActionColor;

    @BindString(R.string.key_intent_current_movie)
    String keyIntentCurrentMovie;

    @BindString(R.string.details_image_size)
    String detailsImageSize;

    @BindString(R.string.key_details_snackbar_dismissed)
    String keySnackBarDismissed;

    @BindString(R.string.snackbar_message)
    String snackBarMessage;

    @BindString(R.string.snackbar_button_label)
    String snackBarButtonLabel;

    Movie currentMovie;
    ActionBar actionBar;
    String backdropUri;

    final AtomicBoolean loaded = new AtomicBoolean();
    boolean isSnackBarDismissed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            //  If user already dismissed snackBar it will not show again on device rotation
            if(savedInstanceState.containsKey(keySnackBarDismissed)) {
                isSnackBarDismissed = savedInstanceState.getBoolean(keySnackBarDismissed);
            }
        } else {
            isSnackBarDismissed = false;
        }

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if(actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        collapsingToolbar.setTitle(detailsToolbarTitle);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(keyIntentCurrentMovie)) {
            currentMovie = intent.getParcelableExtra(keyIntentCurrentMovie);
            movieTitle.setText(currentMovie.getMovieTitle());
            overview.setText(currentMovie.getOverview());
            releaseDate.setText(currentMovie.getReleaseDate());
            genres.setText(currentMovie.getGenres());
            backdropUri = currentMovie.getBackdropPath();
            float ratingValue = currentMovie.getVoteAverage();
            ratingBar.setRating(ratingValue);
        }

        String imageUri = NetworkUtils.buildImageUrl(detailsImageSize, backdropUri);
        Picasso.get()
                .load(imageUri)
                .placeholder(R.drawable.placeholder_shape_land)
                .error(R.drawable.error_shape_land)
                .into(backdropImage, new Callback.EmptyCallback(){
                    @Override
                    public void onSuccess() {
                        loaded.set(true);
                    }
                });

        // Check for connection and if Picasso already has loaded the image
        // Also check if user has already dismissed the snackBar,
        if(!isConnected(DetailsActivity.this) && !(loaded.get()) && !isSnackBarDismissed) {

            final Snackbar snackbar = Snackbar.make(detailsCoordinator,
                    snackBarMessage,
                    Snackbar.LENGTH_INDEFINITE );
            snackbar.setAction(snackBarButtonLabel, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isSnackBarDismissed = true;
                    snackbar.dismiss();
                }
            });

            snackbar.setActionTextColor(snackbarActionColor);

            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(snackbarBackgroundColor);
            TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setMaxLines(3);

            snackbar.show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(keySnackBarDismissed, isSnackBarDismissed);
    }
}

