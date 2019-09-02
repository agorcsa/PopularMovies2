package com.example.andreeagorcsa.popularmovies2.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.andreeagorcsa.popularmovies2.models.Movie;

import java.util.List;

@Dao
// DAO =  Data Access Object
public interface MovieDAO {

    @Insert
    void insert(Movie movie);

    @Update
    void update(Movie movie);

    @Delete
    void delete(Movie movie);

    @Query("SELECT * FROM MOVIE_TABLE WHERE IS_FAVORITE = 1")
    // the object Movie ArrayList is observed by LiveData
    // all the changes of the DB will be notified by LiveData which sends the update to the interface
    LiveData<List<Movie>> showFavoriteMovies();
}
