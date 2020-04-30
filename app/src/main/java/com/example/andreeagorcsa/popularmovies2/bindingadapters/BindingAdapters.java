package com.example.andreeagorcsa.popularmovies2.bindingadapters;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.example.andreeagorcsa.popularmovies2.R;
import com.squareup.picasso.Picasso;

public class BindingAdapters {

        @BindingAdapter({"imageUrl"})
        public static void loadImage(ImageView view, String imageUrl) {
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.cinema_poster)
                    .error(R.color.colorPrimaryDark)
                    .into(view);
        }
    }
