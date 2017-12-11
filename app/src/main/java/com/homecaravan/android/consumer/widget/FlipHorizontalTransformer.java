package com.homecaravan.android.consumer.widget;


import android.view.View;

public class FlipHorizontalTransformer extends ABaseTransformer {

    @Override
    protected void onTransform(View view, float position) {
        final float rotation = 180f * position;

        view.setVisibility(rotation > 90f || rotation < -90f ? View.INVISIBLE : View.VISIBLE);
        view.setPivotX(view.getWidth() * 0.5f);
        view.setPivotY(view.getHeight() * 0.5f);
        view.setRotationY(rotation);

    }
}
