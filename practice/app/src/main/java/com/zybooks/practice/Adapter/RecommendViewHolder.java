package com.zybooks.practice.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zybooks.practice.R;

public class RecommendViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView title, release_date;
    RatingBar vote_average;
    ImageView image;
    OnRecommendListener onClickListener;

    public RecommendViewHolder(@NonNull View itemView, OnRecommendListener onClickListener) {
        super(itemView);
        this.onClickListener = onClickListener;

        title = itemView.findViewById(R.id.movie_title);
        vote_average = itemView.findViewById(R.id.movie_social_score);

        image = itemView.findViewById(R.id.movie_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onClickListener.onRecommendMovieClick(getAdapterPosition());
    }
}
