package com.example.andreeagorcsa.popularmovies2.viewmodel;

import android.app.Application;
import android.widget.Button;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.andreeagorcsa.popularmovies2.database.MovieRepository;
import com.example.andreeagorcsa.popularmovies2.models.Movie;

import java.util.List;

public class DetailViewModel extends AndroidViewModel {

    private MovieRepository mRepository;

    private LiveData<List<Movie>> mFavoriteMovies;

    private Movie movie;

    public LiveData<Boolean> isFavoriteVM;

    // constructor
    public DetailViewModel(Application application, Movie movie) {
        super(application);
        mRepository = new MovieRepository(application);
        mFavoriteMovies = mRepository.getFavoriteMovies();
        this.movie = movie;

        mRepository.isMovieFavorite(movie);
        isFavoriteVM = mRepository.isFavourite;
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return mFavoriteMovies;
    }

   public void toggleFavoriteButton(Movie movie) {
       mRepository.toggleFavorite(this.movie);
   }

   public void playFirstTrailer(Movie movie) {

   }

    public void update(Movie movie) {
        mRepository.update(movie);
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public LiveData<Boolean> getIsFavoriteVM() {
        return isFavoriteVM;
    }

    public void setIsFavoriteVM(LiveData<Boolean> isFavoriteVM) {
        this.isFavoriteVM = isFavoriteVM;
    }
}
