package com.example.andreeagorcsa.popularmovies2.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.andreeagorcsa.popularmovies2.models.Movie;

import java.util.List;

// DAO =  Data Access Object
@Dao
public interface MovieDAO {

    //  ignores adding a new movie, if it's exactly the same as one already in the list
    @Insert(onConflict = OnConflictStrategy.IGNORE)

    void insert(Movie movie);

    @Update
    void update(Movie movie);

    @Delete
    void delete(Movie movie);

    // queries the favorite movies
    @Query("SELECT * FROM MOVIE_TABLE WHERE IS_FAVORITE = 1 ORDER BY MOVIE_ID")
    // the object Movie ArrayList is observed by LiveData
    // all the changes of the DB will be notified by LiveData which sends the update to the interface
    LiveData<List<Movie>> showFavoriteMovies();

    // queries the favorite movies
    @Query("SELECT * FROM MOVIE_TABLE ORDER BY MOVIE_ID")
    // the object Movie ArrayList is observed by LiveData
    // all the changes of the DB will be notified by LiveData which sends the update to the interface
    LiveData<List<Movie>> getFavoriteMovies();

    @Query("SELECT * FROM MOVIE_TABLE WHERE MOVIE_ID = :movieId")
    Movie selectMovie(String movieId);
}
