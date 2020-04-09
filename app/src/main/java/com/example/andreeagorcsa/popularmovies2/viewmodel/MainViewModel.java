package com.example.andreeagorcsa.popularmovies2.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.andreeagorcsa.popularmovies2.database.MovieRepository;
import com.example.andreeagorcsa.popularmovies2.models.Movie;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private MovieRepository mRepository;

    private LiveData<List<Movie>> mFavoriteMovies;

    public MainViewModel(Application application) {
        super(application);
        mRepository = new MovieRepository(application);
        mFavoriteMovies = mRepository.getFavoriteMovies();
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return mFavoriteMovies;
    }

    public void insert(Movie movie) {
        mRepository.insert(movie);
    }

    public void update(Movie movie) {
        mRepository.update(movie);
    }

    public void delete(Movie movie) {
        mRepository.delete(movie);
    }
}
