package com.homecaravan.android.ui;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

/**
 * Created by RAINY on 7/7/2016.
 */
public class CustomLayoutManager extends GridLayoutManager {


    public CustomLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }
}