package com.example.andreeagorcsa.popularmovies2.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.andreeagorcsa.popularmovies2.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieRepository {

    private MovieDAO movieDAO;
    private LiveData<ArrayList<Movie>> favoriteMovies;

    // constructor
    // application is a subclass of context
    public MovieRepository(Application application) {
        MovieDatabase movieDatabase = MovieDatabase.getInstance(application);
        movieDAO = movieDatabase.movieDAO();
        favoriteMovies = movieDAO.showFavoriteMovies();
    }

    public void insert(Movie movie) {
        new InsertMovieAsyncTask(movieDAO).execute(movie);
    }

    public void update(Movie movie) {
        new UpdateMovieAsyncTask(movieDAO).execute(movie);
    }

    public void delete(Movie movie) {
        new DeleteMovieAsyncTask(movieDAO).execute(movie);
    }

    public LiveData<ArrayList<Movie>> showFavoriteMovies() {
        new QueryMovieAsyncTask(movieDAO).execute();
        return favoriteMovies;
    }

    private static class InsertMovieAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDAO movieDAO;

        private InsertMovieAsyncTask(MovieDAO movieDAO) {
            this.movieDAO = movieDAO;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDAO.insert(movies[0]);
            return null;
        }
    }

    private static class UpdateMovieAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDAO movieDAO;

        private UpdateMovieAsyncTask(MovieDAO movieDAO) {
            this.movieDAO = movieDAO;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDAO.update(movies[0]);
            return null;
        }
    }

    private static class DeleteMovieAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDAO movieDAO;

        private DeleteMovieAsyncTask(MovieDAO movieDAO) {
            this.movieDAO = movieDAO;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDAO.delete(movies[0]);
            return null;
        }
    }

    private static class QueryMovieAsyncTask extends AsyncTask<Void, Void, Void> {

        private MovieDAO movieDAO;

        private QueryMovieAsyncTask(MovieDAO movieDAO) {
            this.movieDAO = movieDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            movieDAO.showFavoriteMovies();
            return null;
        }
    }
}
