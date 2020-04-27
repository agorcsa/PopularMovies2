package com.example.andreeagorcsa.popularmovies2.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.andreeagorcsa.popularmovies2.R;
import com.example.andreeagorcsa.popularmovies2.adapters.MovieAdapter;
import com.example.andreeagorcsa.popularmovies2.databinding.ActivityMainBinding;
import com.example.andreeagorcsa.popularmovies2.models.Movie;
import com.example.andreeagorcsa.popularmovies2.utils.JsonUtils;
import com.example.andreeagorcsa.popularmovies2.viewmodel.MainViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ItemClickHandler {

    public static final String LOG_TAG = MainActivity.class.getName();

    public static final String MOVIE_OBJECT = "movie_object";

   /* public static final String TOP_RATED = "top rated";
    public static final String MOST_POPULAR = "most popular";
    public static final String IS_FAVORITE = "favorite";*/

    public static final int MOST_POPULAR = 0;
    public static final int TOP_RATED = 1;
    public static final int IS_FAVORITE = 2;

    public static final String SORT_TYPE = "sort_type";

    private int sortType = MOST_POPULAR;

    @BindView(R.id.movie_recycler_view)
    RecyclerView mMovieRecyclerView;
    // by default the value of sortType is "popular"
    private MovieAdapter mMovieAdapter;
    private GridLayoutManager mGridLayoutManager;
    private List<Movie> mMovieList;

    private List<Movie> mFavoriteMovies = new ArrayList<>();

    // DataBinding
    ActivityMainBinding mBinding;

    // ViewModel
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(mBinding.toolbarMain);
        mBinding.toolbarMain.setTitle("Popular Movies");
        mBinding.toolbarMain.setTitleTextColor(getResources().getColor(R.color.colorWhite));

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        readFavoritesFromDatabase();

        ButterKnife.bind(this);

        buildMovieRecyclerView();

        if (savedInstanceState != null) {
            sortType = savedInstanceState.getInt(SORT_TYPE);
            if (sortType == IS_FAVORITE) {
                mainViewModel.getFavoriteMovies().observe(this, new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(List<Movie> movies) {
                        mFavoriteMovies = movies;
                        mMovieAdapter.setMovieList(mFavoriteMovies);
                    }
                });
            } else if (sortType == MOST_POPULAR){
                new MovieAsyncTask().execute(JsonUtils.POPULARITY);
            } else {
                new MovieAsyncTask().execute(JsonUtils.TOP_RATED);
            }
        } else {
           checkForConnection(JsonUtils.POPULARITY);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SORT_TYPE, sortType);
    }

    private void buildMovieRecyclerView() {
        mMovieRecyclerView.setHasFixedSize(true);
        mMovieList = new ArrayList<>();
        mGridLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.numberOfColumns));
        mMovieAdapter = new MovieAdapter(this, mMovieList);
        mMovieRecyclerView.setLayoutManager(mGridLayoutManager);
        mMovieRecyclerView.setAdapter(mMovieAdapter);
    }


    @Override
    public void onItemClick(Movie movie) {
        Intent movieIntent = new Intent(this, DetailActivity.class);

        movieIntent.putExtra(MOVIE_OBJECT, movie);
        startActivity(movieIntent);
    }

    public void readFavoritesFromDatabase() {
        mainViewModel.getFavoriteMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                mFavoriteMovies = movies;
            }
        });
    }

    public void checkForConnection(String sortType) {
        if (internetConnectionCheck()) {
            new MovieAsyncTask().execute(sortType);
        } else {
            Toast.makeText(MainActivity.this, "No Internet connection", Toast.LENGTH_LONG).show();
        }
    }

    public boolean internetConnectionCheck() {
        // checking for Internet connection
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem spinnerItem = menu.findItem(R.id.action_change_movie);

        final Spinner spinner = (Spinner) spinnerItem.getActionView();

        ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item,
                 MainActivity.this.getResources().getTextArray(R.array.change_movie));

        spinner.setPopupBackgroundResource(R.color.colorPrimaryDark);
        spinner.setAdapter(arrayAdapter);

        sortType = spinner.getSelectedItemPosition();
        spinner.setSelection(sortType, false);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == TOP_RATED) {
                    sortType = TOP_RATED;
                    new MovieAsyncTask().execute(JsonUtils.TOP_RATED);
                } else if (position == MOST_POPULAR) {
                    sortType = MOST_POPULAR;
                    new MovieAsyncTask().execute(JsonUtils.POPULARITY);
                } else if (position == IS_FAVORITE) {
                    sortType = IS_FAVORITE;
                    mMovieAdapter.setMovieList(mFavoriteMovies);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });

        return true;
    }

    /**
     * runs the fetchMovieData(moviesUrl) method at the background thread
     */
    public class MovieAsyncTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected void onPreExecute() {
            mMovieAdapter = (MovieAdapter) mMovieRecyclerView.getAdapter();
        }

        @Override
        protected List<Movie> doInBackground(String... url) {
            try {
                String moviesUrl = JsonUtils.buildUrl(url[0]);
                mMovieList = JsonUtils.fetchMovieData(moviesUrl);
                Thread.sleep(1000);
            } catch (IOException e) {
                e.printStackTrace();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return mMovieList;
        }


        @Override
        protected void onPostExecute(List<Movie> movies) {
            mMovieList = movies;
            if (movies != null) {
                mMovieAdapter.setMovieList(movies);
            } else {
                Toast.makeText(MainActivity.this, "Your movie list is empty", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        switch (sortType) {
            case MOST_POPULAR:
                new MovieAsyncTask().execute(JsonUtils.POPULARITY);
                break;
            case TOP_RATED:
                new MovieAsyncTask().execute(JsonUtils.TOP_RATED);
                break;
            default:
                mainViewModel.getFavoriteMovies().observe(this, new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(List<Movie> movies) {
                        mFavoriteMovies = movies;

                        //Toast.makeText(getApplicationContext(), "Favorite list is empty", Toast.LENGTH_LONG).show();
                        mMovieAdapter.setMovieList(mFavoriteMovies);
                        /*if (mFavoriteMovies.isEmpty()) {

                            Toast.makeText(getApplicationContext(), "Favorite list is empty", Toast.LENGTH_LONG).show();

                            mBinding.movieRecyclerView.setVisibility(View.INVISIBLE);
                            mBinding.cardViewEmptyList.setVisibility(View.VISIBLE);
                        }
                            mMovieAdapter.setMovieList(mFavoriteMovies);
                    }*/
                    }
                });
        }
    }
}
