package com.aweshams.cinematch.ui.controls.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aweshams.cinematch.ui.controls.BaseRecyclerViewListener;
import com.aweshams.cinematch.models.BaseItem;
import com.aweshams.cinematch.ui.viewholders.BaseViewHolder;

import java.util.Collections;
import java.util.List;

/**
 * Created by irteza on 2018-02-15.
 */

/**
 * Base generic RecyclerView adapter.
 * Handles basic logic such as adding/removing items,
 * setting listener, binding ViewHolders.
 * Extend the adapter for appropriate use case.
 *
 * @param <T>  type of objects, which will be used in the adapter's dataset
 * @param <L>  click listener {@link BaseRecyclerViewListener}
 * @param <VH> ViewHolder {@link BaseViewHolder}
 */
public abstract class GenericRecyclerViewAdapter<T extends BaseItem, L extends BaseRecyclerViewListener, VH extends BaseViewHolder<T, L>> extends RecyclerView.Adapter<VH> {

    private List<T> mItemList;
    private L listener;
    private LayoutInflater layoutInflater;
    private Context mContext;

    /**
     * Base constructor.
     * Allocate adapter-related objects here if needed.
     *
     * @param context Context needed to retrieve LayoutInflater
     */
    public GenericRecyclerViewAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mItemList = Collections.emptyList();
    }

    /**
     * To be implemented in as specific adapter
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @Override
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the itemView to reflect the item at the given
     * position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(VH holder, int position) {
        T item = mItemList.get(position);
        holder.bind(item, listener);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mItemList != null ? mItemList.size() : 0;
    }

    /**
     * Sets items to the adapter and notifies that data set has been changed.
     *
     * @param itemList items to set to the adapter
     * @throws IllegalArgumentException in case of setting `null` items
     */
    public void updateList(List<T> itemList) {
        mItemList = itemList;
        notifyDataSetChanged();
    }

    /**
     * Returns all items from the data set held by the adapter.
     *
     * @return All of items in this adapter.
     */
    public List<T> getList(){
        return mItemList;
    }

    /**
     * Returns an items from the data set at a certain position.
     *
     * @return All of items in this adapter.
     */
    public T getItem(int position) {
        return mItemList.get(position);
    }

    /**
     * Adds item to the end of the data set.
     * Notifies that item has been inserted.
     *
     * @param item item which has to be added to the adapter.
     */
    public void addItem(T item) {
        mItemList.add(item);
        notifyItemInserted(mItemList.size() - 1);
    }

    /**
     * Adds item to a specified position in the data set
     * Notifies that item has been inserted.
     *
     * @param item item which has to be added to the adapter.
     * @param position position at which to add the item.
     */
    public void addItem(T item, int position) {
        mItemList.add(position, item);
        notifyItemInserted(position);
    }

    /**
     * Adds list of items to the end of the adapter's data set.
     * Notifies that item has been inserted.
     *
     * @param items items which has to be added to the adapter.
     */
    public void addAll(List<T> items) {
        this.mItemList.addAll(items);
        notifyItemRangeInserted(this.mItemList.size() - items.size(), items.size());
    }

    /**
     * Clears all the items in the adapter.
     */
    public void clear() {
        mItemList.clear();
        notifyDataSetChanged();
    }

    /**
     * Removes an item from the adapter.
     * Notifies that item has been removed.
     *
     * @param item to be removed
     */
    public void remove(T item) {
        int position = mItemList.indexOf(item);
        if (position > -1) {
            mItemList.remove(position);
            notifyItemRemoved(position);
        }
    }

    /**
     * Removes item from a specified position in the data set
     * Notifies that item has been inserted.
     *
     * @param position position at which to remove the item.
     */
    public void removeItem(int position) {
        mItemList.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * Returns whether adapter is empty or not.
     *
     * @return `true` if adapter is empty or `false` otherwise
     */
    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    /**
     * Indicates whether each item in the data set can be represented with a unique identifier
     * of type {@link Long}.
     *
     * @param hasStableIds Whether items in data set have unique identifiers or not.
     * @see #hasStableIds()
     * @see #getItemId(int)
     */
    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }


    /**
     * Set click listener, which must extend {@link BaseRecyclerViewListener}
     *
     * @param listener click listener
     */
    public void setListener(L listener) {
        this.listener = listener;
    }

    /**
     * Inflates a view.
     *
     * @param layout       layout to me inflater
     * @param parent       container where to inflate
     * @param attachToRoot whether to attach to root or not
     * @return inflated View
     */
    @NonNull
    protected View inflate(@LayoutRes final int layout, @Nullable final ViewGroup parent, final boolean attachToRoot) {
        return layoutInflater.inflate(layout, parent, attachToRoot);
    }

    /**
     * Inflates a view.
     *
     * @param layout layout to me inflater
     * @param parent container where to inflate
     * @return inflated View
     */
    @NonNull
    protected View inflate(@LayoutRes final int layout, final @Nullable ViewGroup parent) {
        return inflate(layout, parent, false);
    }
}
