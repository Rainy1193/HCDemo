package com.homecaravan.android.consumer.widget;

import android.view.View;

import com.homecaravan.android.R;

public class ScaleTransformerReviewRecyclerView implements CustomLayoutManager.ItemTransformer {
    private static final String TAG = "ScaleTransformerRecyclerView";


    @Override
    public void transformItem(CustomLayoutManager layoutManager, View item, float fraction) {
        item.setPivotX(item.getWidth() / 2.0f);
        item.setPivotY(item.getHeight() / 2.0f);
        float scale = 1 - 0.1f * Math.abs(fraction);
        float alpha = 0.25f * Math.abs(fraction);
        item.setScaleX(scale);
        item.setScaleY(scale);
        item.findViewById(R.id.layoutItem).setAlpha(1 - alpha);
    }
}
