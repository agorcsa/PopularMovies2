package com.example.andreeagorcsa.popularmovies2.complexadapter.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andreeagorcsa.popularmovies2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailerTextViewHolder extends RecyclerView.ViewHolder{

    public @BindView(R.id.trailer_label)
    TextView mTrailerLabel;

    public TrailerTextViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
