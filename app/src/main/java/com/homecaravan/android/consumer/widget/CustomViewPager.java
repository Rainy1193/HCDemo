package com.homecaravan.android.consumer.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * The type Custom view pager.
 */
public class CustomViewPager extends ViewPager {
    private boolean isSwipeEnabled = false;

    /**
     * Instantiates a new Custom view pager.
     *
     * @param context the context
     */
    public CustomViewPager(Context context) {
        super(context);
    }

    /**
     * Instantiates a new Custom view pager.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMyScroller();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return isSwipeEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return isSwipeEnabled && super.onInterceptTouchEvent(event);
    }

    /**
     * Sets swipe enabled.
     *
     * @param b the b
     */
    public void setSwipeEnabled(boolean b) {
        this.isSwipeEnabled = b;
    }

    private void setMyScroller() {
        try {
            Class<?> viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            scroller.set(this, new MyScroller(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The type My scroller.
     */
    public class MyScroller extends Scroller {
        /**
         * Instantiates a new My scroller.
         *
         * @param context the context
         */
        public MyScroller(Context context) {
            super(context, new DecelerateInterpolator());
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, 500 /*1 secs*/);
        }
    }

}