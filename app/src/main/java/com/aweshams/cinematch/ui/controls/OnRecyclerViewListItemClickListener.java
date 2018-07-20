package com.aweshams.cinematch.ui.controls;

/**
 * Created by irteza on 2018-05-24.
 */

public interface OnRecyclerViewListItemClickListener<T> extends BaseRecyclerViewListener {

    /**
     * Item has been clicked.
     *
     * @param item object associated with the clicked item.
     */
    void onItemClicked(T item);
}
