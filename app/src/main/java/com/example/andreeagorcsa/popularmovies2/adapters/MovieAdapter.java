package com.example.andreeagorcsa.popularmovies2.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
     * @param itemClickHandler
     * @param mMovieList
     */
    public MovieAdapter(ItemClickHandler itemClickHandler, List<Movie> mMovieList) {
        this.itemClickHandler = itemClickHandler;
        this.mMovieList = new ArrayList<>();
    }

    /**
     * Inflates the layout, and creates the ViewHolder object required from the MovieAdapter
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MovieItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieItemViewHolder(view);
    }

    /**
     * updates the contents of the ItemView to reflect the movie in the given position.
     * @param movieHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(MovieItemViewHolder movieHolder, int position) {
        Movie currentMovie = mMovieList.get(position);
        String moviePoster = currentMovie.getPosterPath();
        String movieTitle = currentMovie.getOriginalTitle();

        Picasso.get()
                .load(moviePoster)
                .placeholder(R.drawable.cinema_poster)
                .into(movieHolder.mMoviePosterImageView);

        movieHolder.mMovieTitleTextView.setText(movieTitle);
    }

    /**
     *
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
        @BindView(R.id.movie_poster)
        ImageView mMoviePosterImageView;
        @BindView(R.id.movie_title)
        TextView mMovieTitleTextView;


        public MovieItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickHandler.onItemClick(mMovieList.get(getAdapterPosition()));
        }
    }
}
