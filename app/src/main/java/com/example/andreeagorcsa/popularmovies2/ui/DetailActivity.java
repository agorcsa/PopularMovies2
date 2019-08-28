package com.example.andreeagorcsa.popularmovies2.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.andreeagorcsa.popularmovies2.R;
import com.example.andreeagorcsa.popularmovies2.complexadapter.viewholder.OverviewViewHolder;
import com.example.andreeagorcsa.popularmovies2.complexadapter.viewholder.TrailerViewHolder;
import com.example.andreeagorcsa.popularmovies2.database.DatabaseHelper;
import com.example.andreeagorcsa.popularmovies2.models.Movie;
import com.example.andreeagorcsa.popularmovies2.models.Review;
import com.example.andreeagorcsa.popularmovies2.models.Trailer;
import com.example.andreeagorcsa.popularmovies2.utils.JsonUtils;
import com.example.andreeagorcsa.popularmovies2.complexadapter.ComplexAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements TrailerViewHolder.ItemClickHandler, OverviewViewHolder.FavoriteClickHandler {

    public static final String LOG_TAG = DetailActivity.class.getName();

    public static final String VIDEO_URI = "http://www.youtube.com/watch?v=";
    public boolean mIsFavorite;
    // complex RecyclerView
    @BindView(R.id.complex_recycler_view)
    RecyclerView mComplexRecyclerView;
    private ComplexAdapter mComplexAdapter;
    // new block added 25.08.2019
    private int mMovieId;
    private String mOriginalTitle;
    private String mMoviePoster;
    private String mFinalUrl;
    private String mPlotSynopsis;
    private double mUserRating;
    private double mPopularity;
    private String mReleaseDate;
    private List<Review> mReviewList = new ArrayList<>();
    private List<Trailer> mTrailerList = new ArrayList<>();
    // creates an instance of the DatabaseHelper
    private DatabaseHelper movieDb;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // TO DO
        if (savedInstanceState != null) {
            savedInstanceState.putBoolean(String.valueOf(mIsFavorite), true);
            mIsFavorite = savedInstanceState.getBoolean(String.valueOf(mIsFavorite));
        }

        ButterKnife.bind(this);

        //getSupportActionBar().hide();

        buildComplexRecyclerView();

        getMovieData();

        new ReviewAsyncTask().execute(JsonUtils.MOVIE_ID);
        new TrailerAsyncTask().execute(JsonUtils.MOVIE_ID);

        // created a db by reading the constructor from DatabaseHelper class
        movieDb = new DatabaseHelper(this);
    }

    private void buildComplexRecyclerView() {
        Movie movie = getIntent().getParcelableExtra(MainActivity.MOVIE_OBJECT);
        mComplexAdapter = new ComplexAdapter(this, this, movie, mReviewList, mTrailerList);
        RecyclerView.LayoutManager complexLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mComplexRecyclerView.setLayoutManager(complexLayoutManager);
        mComplexRecyclerView.setAdapter(mComplexAdapter);
    }

    private void getMovieData() {
        Movie movie = getIntent().getParcelableExtra(MainActivity.MOVIE_OBJECT);

        mComplexAdapter.updateMovie(movie);

        mMovieId = movie.getMovieId();
        mOriginalTitle = movie.getOriginalTitle();
        mMoviePoster = movie.getPosterPath();
        mFinalUrl = JsonUtils.BASE_URL.concat(JsonUtils.POSTER_SIZE).concat(JsonUtils.POSTER_PATH);
        mPlotSynopsis = movie.getOverview();
        mPopularity = movie.getPopularity();
        mUserRating = movie.getVoteAverage();
        mReleaseDate = movie.getReleaseDate();
        mIsFavorite = movie.getIsFavorite();
    }

    @Override
    public void onItemClick(Trailer trailer) {
        String trailerKey = trailer.getKey();
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(VIDEO_URI + trailerKey)));
    }

    @Override
    public void onFavoriteClick(Movie movie, Button button) {
        Log.i(LOG_TAG, "+ capsule button was clicked");
        //Toast.makeText(getApplicationContext(), "+ capsule button was clicked", Toast.LENGTH_LONG).show();

        if (mIsFavorite) {
            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add, 0, 0, 0);
            DeleteData();
            mIsFavorite = false;

        } else {
            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_remove, 0, 0, 0);
            InsertData();
            mIsFavorite = true;
        }
    }

    public void InsertData(){
        getMovieData();
        // TO DO: is isFavorite part of a movie that I will insert?
        movieDb.insertData(mMovieId, mOriginalTitle, mMoviePoster, mFinalUrl, mPlotSynopsis, mUserRating, mPopularity, mReleaseDate);
        showToast("Movie was added to favorites");
    }

    public void DeleteData() {
        Integer deletedRows = movieDb.deleteData(String.valueOf(mMovieId));
        if (deletedRows != 0) {
            showToast("Movie was deleted from favorites");
        } else {
            showToast("Movie was not deleted from favorites ");
        }
    }


    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * runs the fetchReviewData(reviewsUrl) method at the background thread
     */
    public class ReviewAsyncTask extends AsyncTask<String, Void, List<Review>> {

        @Override
        protected void onPreExecute() {
            mComplexAdapter = (ComplexAdapter) mComplexRecyclerView.getAdapter();
        }

        @Override
        protected List<Review> doInBackground(String... url) {
            try {
                String reviewsUrl = JsonUtils.buildReviewUrl(Integer.toString(mMovieId));
                mReviewList = JsonUtils.fetchReviewData(reviewsUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mReviewList;

        }

        @Override
        protected void onPostExecute(List<Review> reviews) {
            mReviewList = reviews;
            if (reviews != null) {
                mComplexAdapter.updateReviewList(mReviewList);
            } else {
                Toast.makeText(DetailActivity.this, "Your review list is empty", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * runs the fetchTrailerData(trailersUrl) method at the background thread
     */
    public class TrailerAsyncTask extends AsyncTask<String, Void, List<Trailer>> {

        @Override
        protected void onPreExecute() {
            mComplexAdapter = (ComplexAdapter) mComplexRecyclerView.getAdapter();
        }

        @Override
        protected List<Trailer> doInBackground(String... url) {
            try {
                String trailersUrl = JsonUtils.buildTrailerUrl(Integer.toString(mMovieId));
                mTrailerList = JsonUtils.fetchTrailerData(trailersUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mTrailerList;
        }

        @Override
        protected void onPostExecute(List<Trailer> trailers) {
            mTrailerList = trailers;
            if (trailers != null) {
                mComplexAdapter.updateTrailerList(mTrailerList);
            } else {
                Toast.makeText(DetailActivity.this, "Your trailer list is empty", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
