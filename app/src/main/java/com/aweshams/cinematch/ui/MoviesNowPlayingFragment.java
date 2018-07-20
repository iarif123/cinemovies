package com.aweshams.cinematch.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aweshams.cinematch.CinematchApplication;
import com.aweshams.cinematch.R;
import com.aweshams.cinematch.ui.controls.EndlessRecyclerViewScrollListener;
import com.aweshams.cinematch.ui.controls.adapters.MovieItemRecyclerViewAdapter;
import com.aweshams.cinematch.managers.MovieManager;
import com.aweshams.cinematch.models.MovieItem;


import javax.inject.Inject;

/**
 * Created by irteza on 2018-04-25.
 */

public class MoviesNowPlayingFragment extends TabItemFragment {

    private final static String LOG_TAG = "MoviesNowPlayingFragment";

    @Inject MovieManager _movieManager;

    private boolean _isLoadingData;
    private MovieItemRecyclerViewAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;

    public MoviesNowPlayingFragment() {
        super();

        CinematchApplication
                .instance
                .getComponent()
                .inject(this);

        setTitle("Now Playing");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_home_base, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.home_base_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);


        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        // find views to populate

        mRecyclerView.addOnScrollListener(scrollListener);
    }

    @Override
    public void onStart(){
        super.onStart();

        adapter = new MovieItemRecyclerViewAdapter(getContext());
        setRecyclerViewAdapter(adapter);

        loadData();

    }

    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

    }

    @Override
    protected void onConnectionGained() {

    }

    /**
     * Makes the service calls to load data into the adapter
     */
    public void loadData() {

        _movieManager.getNowPlayingMovies(1)
                .then(result -> {
                    adapter.updateList(result);
                    return result;
                });
    }

    public void loadNextDataFromApi(int offset) {

        _movieManager.getNowPlayingMovies(offset + 1)
                .then(result -> {
                    for (MovieItem item : result) {
                        mAdapter.addItem(item);
                    }

                    return null;
                }).error(e -> {
                    Log.d("TEST", e.getMessage());
                });

        scrollListener.resetState();
    }

}
