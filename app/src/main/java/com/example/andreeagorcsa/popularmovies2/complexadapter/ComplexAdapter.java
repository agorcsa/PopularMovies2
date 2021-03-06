package com.example.andreeagorcsa.popularmovies2.complexadapter;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andreeagorcsa.popularmovies2.R;
import com.example.andreeagorcsa.popularmovies2.complexadapter.viewholder.OverviewViewHolder;
import com.example.andreeagorcsa.popularmovies2.complexadapter.viewholder.ReviewTextViewHolder;
import com.example.andreeagorcsa.popularmovies2.complexadapter.viewholder.ReviewViewHolder;
import com.example.andreeagorcsa.popularmovies2.complexadapter.viewholder.TrailerTextViewHolder;
import com.example.andreeagorcsa.popularmovies2.complexadapter.viewholder.TrailerViewHolder;
import com.example.andreeagorcsa.popularmovies2.justifytext.TextViewEx;
import com.example.andreeagorcsa.popularmovies2.models.Movie;
import com.example.andreeagorcsa.popularmovies2.models.Review;
import com.example.andreeagorcsa.popularmovies2.models.Trailer;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.List;

public class ComplexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String MOVIE_OBJECT = "movie_object";

    // constants used for the switch method
    private final int OVERVIEW = 0;
    private final int REVIEW = 1;
    private final int TRAILER = 2;
    // inflates the
    private final int REVIEW_TEXT = 3;
    private final int TRAILER_TEXT = 4;

    TrailerViewHolder.ItemClickHandler itemClickHandler;

    OverviewViewHolder.FavoriteClickHandler favoriteClickHandler;

    // the items to display in your RecyclerView: OverView, Review, Trailer
    private Movie mMovie;
    private List<Review> mReviewList;
    private List<Trailer> mTrailerList;

    private boolean isFavourite;

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
            case TRAILER:
                View v3 = inflater.inflate(R.layout.trailer_item, viewGroup, false);
                viewHolder = new TrailerViewHolder(v3);
                break;
            case REVIEW_TEXT:
                View v4 = inflater.inflate(R.layout.review_text_item, viewGroup, false);
                viewHolder = new ReviewTextViewHolder(v4);
                break;
            default:
                View v5 = inflater.inflate(R.layout.trailer_text_item, viewGroup, false);
                viewHolder = new TrailerTextViewHolder(v5);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Log.i("position - item type", position + " - " + viewHolder.getItemViewType());
        switch (viewHolder.getItemViewType()) {
            case OVERVIEW:
                OverviewViewHolder vh1 = (OverviewViewHolder) viewHolder;
                configureOverviewViewHolder(vh1);
                break;
            case REVIEW_TEXT:
                ReviewTextViewHolder vh4 = (ReviewTextViewHolder) viewHolder;
                configureReviewTextViewHolder(vh4);
                break;
            case REVIEW:
                ReviewViewHolder vh2 = (ReviewViewHolder) viewHolder;
                configureReviewViewHolder(vh2, position - 2);
                break;
            case TRAILER_TEXT:
                TrailerTextViewHolder vh5 = (TrailerTextViewHolder) viewHolder;
                configureTrailerTextViewHolder(vh5);
                break;
            default:
                TrailerViewHolder vh3 = (TrailerViewHolder) viewHolder;
                configureTrailerViewHolder(vh3, position - mReviewList.size() - 3);
                break;
        }
    }

    private void configureReviewTextViewHolder(ReviewTextViewHolder vh4) {
        if (mReviewList.size() > 0) {
            vh4.mReviewLabel.setText("REVIEWS");
            vh4.mBlueLine.setVisibility(View.VISIBLE);
        } else {
            vh4.mBlueLine.setVisibility(View.GONE);
        }
    }

    private void configureTrailerTextViewHolder(TrailerTextViewHolder vh5) {
        if (mTrailerList.size() > 0) {
            vh5.mTrailerLabel.setText("TRAILERS");
            vh5.blueLineView.setVisibility(View.VISIBLE);
        } else {
            vh5.blueLineView.setVisibility(View.GONE);
        }
    }

    private void configureOverviewViewHolder(OverviewViewHolder vh1) {

        String originalTitle = mMovie.getOriginalTitle();
        vh1.mMovieTitle.setText(originalTitle);

        String posterPath = mMovie.getPosterPath();

        Picasso.get()
                .load(posterPath)
                .placeholder(R.drawable.cinema_poster)
                .into(vh1.mMoviePoster);

        String plotSynopsis = mMovie.getOverview();
        vh1.mSynopsis.setText(plotSynopsis, true);

        double userRating = mMovie.getVoteAverage();
        float userRatingFloat = BigDecimal.valueOf(userRating).floatValue();
        String userRatingString = String.valueOf(userRatingFloat);
        vh1.mScore.setText("SCORE" + userRatingString);

        String releaseDate = mMovie.getReleaseDate();
        vh1.mMovieDate.setText(releaseDate);
        vh1.setFavoriteClickHandler(favoriteClickHandler,mMovie);
        setFavoriteButtonEnabled(vh1);
    }

    // switches the value of the favorite button from "+" to "-" in DetailActivity
    public void setFavoriteButtonEnabled(OverviewViewHolder overviewViewHolder) {
        if (isFavourite) {
            overviewViewHolder.mFavoriteButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_remove, 0, 0, 0);
            isFavourite = true;
        } else {
            overviewViewHolder.mFavoriteButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add, 0, 0, 0);
            isFavourite = false;
        }
    }

    private void configureReviewViewHolder(ReviewViewHolder vh2, int position) {
        Review currentReview = mReviewList.get(position);
        String reviewAuthor = currentReview.getAuthor();
        String reviewContent = currentReview.getContent();

        vh2.mReviewAuthorTextView.setText(reviewAuthor);
        vh2.expTv1.setText(reviewContent);
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
        return 1 + 1 + mReviewList.size() + 1 + mTrailerList.size();
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
        } else if (position == 1) {
            return REVIEW_TEXT;
        } else if (position > 1 && position <= ((mReviewList.size() -1 ) + 2)) {
            return REVIEW;
        } else if (position == (mReviewList.size() - 1) + 3) {
            return TRAILER_TEXT;
        } else {
            return TRAILER;
        }
    }

    public void setFavoriteStatus(boolean favourite) {
        // This is a global variable in the adapter
        this.isFavourite = favourite;
        notifyDataSetChanged();
    }
}