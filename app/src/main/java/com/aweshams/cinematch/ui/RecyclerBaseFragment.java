package com.aweshams.cinematch.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aweshams.cinematch.R;
import com.aweshams.cinematch.ui.controls.adapters.MovieItemRecyclerViewAdapter;
import com.aweshams.cinematch.ui.controls.adapters.GenericRecyclerViewAdapter;

/**
 * Created by irteza on 2018-02-28.
 */

public class RecyclerBaseFragment extends BaseHomeFragment {

    protected RecyclerView mRecyclerView;
    protected MovieItemRecyclerViewAdapter mAdapter;
    protected RecyclerView.OnScrollListener mSrollListener;

    public RecyclerBaseFragment() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_home_base, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.home_base_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }

    /**
     * Assign a {@link GenericRecyclerViewAdapter} to be used with recycler view on this fragment
     *
     * @param adapter The {@link GenericRecyclerViewAdapter} implementation for use in the view
     */
    protected void setRecyclerViewAdapter(MovieItemRecyclerViewAdapter adapter) {
        mAdapter = adapter;
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * @return true if an assigned adapter for {@link RecyclerView} is not null
     */
    protected boolean hasRecyclerViewAdapter() {
        return mAdapter != null;
    }

    /**
     * Method is not NULL safe
     *
     * @return The assigned {@link GenericRecyclerViewAdapter} if assigned to the {@link RecyclerView}, null otherwise
     */
    protected MovieItemRecyclerViewAdapter getRecyclerViewAdapter() {
        return mAdapter;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    protected void setScrollListener(RecyclerView.OnScrollListener listener) {
        mSrollListener = listener;
        mRecyclerView.addOnScrollListener(listener);
    }

    protected RecyclerView.OnScrollListener getScrollLestener() {
        return mSrollListener;
    }
}
