package com.example.andreeagorcsa.popularmovies2.adapters;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andreeagorcsa.popularmovies2.BR;
import com.example.andreeagorcsa.popularmovies2.R;
import com.example.andreeagorcsa.popularmovies2.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieItemViewHolder> {

    private final static String LOG_TAG = MovieAdapter.class.getSimpleName();

    private List<Movie> mMovieList;
    private ItemClickHandler itemClickHandler;

    /**
     * Constructor for the MovieAdapter
     *
     * @param itemClickHandler
     * @param mMovieList
     */
    public MovieAdapter(ItemClickHandler itemClickHandler, List<Movie> mMovieList) {
        this.itemClickHandler = itemClickHandler;
        this.mMovieList = new ArrayList<>();
    }

    /**
     * Inflates the layout, and creates the ViewHolder object required from the MovieAdapter
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MovieItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.movie_item, parent, false);
        return new MovieItemViewHolder(binding);
    }

    /**
     * updates the contents of the ItemView to reflect the movie in the given position.
     *
     * @param movieHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(MovieItemViewHolder movieHolder, int position) {
        Movie currentMovie = mMovieList.get(position);
        movieHolder.bind(currentMovie);
    }

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        if (mMovieList == null) {
            return 0;
        }
        return mMovieList.size();
    }

    // Getter method for the mMovieList
    private List<Movie> getMovieList() {
        return mMovieList;
    }

    // Setter method for the mMovieList
    public void setMovieList(List<Movie> mMovieList) {
        this.mMovieList = mMovieList;
        notifyDataSetChanged();
    }

    public interface ItemClickHandler {
        void onItemClick(Movie movie);
    }

    public class MovieItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ViewDataBinding binding;

        public MovieItemViewHolder(ViewDataBinding dataBinding) {
            super(dataBinding.getRoot());
            this.binding = dataBinding;
            itemView.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            this.binding.setVariable(BR.movie, movie);
            this.binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            itemClickHandler.onItemClick(mMovieList.get(getAdapterPosition()));
        }
    }
}
