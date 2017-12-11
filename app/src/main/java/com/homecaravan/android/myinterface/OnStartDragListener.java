package com.homecaravan.android.myinterface;
import android.support.v7.widget.RecyclerView;

/**
 * Created by vankhoadesign on 7/18/16.
 */
public interface OnStartDragListener {
    /**
     * Called when a view is requesting a start of a drag.
     *
     * @param viewHolder The holder of the view to drag.
     */
    void onStartDrag(RecyclerView.ViewHolder viewHolder);

    void onEndDrag(RecyclerView.ViewHolder viewHolder);
}
