package com.aweshams.cinematch.ui.viewholders;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aweshams.cinematch.R;
import com.aweshams.cinematch.ui.controls.OnRecyclerViewListItemClickListener;
import com.aweshams.cinematch.models.MovieItem;

/**
 * Created by irteza on 2018-03-02.
 */

public class MovieItemViewHolder extends BaseViewHolder<MovieItem, OnRecyclerViewListItemClickListener<MovieItem>> {

    private MovieItem mData;
    ImageView _coverImage;
    TextView _releaseDate;
    TextView _title;
    TextView _genre;
    ImageView _TMDBlogo;
    TextView _rating;

    public MovieItemViewHolder(View itemView) {
        super(itemView);
        _coverImage = (ImageView) itemView.findViewById(R.id.cover_image);
        _releaseDate = (TextView) itemView.findViewById(R.id.release_date);
        _title = (TextView) itemView.findViewById(R.id.title);
        _genre = (TextView) itemView.findViewById(R.id.genre);
        _TMDBlogo = (ImageView) itemView.findViewById(R.id.tmdb_logo);
        _rating = (TextView) itemView.findViewById(R.id.rating);
    }

    @Override
    public void bind(MovieItem item, @Nullable OnRecyclerViewListItemClickListener<MovieItem> listener) {

        mData = item;
        _coverImage.setImageBitmap(mData.poster);
        _releaseDate.setText(mData.release_date);
        _title.setText(mData.title);
        _rating.setText(mData.rating);
    }
}
