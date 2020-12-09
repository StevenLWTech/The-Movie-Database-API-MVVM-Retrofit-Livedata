package com.zybooks.practice.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;
import com.zybooks.practice.Model.MovieModel;
import com.zybooks.practice.R;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnMovieListener mOnMovieListener;
    private OnRecommendListener mOnRecommendListener;
    private List<MovieModel> mMovies;
    private static final String TAG = "TrendingAdapter";

    public MovieAdapter(OnMovieListener onMovieListener) {

        this.mOnMovieListener = onMovieListener;
    }
    public MovieAdapter(OnRecommendListener mOnRecommendListener) {

        this.mOnRecommendListener = mOnRecommendListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reycler_item_list, parent, false);
        return new MovieViewHolder(view, mOnMovieListener);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (mMovies.get(position).getBackdrop_path() != null) {
            String posterPath = "https://image.tmdb.org/t/p/w500/" + mMovies.get(position).getPoster_path();
            ((MovieViewHolder) holder).title.setText(mMovies.get(position).getOriginal_title());
            Picasso.get().load(posterPath).into(((MovieViewHolder) holder).image);
            float rating = mMovies.get(position).getVote_average() / 2;
            ((MovieViewHolder) holder).vote_average.setRating(rating);

            // storing the object on the holder
            ((MovieViewHolder) holder).movieModel = mMovies.get(position);
        }
    }

    @Override
    public int getItemCount() {
        if (mMovies != null) {
            return mMovies.size();
        }
        return 0;
    }

    public void setMovies(List<MovieModel> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }


}