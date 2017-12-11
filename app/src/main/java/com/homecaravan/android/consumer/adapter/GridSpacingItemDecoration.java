package com.homecaravan.android.consumer.adapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * The type Grid spacing item decoration.
 */
public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private int mSpace;

    /**
     * Instantiates a new Grid spacing item decoration.
     *
     * @param space the space
     */
    public GridSpacingItemDecoration(int space) {
        this.mSpace = space;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) % 2 == 0) {
            outRect.set(0, mSpace, mSpace, mSpace);
        } else {
            outRect.set(mSpace, mSpace, 0, mSpace);
        }
    }
}