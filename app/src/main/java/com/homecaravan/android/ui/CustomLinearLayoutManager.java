package com.homecaravan.android.ui;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by RAINY on 7/8/2016.
 */
public class CustomLinearLayoutManager extends LinearLayoutManager {

    public CustomLinearLayoutManager(Context context) {
        super(context);
    }

    public CustomLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }
}
