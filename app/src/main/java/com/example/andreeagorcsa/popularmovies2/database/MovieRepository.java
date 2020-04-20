package com.example.andreeagorcsa.popularmovies2.database;

import android.app.Application;
import android.util.Log;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.RoomDatabase;

import com.example.andreeagorcsa.popularmovies2.models.Movie;

import java.util.List;

public class MovieRepository {

    private MovieDAO movieDAO;

    private LiveData<List<Movie>> favoriteMovies;

    private MovieRoomDatabase movieRoomDatabase;

    public MutableLiveData<Boolean> isFavourite = new MutableLiveData<>();

    private long longVar;

    // constructor
    // application is a subclass of context
    public MovieRepository(Application application) {
        MovieDatabase movieDatabase = MovieDatabase.getInstance(application);
        movieDAO = movieDatabase.movieDAO();
        favoriteMovies = movieDAO.showFavoriteMovies();
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void insert(Movie movie) {
        MovieRoomDatabase.databaseWriteExecutor.execute(() -> {
         movieDAO.insert(movie);
         isFavourite.postValue(true);
        });
    }

    public void update(Movie movie) {
        MovieRoomDatabase.databaseWriteExecutor.execute(() -> {
            movieDAO.update(movie);
        });
    }

    public void delete(Movie movie) {
        MovieRoomDatabase.databaseWriteExecutor.execute(() -> {
            movieDAO.delete(movie);
            isFavourite.postValue(false);
        });
    }

    public void isMovieFavorite(Movie movie) {
        int movieId = movie.getMovieId();
        MovieRoomDatabase.databaseWriteExecutor.execute(() -> {
            Movie favMovie = movieDAO.selectMovie(String.valueOf(movieId));
            if (favMovie != null) {
                isFavourite.postValue(true);
            } else {
                isFavourite.postValue(false);
            }
        });
    }

    public void toggleFavorite(Movie movie) {
        if (isFavourite.getValue()!= null && isFavourite.getValue()) {
            delete(movie);
        } else {
            insert(movie);
        }
    }
}



