package com.aweshams.cinematch.ui;

import com.aweshams.cinematch.CinematchApplication;
import com.aweshams.cinematch.R;
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
        _fragmentList.add(new MoviesNowPlayingFragment());

        // create adapter and return it
        return new TabControlAdapter(getChildFragmentManager(), _fragmentList);
    }
}
