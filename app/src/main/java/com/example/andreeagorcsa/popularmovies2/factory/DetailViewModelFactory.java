package com.example.andreeagorcsa.popularmovies2.factory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.andreeagorcsa.popularmovies2.models.Movie;
import com.example.andreeagorcsa.popularmovies2.viewmodel.DetailViewModel;

public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Application application;
    private Movie movie;

    public DetailViewModelFactory(Application application, Movie movie) {
        this.application = application;
        this.movie = movie;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == DetailViewModel.class) {
            return (T) new DetailViewModel(application, movie);
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}