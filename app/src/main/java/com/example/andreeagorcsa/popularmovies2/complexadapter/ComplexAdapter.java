package com.example.andreeagorcsa.popularmovies2.complexadapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andreeagorcsa.popularmovies2.R;
import com.example.andreeagorcsa.popularmovies2.complexadapter.viewholder.OverviewViewHolder;
import com.example.andreeagorcsa.popularmovies2.complexadapter.viewholder.ReviewViewHolder;
import com.example.andreeagorcsa.popularmovies2.complexadapter.viewholder.TrailerViewHolder;
import com.example.andreeagorcsa.popularmovies2.models.Movie;
import com.example.andreeagorcsa.popularmovies2.models.Review;
import com.example.andreeagorcsa.popularmovies2.models.Trailer;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ComplexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String MOVIE_OBJECT = "movie_object";

    // constants used for the switch method
    private final int OVERVIEW = 0;
    private final int REVIEW = 1;
    private final int TRAILER = 2;

    TrailerViewHolder.ItemClickHandler itemClickHandler;

    OverviewViewHolder.FavoriteClickHandler favoriteClickHandler;

    // the items to display in your RecyclerView: OverView, Review, Trailer
    private Movie mMovie;
    private List<Review> mReviewList = new ArrayList<>();
    private List<Trailer> mTrailerList = new ArrayList<>();

    public ComplexAdapter(OverviewViewHolder.FavoriteClickHandler favoriteClickHandler, TrailerViewHolder.ItemClickHandler itemClickHandler, Movie movie, List<Review> reviews, List<Trailer> trailers) {
        mMovie = movie;
        mReviewList = reviews;
        mTrailerList = trailers;
        this.itemClickHandler = itemClickHandler;
        this.favoriteClickHandler = favoriteClickHandler;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case OVERVIEW:
                View v1 = inflater.inflate(R.layout.overview_item, viewGroup, false);
                viewHolder = new OverviewViewHolder(v1);
                break;
            case REVIEW:
                View v2 = inflater.inflate(R.layout.review_item, viewGroup, false);
                viewHolder = new ReviewViewHolder(v2);
                break;
            default:
                View v3 = inflater.inflate(R.layout.trailer_item, viewGroup, false);
                viewHolder = new TrailerViewHolder(v3);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType()) {
            case OVERVIEW:
                OverviewViewHolder vh1 = (OverviewViewHolder) viewHolder;
                configureOverviewViewHolder(vh1, position);
                break;
            case REVIEW:
                ReviewViewHolder vh2 = (ReviewViewHolder) viewHolder;
                configureReviewViewHolder(vh2, position - 1);
                break;
            default:
                TrailerViewHolder vh3 = (TrailerViewHolder) viewHolder;
                configureTrailerViewHolder(vh3, position - 1 - mReviewList.size());
                break;
        }
    }

    private void configureOverviewViewHolder(OverviewViewHolder vh1, int position) {

        String originalTitle = mMovie.getOriginalTitle();
        vh1.mMovieTitle.setText(originalTitle);

        String posterPath = mMovie.getPosterPath();

        Picasso.get()
                .load(posterPath)
                .placeholder(R.drawable.cinema_poster)
                .into(vh1.mMoviePoster);

        String plotSynopsis = mMovie.getOverview();
        vh1.mSynopsis.setText(plotSynopsis);

        double userRating = mMovie.getVoteAverage();
        float userRatingFloat = BigDecimal.valueOf(userRating).floatValue();
        String userRatingString = String.valueOf(userRatingFloat);
        vh1.mScore.setText("SCORE" + userRatingString);

        String releaseDate = mMovie.getReleaseDate();
        vh1.mMovieDate.setText(releaseDate);
        vh1.setFavoriteClickHandler(favoriteClickHandler,mMovie);
    }

    private void configureReviewViewHolder(ReviewViewHolder vh2, int position) {
        Review currentReview = mReviewList.get(position);
        String reviewAuthor = currentReview.getAuthor();
        String reviewContent = currentReview.getContent();

        vh2.mReviewAuthorTextView.setText(reviewAuthor);
        vh2.mReviewContentTextView.setText(reviewContent);
    }

    public void configureTrailerViewHolder(TrailerViewHolder vh3, int position) {
        Trailer currentReview = mTrailerList.get(position);
        String trailerName = currentReview.getName();
        vh3.mTrailerNameTextView.setText(trailerName);
        vh3.setItemClickHandler(itemClickHandler, mTrailerList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mMovie == null && mReviewList == null && mTrailerList == null) {
            return 0;
        }
        return 1 + mReviewList.size() + mTrailerList.size();
    }

    public void updateMovie(Movie movie) {
        mMovie = movie;
        notifyDataSetChanged();
    }

    public void updateReviewList(List<Review> reviews) {
        mReviewList = reviews;
        notifyDataSetChanged();
    }

    public void updateTrailerList(List<Trailer> trailers) {
        mTrailerList = trailers;
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return OVERVIEW;
        } else if (position > 0 && position <= mReviewList.size()) {
            return REVIEW;
        } else {
            return TRAILER;
        }
    }

}