package com.zybooks.practice.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;
import com.zybooks.practice.Model.TVModel;
import com.zybooks.practice.R;

import java.util.List;

/**
 *
 *Adapter for TV Shows in TvShowsFragment
 */
public class TVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnMovieListener mOnTvListener;
    private List<TVModel> mShows;
    private static final String TAG = "TrendingAdapter";

    public TVAdapter(OnMovieListener onTvListener) {
        this.mOnTvListener = onTvListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reycler_item_list, parent, false);
        return new tvViewHolder(view, mOnTvListener);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            String posterPath = "https://image.tmdb.org/t/p/w500/" + mShows.get(position).getPoster_path();
            ((tvViewHolder) holder).title.setText(mShows.get(position).getOriginal_name());
            Picasso.get().load(posterPath).into(((tvViewHolder) holder).image);
            float rating = mShows.get(position).getVote_average() / 2;
            ((tvViewHolder) holder).vote_average.setRating(rating);

            ((tvViewHolder) holder).tvModel = mShows.get(position);
    }


    @Override
    public int getItemCount() {
        if (mShows != null) {
            return mShows.size();
        }
        return 0;
    }

    public void setShows(List<TVModel> shows) {
        mShows = shows;
        notifyDataSetChanged();
    }

}