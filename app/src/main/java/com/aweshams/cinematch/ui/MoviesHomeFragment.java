package com.aweshams.cinematch.ui;

import com.aweshams.cinematch.CinematchApplication;
import com.aweshams.cinematch.R;
import com.aweshams.cinematch.services.api.tmdb.enums.MovieSummaryType;
import com.aweshams.cinematch.services.api.tmdb.models.MovieSummary;
import com.aweshams.cinematch.ui.controls.adapters.TabControlAdapter;

import java.util.ArrayList;

/**
 * Created by irteza on 2018-04-23.
 */

public class MoviesHomeFragment extends TabControlFragment {

    public MoviesHomeFragment() {
        super(R.layout.fragment_home_tab_layout);

        CinematchApplication
                .instance
                .getComponent()
                .inject(this);
    }

    @Override
    protected TabControlAdapter createTabAdapter() {
        // create tab list
        _fragmentList = new ArrayList<>();

        // add fragments
        _fragmentList.add(new MoviesListFragment(MovieSummaryType.NOW_PLAYING, "Now Playing"));
        _fragmentList.add(new MoviesListFragment(MovieSummaryType.TOP_RATED, "Top Rated"));
        _fragmentList.add(new MoviesListFragment(MovieSummaryType.UPCOMING, "Upcoming"));

        // create adapter and return it
        return new TabControlAdapter(getChildFragmentManager(), _fragmentList);
    }
}
