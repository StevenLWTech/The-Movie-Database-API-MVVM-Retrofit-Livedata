package com.zybooks.practice.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.zybooks.practice.Model.MovieModel;
import com.zybooks.practice.Model.RecommendModel;
import com.zybooks.practice.R;

import java.util.List;

/**
 * Adapter for Recommended Recyclerview in MovieDetailActivity
 */
public class RecommendMovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnRecommendListener onRecommendListener;
    private List<MovieModel> mMovies;

    public RecommendMovieAdapter(OnRecommendListener onRecommendListener) {
        this.onRecommendListener = onRecommendListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reycler_item_list, parent, false);
        return new RecommendViewHolder(view, onRecommendListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String posterPath = "https://image.tmdb.org/t/p/w500/" + mMovies.get(position).getPoster_path();
        ((RecommendViewHolder) holder).title.setText(mMovies.get(position).getOriginal_title());
        Picasso.get().load(posterPath).into(((RecommendViewHolder) holder).image);
        float rating = mMovies.get(position).getVote_average() / 2;
        ((RecommendViewHolder) holder).vote_average.setRating(rating);
    }

    @Override
    public int getItemCount() {
        if (mMovies != null) {
            return mMovies.size();
        }
        return 0;
    }
    public MovieModel getSelectedMovie(int position) {
        if (mMovies != null) {
            if (mMovies.size() > 0) {
                return mMovies.get(position);
            }
        }
        return null;
    }
    public void setMovies(List<MovieModel> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }
}
