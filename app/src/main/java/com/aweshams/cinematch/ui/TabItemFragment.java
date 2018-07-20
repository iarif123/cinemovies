package com.aweshams.cinematch.ui;

/**
 * Created by irteza on 2018-02-28.
 */

public abstract class TabItemFragment extends RecyclerBaseFragment {

    private String _title;

    public TabItemFragment() {}

    public void setTitle(String title) {
        _title = title;
    }

    public CharSequence getTitle() {
        return _title;
    }


}
