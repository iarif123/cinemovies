package com.aweshams.cinematch.ui.viewholders;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aweshams.cinematch.ui.controls.BaseRecyclerViewListener;
import com.aweshams.cinematch.models.BaseItem;

/**
 * Created by irteza on 2018-03-02.
 */

public abstract class BaseViewHolder<T extends BaseItem, L extends BaseRecyclerViewListener> extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    /**
     * Bind data to the item and set listener if needed.
     *
     * @param item     object, associated with the item.
     * @param listener listener a listener {@link BaseRecyclerViewListener} which has to b set at the item (if not `null`).
     */
    public abstract void bind(T item, @Nullable L listener);
}
