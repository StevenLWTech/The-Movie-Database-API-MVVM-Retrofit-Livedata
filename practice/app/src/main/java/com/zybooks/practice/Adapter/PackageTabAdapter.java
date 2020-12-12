package com.zybooks.practice.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.zybooks.practice.Fragment.MoviesFragment;
import com.zybooks.practice.Fragment.TvShowsFragment;

/**
 * The tab adapter for HomeActivity
 */
public class PackageTabAdapter extends FragmentStatePagerAdapter {
    TabLayout tabLayout;

    // CHANGED: Added storing the listener so we can pass it to the fragments
    OnMovieListener mListener;

    public PackageTabAdapter(FragmentManager fm, TabLayout _tabLayout, OnMovieListener listener) {
        super(fm);
        this.tabLayout = _tabLayout;
        this.mListener = listener;
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new MoviesFragment();

            // CHANGED: Passing listener to the fragment
            ((MoviesFragment)fragment).setOnMovieListener(mListener);

        }
        else if (position == 1)
        {
            fragment = new TvShowsFragment();
            ((TvShowsFragment)fragment).setOnTvListener(mListener);
        }
        return fragment;
    }
    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Movies";
        }
        else if (position == 1)
        {
            title = "TV Shows";
        }
        return title;
    }
}
