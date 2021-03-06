package com.example.andreeagorcsa.popularmovies2.complexadapter.viewholder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.andreeagorcsa.popularmovies2.R;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    public @BindView(R.id.reviewAuthor)
    TextView mReviewAuthorTextView;

    public @BindView(R.id.expand_text_view)
    ExpandableTextView expTv1;

    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
