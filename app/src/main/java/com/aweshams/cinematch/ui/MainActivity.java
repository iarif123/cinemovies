package com.aweshams.cinematch.ui;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;

import com.aweshams.cinematch.R;
import com.aweshams.cinematch.controls.EndlessRecyclerViewScrollListener;
import com.aweshams.cinematch.controls.adapters.RecyclerViewAdapter;
import com.aweshams.cinematch.serviceclients.tmdb.TMDbApiClient;
import com.aweshams.cinematch.serviceclients.tmdb.models.MovieSummary;
import com.aweshams.cinematch.serviceclients.tmdb.models.MovieSummaryList;
import com.aweshams.cinematch.utils.promises.Promise;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    // temp remove later
    private ListView mCards;

    private static final int NUM_LIST_MOVIES = 100;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView mMoviesList;
    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesList = (RecyclerView) findViewById(R.id.rv_movies_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mMoviesList.setLayoutManager(layoutManager);

        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };

        mMoviesList.addOnScrollListener(scrollListener);

        mAdapter = new RecyclerViewAdapter();

        mMoviesList.setAdapter(mAdapter);

        new TestUrl(mAdapter).execute();
        // initializeDrawer();
    }

    private void initializeDrawer() {
        mPlanetTitles = getResources().getStringArray(R.array.drawer_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //mDrawerList = (ListView) findViewById(R.id.drawer_list);

        // Set the adapter for the list view
        //mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                //R.layout.drawer_list_item, mPlanetTitles));
        // Set the list's click listener
        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`

        OkHttpClient client = new OkHttpClient();
        TMDbApiClient tmDbApiClient = new TMDbApiClient(client);
        Promise<MovieSummaryList> promise = tmDbApiClient.getNowPlayingMovies(offset + 1);

        promise.then(result -> {
            for (MovieSummary summary : result.results) {
                mAdapter.addItem(mAdapter.getItemCount(), summary);
            }
            return null;
        }).error(e -> {
            Log.d("TEST", e.getMessage());
        });

        scrollListener.resetState();
    }
}
