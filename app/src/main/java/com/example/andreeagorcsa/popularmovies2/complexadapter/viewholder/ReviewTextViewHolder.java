package com.example.andreeagorcsa.popularmovies2.complexadapter.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andreeagorcsa.popularmovies2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewTextViewHolder extends RecyclerView.ViewHolder {

    public @BindView(R.id.review_label)
    TextView mReviewLabel;

    public ReviewTextViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
