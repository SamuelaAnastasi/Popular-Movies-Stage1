package com.example.android.popularmoviesstage1.ui;

/**
 * Created by Samuela Anastasi on 18/03/2018.
 *
 * This project is part of Android Developer Nanodegree Scholarship Program by
 * Udacity and Google
 */

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.android.popularmoviesstage1.R;
import com.example.android.popularmoviesstage1.data.Movie;
import com.example.android.popularmoviesstage1.interfaces.MoviesFirstLoadAnimator;
import com.example.android.popularmoviesstage1.tasks.MoviesAsyncTask;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.android.popularmoviesstage1.networking.NetworkUtils.isConnected;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    // Bind resources
    @BindString(R.string.category_popular)
    String popular;

    @BindString(R.string.category_top_rated)
    String topRated;

    @BindString(R.string.search_option_popular)
    String searchOptionPopular;

    @BindString(R.string.search_option_top_rated)
    String searchOptionTopRated;

    // Bind savedInstanceState Bundle key Strings
    @BindString(R.string.key_nav_option_selected)
    String keyNavItemSelected;

    @BindString(R.string.key_search_option_string)
    String keySearchOptionString;

    @BindString(R.string.key_action_bar_title)
    String keyActionBarTitle;

    @BindString(R.string.key_recycler_scroll_state)
    String recyclerScrollState;

    // Bind views
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fl_recycler_container)
    FrameLayout recyclerFrame;

    @BindView(R.id.movies_recycler_view)
    GridRecyclerView rv;

    @BindView(R.id.no_internet_view)
    RelativeLayout noInternet;

    @BindView(R.id.btn_try_again)
    Button buttonTryAgain;

    @BindView(R.id.bnv_main_navigation)
    BottomNavigationView navigationView;

    private String searchOption;
    private ActionBar actionBar;

    private MoviesAnimator moviesFirstLoadAnimator = new MoviesAnimator();

    private NavigationItemSelectedListener navigationItemSelectedListener =
            new NavigationItemSelectedListener();

    private NavigationItemReselectedListener navigationItemReselectedListener =
            new NavigationItemReselectedListener();

    GridLayoutManager gridLayoutManager;
    private Parcelable scrollState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        final int columnCount = getResources().getInteger(R.integer.movies_column_count);
        gridLayoutManager = new GridLayoutManager(this, columnCount);
        rv.setLayoutManager(gridLayoutManager);
        rv.setHasFixedSize(true);

        if (savedInstanceState == null) {
            navigationView.setSelectedItemId(R.id.action_popular);
            searchOption = searchOptionPopular;
            if(actionBar != null) {
                actionBar.setTitle(popular);
            }
            startMovieTask(this);
        } else {

            if(savedInstanceState.containsKey(keyNavItemSelected))
            navigationView.setSelectedItemId(savedInstanceState.getInt(keyNavItemSelected));

            if(savedInstanceState.containsKey(keySearchOptionString))
            searchOption = savedInstanceState.getString(keySearchOptionString);

            if(savedInstanceState.containsKey(keyActionBarTitle)) {
                if(actionBar != null) {
                    actionBar.setTitle(savedInstanceState.getString(keyActionBarTitle));
                }
            }

            startTaskPerSelectedOption(savedInstanceState.getInt(keyNavItemSelected));
        }

        // Set listeners for BottomNavigation item selected and reselected events
        navigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        navigationView.setOnNavigationItemReselectedListener(navigationItemReselectedListener);

        // Get layout params from BottomNavigationView
        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) navigationView.getLayoutParams();

        // Set custom {@link}BottomNavigationBehavior to show / hide when scrolling
        layoutParams.setBehavior(new BottomNavigationBehavior());

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(keyNavItemSelected, navigationView.getSelectedItemId());
        outState.putString(keySearchOptionString, searchOption);

        if(actionBar != null) {
            if(actionBar.getTitle() instanceof String)
            outState.putString(keyActionBarTitle, actionBar.getTitle().toString());
        }

        scrollState = rv.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(recyclerScrollState, scrollState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null)
            scrollState = savedInstanceState.getParcelable(recyclerScrollState);
    }

    // Inner classes ------------------------------------------------------------------------------

    // Inner class implements MoviesFirstLoadAnimator interface
    private class MoviesAnimator implements MoviesFirstLoadAnimator {
        @Override
        public void onMoviesFirstLoad(List<Movie> movies) {
            if (movies != null) {
                MovieAdapter adapter = new MovieAdapter();
                adapter.setMovies(movies);
                GridLayoutManager manager = (GridLayoutManager) rv.getLayoutManager();
                rv.setAdapter(adapter);
                if (scrollState != null) {
                    manager.onRestoreInstanceState(scrollState);
                }
                rv.scheduleLayoutAnimation();
            }
        }
    }

    // Inner class to handle BottomNavigationView item selection
    private class NavigationItemSelectedListener
            implements BottomNavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();

            switch (itemId) {
                case R.id.action_popular:
                    searchOption = searchOptionPopular;
                    if(actionBar != null) {
                        actionBar.setTitle(popular);
                    }
                    startMovieTask(MainActivity.this);
                    return true;
                case R.id.action_top_rated:
                    searchOption = searchOptionTopRated;
                    if(actionBar != null) {
                        actionBar.setTitle(topRated);
                    }
                    startMovieTask(MainActivity.this);
                    return true;
            }
            return false;
        }
    }

    // Inner class to handle BottomNavigationView item reselection
    // When bottom nav item is reselected the layout manager scrolls on the top of the list
    private class NavigationItemReselectedListener
            implements BottomNavigationView.OnNavigationItemReselectedListener {
        @Override
        public void onNavigationItemReselected(@NonNull MenuItem item) {
            if (isConnected(MainActivity.this) && rv.getVisibility() == View.GONE) {
                showRecyclerView();
                MoviesAsyncTask task = new MoviesAsyncTask(moviesFirstLoadAnimator);
                task.execute(searchOption);

            }
            ((GridLayoutManager) rv.getLayoutManager())
                    .scrollToPositionWithOffset(0, 0);
        }
    }

    // End of inner classes -----------------------------------------------------------------------

    // Create and execute AsyncTask to fetch movies from API
    private void startMovieTask(Context context) {
        if (isConnected(context)) {
            showRecyclerView();
            MoviesAsyncTask task = new MoviesAsyncTask(moviesFirstLoadAnimator);
            task.execute(searchOption);
        } else {
            showErrorView();
        }
    }

    @OnClick(R.id.btn_try_again)
    public void tryAgain() {
        startMovieTask(this);
    }

    // Helper methods to toggle between error view and recycler view ------------------------------
    private void showRecyclerView() {
        noInternet.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
    }
    private void showErrorView() {
        rv.setVisibility(View.GONE);
        noInternet.setVisibility(View.VISIBLE);
    }

    // Start AsyncTask either for popular or top_rated
    private void startTaskPerSelectedOption(int option) {
        switch (option) {
            case R.id.action_popular:
                searchOption = searchOptionPopular;
                startMovieTask(this);
                break;
            case R.id.action_top_rated:
                searchOption = searchOptionTopRated;
                startMovieTask(this);
                break;
        }
    }
}
