package com.homecaravan.android.consumer.widget;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.homecaravan.android.consumer.adapter.DiscoverListListingAdapter;
import com.homecaravan.android.consumer.fastadapter.SearchListItem;
import com.homecaravan.android.consumer.listener.ISwipedHelper;


/**
 * The type Item touch helper callback.
 */
public class ItemTouchHelperCallback extends ItemTouchHelperExtension.Callback {
    private ISwipedHelper mHelper;
    private int mFlags = 0;
    private boolean isDraw;
    private boolean onSwiped = true;

    public boolean isOnSwiped() {
        return onSwiped;
    }


    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }

    /**
     * Sets helper.
     *
     * @param mHelper the m helper
     */
    public void setHelper(ISwipedHelper mHelper) {
        this.mHelper = mHelper;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        mFlags++;
        if (mFlags == -1) {
            mHelper.mGetMovementFlags(viewHolder.getAdapterPosition());
        }
        if (mFlags % 2 == 0) {
            onSwiped = false;
        }
        Log.e("mGetMovementFlags", "mGetMovementFlags");
        return makeMovementFlags(0, ItemTouchHelper.END | ItemTouchHelper.START);
    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
    /*    MainRecyclerAdapter adapter = (MainRecyclerAdapter) recyclerView.getAdapter();
        adapter.move(viewHolder.getAdapterPosition(), target.getAdapterPosition());*/
        mHelper.mOnMove();
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Log.e("onSwiped", "onSwiped");
        mFlags = 0;
        mHelper.mOnSwiped(viewHolder, direction);
        onSwiped = true;
        if (viewHolder instanceof DiscoverListListingAdapter.ListingHolder) {
            DiscoverListListingAdapter.ListingHolder holder = (DiscoverListListingAdapter.ListingHolder) viewHolder;
            if (viewHolder instanceof DiscoverListListingAdapter.ItemSwipeWithActionWidthNoSpringViewHolder) {
                Log.e("direction", String.valueOf(direction));
            }
        }
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        mHelper.mOnChildDraw();
        if (viewHolder instanceof DiscoverListListingAdapter.ListingHolder) {
            DiscoverListListingAdapter.ListingHolder holder = (DiscoverListListingAdapter.ListingHolder) viewHolder;
            if (viewHolder instanceof DiscoverListListingAdapter.ItemSwipeWithActionWidthNoSpringViewHolder) {
                if (dX < 0) {
                    holder.mLayoutLike.setVisibility(View.GONE);
                    holder.mLayoutDislike.setVisibility(View.VISIBLE);
                } else {
                    holder.mLayoutLike.setVisibility(View.VISIBLE);
                    holder.mLayoutDislike.setVisibility(View.GONE);
                }
                if (dX == 0) {
                    holder.mLayoutDislike.setVisibility(View.GONE);
                    holder.mLayoutLike.setVisibility(View.GONE);
                }
                if (dX < -holder.mActionContainer.getWidth()) {
                    dX = -holder.mActionContainer.getWidth();
                }
                holder.mViewContent.setTranslationX(dX);
            }
        }
        if (viewHolder instanceof SearchListItem.SearchListItemHolder && isDraw) {
            SearchListItem.SearchListItemHolder holder = (SearchListItem.SearchListItemHolder) viewHolder;
            if (viewHolder instanceof SearchListItem.ItemSwipeWithActionWidthNoSpringViewHolder) {
                if (dX < 0) {
                    holder.mLayoutLike.setVisibility(View.GONE);
                    holder.mLayoutDislike.setVisibility(View.VISIBLE);
                } else {
                    holder.mLayoutLike.setVisibility(View.VISIBLE);
                    holder.mLayoutDislike.setVisibility(View.GONE);
                }
                if (dX == 0) {
                    holder.mLayoutDislike.setVisibility(View.GONE);
                    holder.mLayoutLike.setVisibility(View.GONE);
                }
                if (dX < -holder.mActionContainer.getWidth()) {
                    dX = -holder.mActionContainer.getWidth();
                }
                if (Math.abs(dX) == holder.mActionContainer.getWidth()) {
                    holder.mLayoutLike.setEnabled(true);
                    holder.mLayoutDislike.setEnabled(true);
                } else {
                    holder.mLayoutLike.setEnabled(false);
                    holder.mLayoutDislike.setEnabled(false);
                }
                holder.mViewContent.setTranslationX(dX);
            }
        }
    }
}
