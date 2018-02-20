package com.aweshams.cinematch.controls.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aweshams.cinematch.R;
import com.aweshams.cinematch.serviceclients.tmdb.models.MovieSummary;
import com.aweshams.cinematch.utils.DownloadImageWithURLTask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

/**
 * Created by irteza on 2018-02-15.
 */

// TODO: generalize this
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MovieViewHolder> {

    private List<MovieSummary> _movies;

    public RecyclerViewAdapter() {
        this._movies = Collections.emptyList();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        MovieViewHolder viewHolder = new MovieViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(_movies.get(position));
    }

    @Override
    public int getItemCount() {
        return _movies.size();
    }

    //region public methods
    public void updateList(List<MovieSummary> moviesList)
    {
        _movies = moviesList;
        notifyDataSetChanged();
    }

    public List<MovieSummary> getList(){
        return _movies;
    }

    public void addItem(int position, MovieSummary data) {
        _movies.add(position, data);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        _movies.remove(position);
        notifyItemRemoved(position);
    }

    //endregion

    // TODO: extract this to a viewholder folder later
    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView coverImage;
        TextView releaseDate;
        TextView title;
        TextView genre;
        ImageView TMDBlogo;
        TextView rating;

        public MovieViewHolder(View itemView) {
            super(itemView);
            coverImage = (ImageView) itemView.findViewById(R.id.cover_image);
            releaseDate = (TextView) itemView.findViewById(R.id.release_date);
            title = (TextView) itemView.findViewById(R.id.title);
            genre = (TextView) itemView.findViewById(R.id.genre);
            TMDBlogo = (ImageView) itemView.findViewById(R.id.tmdb_logo);
            rating = (TextView) itemView.findViewById(R.id.rating);
        }

        public void bind(MovieSummary movieSummary) {

            coverImage.setImageDrawable(null);
            String url = "https://image.tmdb.org/t/p/w185" + movieSummary.poster_path;
            new DownloadImageWithURLTask(coverImage).execute(url);
            releaseDate.setText(movieSummary.release_date);
            title.setText(movieSummary.title);
            rating.setText(Double.toString(movieSummary.vote_average));
        }
    }
}
