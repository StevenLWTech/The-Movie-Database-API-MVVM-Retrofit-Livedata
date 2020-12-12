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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zybooks.practice.Model.MovieModel;
import com.zybooks.practice.Adapter.OnMovieListener;
import com.zybooks.practice.requests.responses.MovieResponse;
import com.zybooks.practice.R;
import com.zybooks.practice.Adapter.MovieAdapter;
import com.zybooks.practice.viewmodels.MovieListViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment displays list of movies & categories
 */
public class MoviesFragment extends Fragment{
    private RecyclerView popularView, familyView, trendingView, actionView, mysteryView;
    private MovieAdapter mTrendingAdapter, mPopularAdapter, mActionAdapter, mMysteryAdapter, mFamilyAdapter;

    // CHANGED: Added reference to the listener that was passed via PackageTabAdapter
    OnMovieListener mListener;

    public void setOnMovieListener(OnMovieListener listener){
        mListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_movies, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        popularView = view.findViewById(R.id.popular_movie_recycler);
        trendingView = view.findViewById(R.id.trending_movie_recycler);
        actionView = view.findViewById(R.id.action_movie_recycler);
        mysteryView = view.findViewById(R.id.mystery_movie_recycler);
        familyView = view.findViewById(R.id.family_movie_recycler);

        initRecyclerView();
        subscribeObservers();

    }
    //region Setup Recyclerview properties
    private void initRecyclerView() {
        if (mPopularAdapter == null) {
            mPopularAdapter = new MovieAdapter(mListener);
            popularView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            popularView.setAdapter(mPopularAdapter);
            popularView.setItemAnimator(new DefaultItemAnimator());
            popularView.setNestedScrollingEnabled(true);
        } else {
            mPopularAdapter.notifyDataSetChanged();
        }
        if (mTrendingAdapter == null) {
            mTrendingAdapter = new MovieAdapter(mListener);
            trendingView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            trendingView.setAdapter(mTrendingAdapter);
            trendingView.setItemAnimator(new DefaultItemAnimator());
            trendingView.setNestedScrollingEnabled(true);
        } else {
            mTrendingAdapter.notifyDataSetChanged();
        }
        if (mActionAdapter == null) {
            mActionAdapter = new MovieAdapter(mListener);
            actionView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            actionView.setAdapter(mActionAdapter);
            actionView.setItemAnimator(new DefaultItemAnimator());
            actionView.setNestedScrollingEnabled(true);
        } else {
            mPopularAdapter.notifyDataSetChanged();
        }
        if (mMysteryAdapter == null) {
            mMysteryAdapter = new MovieAdapter(mListener);
            mysteryView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            mysteryView.setAdapter(mMysteryAdapter);
            mysteryView.setItemAnimator(new DefaultItemAnimator());
            mysteryView.setNestedScrollingEnabled(true);
        } else {
            mMysteryAdapter.notifyDataSetChanged();
        }
        if (mFamilyAdapter == null) {
            mFamilyAdapter = new MovieAdapter(mListener);
            familyView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            familyView.setAdapter(mFamilyAdapter);
            familyView.setItemAnimator(new DefaultItemAnimator());
            familyView.setNestedScrollingEnabled(true);
        } else {
            mFamilyAdapter.notifyDataSetChanged();
        }
    }
    //endregion
    //region Setup Livedata
    private void subscribeObservers() {
        MovieListViewModel movieListViewModel = new ViewModelProvider(requireActivity()).get(MovieListViewModel.class);
        movieListViewModel.init();

        movieListViewModel.getPopularMovies().observe(getViewLifecycleOwner(), new Observer<MovieResponse>() {
            @Override
            public void onChanged(MovieResponse movieResponse) {
                List<MovieModel> list = movieResponse.getMovies();
                mPopularAdapter.setMovies(list);
                mPopularAdapter.notifyDataSetChanged();
            }
        });

        movieListViewModel.getTrendingMovies().observe(getViewLifecycleOwner(), new Observer<MovieResponse>() {
            @Override
            public void onChanged(MovieResponse movieResponse) {
                List<MovieModel> list = movieResponse.getMovies();
                mTrendingAdapter.setMovies(list);
                trendingView.setAdapter(mTrendingAdapter);
            }
        });
        movieListViewModel.getActionMovies().observe(getViewLifecycleOwner(), new Observer<MovieResponse>() {
            @Override
            public void onChanged(MovieResponse movieResponse) {
                List<MovieModel> list = movieResponse.getMovies();
                mActionAdapter.setMovies(list);
                actionView.setAdapter(mActionAdapter);
            }
        });
        movieListViewModel.getMysteryMovies().observe(getViewLifecycleOwner(), new Observer<MovieResponse>() {
            @Override
            public void onChanged(MovieResponse movieResponse) {
                List<MovieModel> list = movieResponse.getMovies();
                mMysteryAdapter.setMovies(list);
                mysteryView.setAdapter(mMysteryAdapter);

            }
        });
        movieListViewModel.getFamilyMovies().observe(getViewLifecycleOwner(), new Observer<MovieResponse>() {
            @Override
            public void onChanged(MovieResponse movieResponse) {
                List<MovieModel> list = movieResponse.getMovies();
                mFamilyAdapter.setMovies(list);
                familyView.setAdapter(mFamilyAdapter);
            }
        });
    }
    //endregion
}