package com.aweshams.cinematch.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aweshams.cinematch.CinematchApplication;
import com.aweshams.cinematch.R;
import com.aweshams.cinematch.managers.MovieManager;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {

    private TabControlFragment _tabControlFragment;

    public MainActivity() {
        CinematchApplication.instance
                .getComponent()
                .inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        // retrieve currently selected fragment
        String selectedTab = null;
        if (_tabControlFragment != null) {
            selectedTab = _tabControlFragment.getCurrentItemOnViewPager();
        }
        // being transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        _tabControlFragment = new MoviesHomeFragment();

        // set current item property on view pager
        _tabControlFragment._selectedTab = selectedTab;

        // add fragment
        fragmentTransaction.replace(R.id.main_fragment, _tabControlFragment);

        // end transaction
        fragmentTransaction.commit();
    }
}

