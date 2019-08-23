package com.example.andreeagorcsa.popularmovies2.complexadapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.andreeagorcsa.popularmovies2.R;
import com.example.andreeagorcsa.popularmovies2.models.Trailer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public @BindView(R.id.play_trailer)
    Button mTrailerButton;

    public @BindView(R.id.trailer_name)
    TextView mTrailerNameTextView;

    Trailer trailer;

    public void setItemClickHandler(ItemClickHandler itemClickHandler, Trailer trailer) {
        this.itemClickHandler = itemClickHandler;
        this.trailer = trailer;
    }

    private ItemClickHandler itemClickHandler;

    public interface ItemClickHandler {
        void onItemClick(Trailer trailer);
    }

    public TrailerViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickHandler.onItemClick(trailer);
    }
}