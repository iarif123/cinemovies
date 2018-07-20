package com.aweshams.cinematch.ui.controls.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.aweshams.cinematch.ui.MoviesHomeFragment;

import java.util.LinkedHashMap;

/**
 * Created by irteza on 2018-04-23.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    LinkedHashMap<Integer, Fragment> mFragmentCache = new LinkedHashMap<>();

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = mFragmentCache.containsKey(position) ? mFragmentCache.get(position)
                : new MoviesHomeFragment();

        /*if (savedState == null || f.getArguments() == null) {
            Bundle bundle = new Bundle();
            bundle.putInt("index", position);
            f.setArguments(bundle);
        } else if (!mFragmentCache.containsKey(position)) {
            f.setInitialSavedState(savedState);
        }*/
        mFragmentCache.put(position, f);
        return f;
    }

    /*@Override
    public void onDestroyItem(int position, Fragment fragment) {
        // onDestroyItem
        while (mFragmentCache.size() > 5) {
            Object[] keys = mFragmentCache.keySet().toArray();
            mFragmentCache.remove(keys[0]);
        }
    }*/

    @Override
    public String getPageTitle(int position) {
        return "item-" + position;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
