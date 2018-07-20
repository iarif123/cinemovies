package com.aweshams.cinematch.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aweshams.cinematch.R;
import com.aweshams.cinematch.ui.controls.adapters.TabControlAdapter;

import java.util.ArrayList;

/**
 * Created by irteza on 2018-02-28.
 */

public abstract class TabControlFragment extends Fragment {

    // used to set selected tab
    public String _selectedTab;

    protected final int _viewId;
    protected TabLayout _tabLayout;
    protected ViewPager _viewPager;
    protected ArrayList<TabItemFragment> _fragmentList;

    public TabControlFragment(int viewId) {

        // initialize instance variables
        _viewId = viewId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // inflate layout
        View view = inflater.inflate(_viewId, container, false);

        // initialize pager
        _viewPager = (ViewPager) view.findViewById(R.id.pager);
        _viewPager.setAdapter(createTabAdapter());

        // initialize tabs
        _tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        _tabLayout.setupWithViewPager(_viewPager);

        // set selected tab and reset flag
        if(_selectedTab != null) {

            setCurrentItemOnViewPager(_selectedTab);
            _selectedTab = null;
        }

        // return main view
        return view;
    }

    protected abstract TabControlAdapter createTabAdapter();

    // ViewPager Methods
    public void setCurrentItemOnViewPager(String selectedTab) {

        // set current item
        for(int i = 0; i < _fragmentList.size(); i++) {

            // retrieve current title
            CharSequence currentTitle = _fragmentList.get(i).getTitle();

            // select tab and break
            if(currentTitle.equals(selectedTab)) {
                _viewPager.setCurrentItem(i, true);
            }
        }
    }

    /**
     * Retrieves title of currently selected tab
     * @return
     */
    public String getCurrentItemOnViewPager() {

        int selectedTab = _viewPager.getCurrentItem();
        return _fragmentList.get(selectedTab).getTitle().toString();
    }
}
