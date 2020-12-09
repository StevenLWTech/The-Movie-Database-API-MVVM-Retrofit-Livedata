package com.zybooks.practice.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zybooks.practice.Model.MovieModel;
import com.zybooks.practice.R;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView title, release_date;
    RatingBar vote_average;
    ImageView image;
    OnMovieListener onClickListener;

    // CHANGED: Storing data so we can pass it in the onClick
    MovieModel movieModel;


    public MovieViewHolder(@NonNull View itemView, OnMovieListener onClickListener) {
        super(itemView);
        this.onClickListener = onClickListener;

        title = itemView.findViewById(R.id.movie_title);
        vote_average = itemView.findViewById(R.id.movie_social_score);

        image = itemView.findViewById(R.id.movie_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // CHANGED: passing model with the click, will fail for tv shows
        if(onClickListener != null){
            onClickListener.onMovieClick(movieModel);
        }
    }
}