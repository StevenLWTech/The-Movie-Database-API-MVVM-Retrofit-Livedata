package com.zybooks.practice.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zybooks.practice.Adapter.MovieAdapter;
import com.zybooks.practice.Adapter.OnMovieListener;
import com.zybooks.practice.Adapter.TVAdapter;
import com.zybooks.practice.Model.MovieModel;
import com.zybooks.practice.requests.responses.MovieResponse;
import com.zybooks.practice.util.Constants;
//import com.zybooks.practice.DetailActivity.java;
import com.zybooks.practice.Model.TVModel;
import com.zybooks.practice.requests.MovieApi;
import com.zybooks.practice.requests.responses.TvResponse;
import com.zybooks.practice.R;
import com.zybooks.practice.requests.ServiceGenerator;
import com.zybooks.practice.viewmodels.MovieListViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragment displays list of tv shows & categories
 */
public class TvShowsFragment extends Fragment {
    private static final String TAG = "TVFragment";
    private RecyclerView popularView;
    private RecyclerView topRatedView;
    private RecyclerView sciFiView;
    private RecyclerView warView;
    private TVAdapter mPopularAdapter, mTopRatedAdapter, mSciFiAdapter, mWarAdapter;

    OnMovieListener mListener;

    public void setOnTvListener(OnMovieListener listener) {
        mListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_shows, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        popularView = view.findViewById(R.id.popular_movie_recycler);
        topRatedView = view.findViewById(R.id.top_rated_show_recycler);
        sciFiView = view.findViewById(R.id.sci_fi_show_recycler);
        warView = view.findViewById(R.id.war_show_recycler);

        initRecyclerView();
        subscribeObservers();

    }
    //region Initialize Recyclerview
    private void initRecyclerView() {
        if (mPopularAdapter == null) {
            mPopularAdapter = new TVAdapter(mListener);
            popularView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            popularView.setAdapter(mPopularAdapter);
            popularView.setItemAnimator(new DefaultItemAnimator());
            popularView.setNestedScrollingEnabled(true);
        } else {
            mPopularAdapter.notifyDataSetChanged();
        }
        if (mTopRatedAdapter == null) {
            mTopRatedAdapter = new TVAdapter(mListener);
            topRatedView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            topRatedView.setAdapter(mTopRatedAdapter);
            topRatedView.setItemAnimator(new DefaultItemAnimator());
            topRatedView.setNestedScrollingEnabled(true);
        } else {
            mTopRatedAdapter.notifyDataSetChanged();
        }
        if (mSciFiAdapter == null) {
            mSciFiAdapter = new TVAdapter(mListener);
            sciFiView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            sciFiView.setAdapter(mSciFiAdapter);
            sciFiView.setItemAnimator(new DefaultItemAnimator());
            sciFiView.setNestedScrollingEnabled(true);
        } else {
            mSciFiAdapter.notifyDataSetChanged();
        }
        if (mWarAdapter == null) {
            mWarAdapter = new TVAdapter(mListener);
            warView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            warView.setAdapter(mWarAdapter);
            warView.setItemAnimator(new DefaultItemAnimator());
            warView.setNestedScrollingEnabled(true);
        } else {
            mWarAdapter.notifyDataSetChanged();
        }
    }
    //endregion

    //region LiveData
    private void subscribeObservers() {

        MovieListViewModel movieListViewModel = new ViewModelProvider(requireActivity()).get(MovieListViewModel.class);
        movieListViewModel.init();
        // Popular Show Live Data
        movieListViewModel.getPopularShows().observe(getViewLifecycleOwner(), new Observer<TvResponse>() {
            @Override
            public void onChanged(TvResponse tvResponse) {
                List<TVModel> list = tvResponse.getTVShows();
                mPopularAdapter.setShows(list);
                mPopularAdapter.notifyDataSetChanged();
            }
        });
        // Top Rated Show Live Data
        movieListViewModel.getTopRatedShows().observe(getViewLifecycleOwner(), new Observer<TvResponse>() {
            @Override
            public void onChanged(TvResponse tvResponse) {
                List<TVModel> list = tvResponse.getTVShows();
                mTopRatedAdapter.setShows(list);
                mTopRatedAdapter.notifyDataSetChanged();
            }
        });
        // Sci FI Show Live Data
        movieListViewModel.getSciFiShows().observe(getViewLifecycleOwner(), new Observer<TvResponse>() {
            @Override
            public void onChanged(TvResponse tvResponse) {
                List<TVModel> list = tvResponse.getTVShows();
                mSciFiAdapter.setShows(list);
                mSciFiAdapter.notifyDataSetChanged();
            }
        });
        // War Show Live Data
        movieListViewModel.getWarShows().observe(getViewLifecycleOwner(), new Observer<TvResponse>() {
            @Override
            public void onChanged(TvResponse tvResponse) {
                List<TVModel> list = tvResponse.getTVShows();
                mWarAdapter.setShows(list);
                mWarAdapter.notifyDataSetChanged();
            }
        });

    }
    //endregion

}