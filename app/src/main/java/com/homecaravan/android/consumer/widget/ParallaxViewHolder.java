package com.homecaravan.android.consumer.widget;

import android.view.View;

import com.nineoldandroids.view.ViewHelper;

public class ParallaxViewHolder {
    View view;
    Float parallaxY;
    Float parallaxX;

    public ParallaxViewHolder(View view, Float parallaxY) {
        this.view = view;
        this.parallaxY = parallaxY;
    }

    public ParallaxViewHolder(ParallaxView parallaxView) {
        this.view = parallaxView;
        this.parallaxX = parallaxView.parallaxHorizontal;
        this.parallaxY = parallaxView.parallaxVertical;
    }

    public void onParallax(int offset) {
        if (parallaxY != null)
            ViewHelper.setTranslationY(view, offset * parallaxY);
        if (parallaxX != null)
            ViewHelper.setTranslationX(view, offset * parallaxX);
    }
}