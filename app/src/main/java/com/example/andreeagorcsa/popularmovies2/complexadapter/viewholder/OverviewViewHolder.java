package com.example.andreeagorcsa.popularmovies2.complexadapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andreeagorcsa.popularmovies2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OverviewViewHolder extends RecyclerView.ViewHolder {

    public @BindView(R.id.movie_title_detail)
    TextView mMovieTitle;

    public @BindView(R.id.movie_date_detail)
    TextView mMovieDate;

    public @BindView(R.id.play_trailer_button)
    Button mPlayTrailer;

    public @BindView(R.id.favorite_list_button)
    Button mFavoriteList;

    public @BindView(R.id.movie_poster_detail)
    ImageView mMoviePoster;

    public @BindView(R.id.synopsis_content)
    TextView mSynopsis;

    public @BindView(R.id.score_detail)
    TextView mScore;

    public OverviewViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
