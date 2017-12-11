package com.homecaravan.android.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.homecaravan.android.R;

/**
 * Created by RAINY on 6/22/2017.
 */

public class ListingItemDecoration extends RecyclerView.ItemDecoration {
    private Context mContext;

    public ListingItemDecoration(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, mContext.getResources().getDimensionPixelSize(R.dimen.item_decoration));
    }
}
