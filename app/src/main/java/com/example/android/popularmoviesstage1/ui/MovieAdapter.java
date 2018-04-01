package com.example.android.popularmoviesstage1.ui;

/**
 * Created by Samuela Anastasi on 18/03/2018.
 *
 * This project is part of Android Developer Nanodegree Scholarship Program by
 * Udacity and Google
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmoviesstage1.R;
import com.example.android.popularmoviesstage1.data.Movie;
import com.example.android.popularmoviesstage1.networking.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    private static final String KEY_CURRENT_MOVIE = "currentMovie";

    private static final String imageSize = "w500";

    // MovieAdapter data
    private List<Movie> movies;

    // Adapter constructor
    MovieAdapter() {
    }

    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_list_item_layout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.MovieViewHolder holder, int position) {
        holder.setMovieData(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    // ViewHolder class declaration
    public class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_poster)
        ImageView posterImageView;

        @BindView(R.id.tv_movie_title)
        TextView titleTextView;

        @BindView(R.id.tv_movie_genre)
        TextView genresTextView;

        Movie currentMovie;

        public MovieViewHolder(final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = itemView.getContext();
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra(KEY_CURRENT_MOVIE, movies.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }

        public void setMovieData(Movie currentMovie) {
            this.currentMovie = currentMovie;
            String imageUri = NetworkUtils.buildImageUrl(imageSize, currentMovie.getPosterPath());

            Picasso.get()
                    .load(imageUri)
                    .placeholder(R.drawable.placeholder_shape)
                    .error(R.drawable.error_shape)
                    .into(posterImageView);
            titleTextView.setText(currentMovie.getMovieTitle());
            genresTextView.setText(currentMovie.getGenres());
        }

    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        this.notifyDataSetChanged();
    }
}
