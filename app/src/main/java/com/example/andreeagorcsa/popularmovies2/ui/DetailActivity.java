package com.example.andreeagorcsa.popularmovies2.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.andreeagorcsa.popularmovies2.R;
import com.example.andreeagorcsa.popularmovies2.complexadapter.viewholder.OverviewViewHolder;
import com.example.andreeagorcsa.popularmovies2.complexadapter.viewholder.TrailerViewHolder;
import com.example.andreeagorcsa.popularmovies2.databinding.ActivityDetailBinding;
import com.example.andreeagorcsa.popularmovies2.factory.DetailViewModelFactory;
import com.example.andreeagorcsa.popularmovies2.models.Movie;
import com.example.andreeagorcsa.popularmovies2.models.Review;
import com.example.andreeagorcsa.popularmovies2.models.Trailer;
import com.example.andreeagorcsa.popularmovies2.utils.JsonUtils;
import com.example.andreeagorcsa.popularmovies2.complexadapter.ComplexAdapter;
import com.example.andreeagorcsa.popularmovies2.viewmodel.DetailViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements TrailerViewHolder.ItemClickHandler, OverviewViewHolder.FavoriteClickHandler {

    public static final String LOG_TAG = DetailActivity.class.getName();

    public static final String VIDEO_URI = "http://www.youtube.com/watch?v=";

    @BindView(R.id.complex_recycler_view)
    RecyclerView mComplexRecyclerView;

    @Nullable
    @BindView(R.id.favorite_button)
    Button favoriteButton;

    private ComplexAdapter mComplexAdapter;

    private Movie movie;

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

    private DetailViewModel detailViewModel;

    private ActivityDetailBinding detailBinding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        setSupportActionBar(detailBinding.toolbarDetail);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ButterKnife.bind(this);

        getMovieIntent();
        buildComplexRecyclerView();
        getMovieData();

        detailViewModel = ViewModelProviders.of(this,
                new DetailViewModelFactory(getApplication(), movie))
                .get(DetailViewModel.class);


        detailBinding.setViewModel(detailViewModel);
        detailBinding.setLifecycleOwner(this);

        readIsFavorite();

        new ReviewAsyncTask().execute(JsonUtils.MOVIE_ID);
        new TrailerAsyncTask().execute(JsonUtils.MOVIE_ID);
    }

    private void readIsFavorite() {

        final Observer<Boolean> isFavObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                mComplexAdapter.setFavoriteStatus(aBoolean);
            }
        };

        detailViewModel.isFavoriteVM.observe(this, isFavObserver);
    }

    private void getMovieIntent() {
        movie = getIntent().getParcelableExtra(MainActivity.MOVIE_OBJECT);
    }

    private void buildComplexRecyclerView() {
        getMovieIntent();
        mComplexAdapter = new ComplexAdapter(this, this, movie, mReviewList, mTrailerList);
        RecyclerView.LayoutManager complexLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mComplexRecyclerView.setLayoutManager(complexLayoutManager);
        mComplexRecyclerView.setAdapter(mComplexAdapter);
    }

    private void getMovieData() {

        mComplexAdapter.updateMovie(movie);

        mMovieId = movie.getMovieId();
        mOriginalTitle = movie.getOriginalTitle();
        mMoviePoster = movie.getPosterPath();
        mFinalUrl = JsonUtils.BASE_URL.concat(JsonUtils.POSTER_SIZE).concat(JsonUtils.POSTER_PATH);
        mPlotSynopsis = movie.getOverview();
        mPopularity = movie.getPopularity();
        mUserRating = movie.getVoteAverage();
        mReleaseDate = movie.getReleaseDate();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(movie.originalTitle);
        }
    }

    @Override
    public void onItemClick(Trailer trailer) {
        String trailerKey = trailer.getKey();
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(VIDEO_URI + trailerKey)));
    }

    @Override
    public void onFavoriteClick(Movie movie, Button button) {
        Log.i(LOG_TAG, "+ favorite button was clicked");
        detailViewModel.toggleFavoriteButton(movie);
    }

    @Override
    public void onPlayTrailerClick() {
        Log.i(LOG_TAG, " Play Trailer button was clicked");

        if (mTrailerList.size() > 0) {
            String key = mTrailerList.get(0).getKey();
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(VIDEO_URI + key)));
        } else {
            Toast.makeText(this, "No trailers in the list", Toast.LENGTH_SHORT).show();
        }
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
