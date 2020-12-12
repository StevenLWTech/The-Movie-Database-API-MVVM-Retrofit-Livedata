package com.zybooks.practice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;


import com.google.android.material.tabs.TabLayout;
import com.zybooks.practice.Adapter.MovieAdapter;
import com.zybooks.practice.Adapter.OnMovieListener;
import com.zybooks.practice.Adapter.PackageTabAdapter;
import com.zybooks.practice.Model.MovieModel;
import com.zybooks.practice.Model.TVModel;
import com.zybooks.practice.viewmodels.MovieListViewModel;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements OnMovieListener {
    private String TAG = "PackageActivity";
    private Context context;
    private Toolbar toolbar;
    private SearchView mSearchView;
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    private FrameLayout mFramelayout;
    private PackageTabAdapter adapter;
    private MovieAdapter mAdapter;
    private RecyclerView mSearchRecyclerView;
    private MovieListViewModel mMovieListViewModel;
    private TextView mSearchText;
    final private String MovieData = "movies";
    final private String TVData = "shows";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchText = (TextView) findViewById(R.id.searchresultsText);
        mFramelayout = (FrameLayout) findViewById(R.id.framelayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(2);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mSearchView = findViewById(R.id.search_view);
        mMovieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);


        mSearchRecyclerView = findViewById(R.id.search_recycler);
        mAdapter = new MovieAdapter(this);
        mSearchRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mSearchRecyclerView.setAdapter(mAdapter);

        createTabFragment();
        subscribeObservers();
        initSearchView();
    }


    private void subscribeObservers() {
        //Observes when a query is done
        mMovieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(@Nullable List<MovieModel> movies) {
                if (movies != null) {
                    mAdapter.setMovies(movies);

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
//        mPopularView.setVisibility(View.GONE);
        super.onBackPressed();
    }

    private void initSearchView() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mMovieListViewModel.searchMoviesApi(query, 1);
                mFramelayout.setVisibility(View.VISIBLE);
                mSearchRecyclerView.setVisibility(View.VISIBLE);
                mSearchText.setVisibility(View.VISIBLE);


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mFramelayout.setVisibility(View.GONE);
                mSearchRecyclerView.setVisibility(View.GONE);
                mSearchText.setVisibility(View.GONE);
                return false;
            }
        });

    }

    private void createTabFragment() {
        // CHANGED: passing "this" since it is holding the callbackListener
        adapter = new PackageTabAdapter(getSupportFragmentManager(), tabLayout, this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //onMovieClick passes movieModel to intent
    @Override
    public void onMovieClick(MovieModel movieModel) {
//        Intent intent = new Intent(this.getApplicationContext(), MovieDetailActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(MovieData, movieModel);
//        this.getApplicationContext().startActivity(intent);
        startActivity(intent);

    }

    @Override
    public void onTvClick(TVModel tvModel) {
        Intent intent = new Intent(this.getApplicationContext(), MovieDetailActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        intent.putExtra(TVData, tvModel);
        this.getApplicationContext().startActivity(intent);
    }
}