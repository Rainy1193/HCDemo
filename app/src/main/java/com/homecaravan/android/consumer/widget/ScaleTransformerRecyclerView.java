package com.homecaravan.android.consumer.widget;

import android.view.View;

import com.homecaravan.android.R;

public class ScaleTransformerRecyclerView implements CustomLayoutManager.ItemTransformer {

    private static final String TAG = "ScaleTransformerRecyclerView";


    @Override
    public void transformItem(CustomLayoutManager layoutManager, View item, float fraction) {
        item.setPivotX(item.getWidth() / 2.0f);
        item.setPivotY(item.getHeight());
        float scale = 1 - 0.1f * Math.abs(fraction);
        float alpha = 0.5f * Math.abs(fraction);
        item.setScaleX(scale);
        item.setScaleY(scale);
        item.findViewById(R.id.viewBg).setAlpha(alpha);
    }
}
