package com.example.andreeagorcsa.popularmovies2.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.andreeagorcsa.popularmovies2.models.Movie;

import java.util.List;

public class MovieRepository {

    private MovieDAO movieDAO;

    private LiveData<List<Movie>> favoriteMovies;

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
}
