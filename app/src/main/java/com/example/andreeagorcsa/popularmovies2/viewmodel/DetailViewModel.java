package com.example.andreeagorcsa.popularmovies2.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.andreeagorcsa.popularmovies2.database.MovieRepository;
import com.example.andreeagorcsa.popularmovies2.models.Movie;

import java.util.List;

public class DetailViewModel extends AndroidViewModel {

    private MovieRepository mRepository;

    private LiveData<List<Movie>> mFavoriteMovies;

    private Movie movie;

    private MutableLiveData<Boolean> isFavoriteMutable = new MutableLiveData<>();

    // constructor
    public DetailViewModel(Application application, Movie movie) {
        super(application);
        mRepository = new MovieRepository(application);
        mFavoriteMovies = mRepository.getFavoriteMovies();
        this.movie = movie;

        mRepository.isMovieFavorite(movie);
        isFavoriteMutable.setValue(mRepository.isFavourite);
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

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
