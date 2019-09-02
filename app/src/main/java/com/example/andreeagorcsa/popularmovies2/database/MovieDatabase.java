package com.example.andreeagorcsa.popularmovies2.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.andreeagorcsa.popularmovies2.models.Movie;

@Database(entities = {Movie.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    // creates an instance of the MovieDatabase, which be will transformed into a singleton
    private static MovieDatabase movieDatabase;

    // accesses the DAO
    public abstract MovieDAO movieDAO();

    // synchronized = only one thread at one time can access this method, to avoid creating 2 instances of the DB
    public static synchronized MovieDatabase getInstance(Context context){
        if (movieDatabase == null) {
            movieDatabase = Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class, "movie_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return movieDatabase;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDatabaseAsyncTask(movieDatabase).execute();
        }
    };

    private static class PopulateDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {

        private MovieDAO movieDAO;

        private PopulateDatabaseAsyncTask(MovieDatabase db) {
            movieDAO = db.movieDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // introduce all the Movies here
            // movieDAO.insert(new Movie(""));
            return null;
        }
    }

}
