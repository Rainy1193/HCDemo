package com.homecaravan.android.consumer.listener;


import android.support.v7.widget.RecyclerView;

/**
 * The interface Swiped helper.
 * @author Dau Hung
 */
public interface ISwipedHelper {
    /**
     * M get movement flags.
     *
     * @param position the position
     */
    void mGetMovementFlags(int position);

    /**
     * M on move.
     */
    void mOnMove();

    /**
     * M on swiped.
     *
     * @param viewHolder the view holder
     * @param direction  the direction
     */
    void mOnSwiped(RecyclerView.ViewHolder viewHolder, int direction);

    /**
     * M on child draw.
     */
    void mOnChildDraw();
}
