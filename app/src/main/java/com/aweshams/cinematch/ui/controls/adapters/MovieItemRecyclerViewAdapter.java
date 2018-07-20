package com.aweshams.cinematch.ui.controls.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.aweshams.cinematch.R;
import com.aweshams.cinematch.ui.controls.OnRecyclerViewListItemClickListener;
import com.aweshams.cinematch.models.MovieItem;
import com.aweshams.cinematch.ui.viewholders.MovieItemViewHolder;

/**
 * Created by irteza on 2018-05-24.
 */

public class MovieItemRecyclerViewAdapter extends GenericRecyclerViewAdapter<MovieItem, OnRecyclerViewListItemClickListener<MovieItem>, MovieItemViewHolder> {

    /**
     * Base constructor.
     * Allocate adapter-related objects here if needed.
     *
     * @param context Context needed to retrieve LayoutInflater
     */
    public MovieItemRecyclerViewAdapter(Context context) {
        super(context);
    }

    @Override
    public MovieItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        int layoutIdForListItem = R.layout.fragment_movie_list_item;

        View view = inflate(layoutIdForListItem, viewGroup, false);

        return new MovieItemViewHolder(view);
    }

}