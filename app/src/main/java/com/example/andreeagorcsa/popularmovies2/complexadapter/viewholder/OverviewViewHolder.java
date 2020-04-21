package com.example.andreeagorcsa.popularmovies2.complexadapter.viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andreeagorcsa.popularmovies2.R;
import com.example.andreeagorcsa.popularmovies2.models.Movie;
import com.example.andreeagorcsa.popularmovies2.models.Trailer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OverviewViewHolder extends RecyclerView.ViewHolder {

    public static final String LOG_TAG = "DetailActivity";

    public @BindView(R.id.movie_title_detail)
    TextView mMovieTitle;

    public @BindView(R.id.movie_date_detail)
    TextView mMovieDate;

    public @BindView(R.id.movie_poster_detail)
    ImageView mMoviePoster;

    public @BindView(R.id.synopsis_content)
    TextView mSynopsis;

    public @BindView(R.id.score_detail)
    TextView mScore;

    public @BindView(R.id.favorite_button)
    Button mFavoriteButton;

    public @BindView(R.id.play_trailer_button)
    Button mPlayTrailerButton;

    Movie movie;

    private FavoriteClickHandler favoriteClickHandler;

    private Trailer trailer;

    public OverviewViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mFavoriteButton.setOnClickListener(view -> favoriteClickHandler.onFavoriteClick(movie, mFavoriteButton));
        mPlayTrailerButton.setOnClickListener(view -> favoriteClickHandler.onPlayTrailerClick());
    }

    public void setFavoriteClickHandler(FavoriteClickHandler favoriteClickHandler, Movie movie) {
        this.favoriteClickHandler = favoriteClickHandler;
        this.movie = movie;
    }

    public interface FavoriteClickHandler {
        void onFavoriteClick(Movie movie, Button button);
        void onPlayTrailerClick();
    }
}