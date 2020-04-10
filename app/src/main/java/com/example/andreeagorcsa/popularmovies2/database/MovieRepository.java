package com.example.andreeagorcsa.popularmovies2.database;

import android.app.Application;


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

    public LiveData<Boolean> isFavourite = new LiveData<Boolean>() {
    };

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
        });
    }

    public void isMovieFavorite(Movie movie) {

        int id = movie.getId();

        MovieRoomDatabase.databaseWriteExecutor.execute(() -> {
            movieDAO.selectMovie(String.valueOf(id));
            isFavourite.setValue(true);

        });
    }
}



