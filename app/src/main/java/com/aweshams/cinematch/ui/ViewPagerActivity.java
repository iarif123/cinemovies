package com.aweshams.cinematch.ui;

import android.content.DialogInterface;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

/**
 * Created by irteza on 2018-05-19.
 */

public class ViewPagerActivity extends BaseActivity {
    public ViewPager mViewPager;
    protected ArrayList<RecyclerBaseFragment> mFragments;

    public RecyclerBaseFragment getSelectedFragment() {
        int currentIndex = mViewPager.getCurrentItem();

        if(mFragments == null) {
            return null;
        } else {
            return mFragments.get(currentIndex);
        }

    }

    protected boolean isFragmentActive(Class c) {
        int currentIndex = mViewPager.getCurrentItem();
        RecyclerBaseFragment fragment = mFragments.get(currentIndex);
        return fragment.getClass() == c;
    }


    public void showErrorDialog(String msg, String actionText, DialogInterface.OnClickListener clickListener, Class c) {
        if  (isFragmentActive(c)) {
            super.showErrorDialog(msg, actionText, clickListener);
        }
    }

    public void showErrorDialog(String text, Class c) {
        if (isFragmentActive(c)) {
            showErrorDialog(text);
        }
    }
}
