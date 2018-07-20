package com.aweshams.cinematch.ui.controls.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.aweshams.cinematch.ui.TabItemFragment;

import java.util.ArrayList;

/**
 * Created by irteza on 2018-04-23.
 */

public class TabControlAdapter extends FragmentPagerAdapter {

    private final ArrayList<TabItemFragment> _fragments;

    public TabControlAdapter(FragmentManager manager, ArrayList<TabItemFragment> fragments) {
        super(manager);

        _fragments = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return _fragments.get(position).getTitle();
    }

    @Override
    public Fragment getItem(int position) { return _fragments.get(position); }

    @Override
    public int getCount() {
        return _fragments.size();
    }
}
