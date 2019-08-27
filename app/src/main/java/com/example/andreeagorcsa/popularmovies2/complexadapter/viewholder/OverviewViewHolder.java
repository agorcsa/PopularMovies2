package com.example.andreeagorcsa.popularmovies2.complexadapter.viewholder;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andreeagorcsa.popularmovies2.R;
import com.example.andreeagorcsa.popularmovies2.complexadapter.ComplexAdapter;
import com.example.andreeagorcsa.popularmovies2.models.Movie;
import com.example.andreeagorcsa.popularmovies2.models.Trailer;
import com.example.andreeagorcsa.popularmovies2.ui.DetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OverviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public static final String LOG_TAG = "DetailActivity";

    public @BindView(R.id.movie_title_detail)
    TextView mMovieTitle;

    public @BindView(R.id.movie_date_detail)
    TextView mMovieDate;

    public @BindView(R.id.play_trailer_button)
    Button mPlayTrailer;

    public @BindView(R.id.movie_poster_detail)
    ImageView mMoviePoster;

    public @BindView(R.id.synopsis_content)
    TextView mSynopsis;

    public @BindView(R.id.score_detail)
    TextView mScore;

    public @BindView(R.id.plus_button)
    Button mFavoriteButton;

    Movie movie;

    private FavoriteClickHandler favoriteClickHandler;

    public OverviewViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mFavoriteButton.setOnClickListener(this);
    }
    public void setFavoriteClickHandler(FavoriteClickHandler favoriteClickHandler, Movie movie) {
        this.favoriteClickHandler = favoriteClickHandler;
        this.movie = movie;
    }

    @Override
    public void onClick(View v) {
        favoriteClickHandler.onFavoriteClick(movie, mFavoriteButton);
    }

    public interface FavoriteClickHandler {
        void onFavoriteClick(Movie movie, Button button);
    }
}