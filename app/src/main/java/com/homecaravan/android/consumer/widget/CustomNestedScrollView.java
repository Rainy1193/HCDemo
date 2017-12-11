package com.homecaravan.android.consumer.widget;


import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * The type Custom nested scroll view.
 */
public class CustomNestedScrollView extends NestedScrollView {
    private boolean mScrollable = true;
    private ScrollViewListener scrollViewListener = null;

    /**
     * Instantiates a new Custom nested scroll view.
     *
     * @param context the context
     */
    public CustomNestedScrollView(Context context) {
        super(context);
    }

    /**
     * Instantiates a new Custom nested scroll view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public CustomNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    /**
     * Sets scrolling enabled.
     *
     * @param enabled the enabled
     */
    public void setScrollingEnabled(boolean enabled) {
        mScrollable = enabled;
    }

    /**
     * Is scrollable boolean.
     *
     * @return the boolean
     */
    public boolean isScrollable() {
        return mScrollable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // if we can scroll pass the event to the superclass
                if (mScrollable) return super.onTouchEvent(ev);
                // only continue to handle the touch event if scrolling enabled
                return mScrollable; // mScrollable is always false at this point
            default:
                return super.onTouchEvent(ev);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // Don't do anything with intercepted touch events if
        // we are not scrollable
        if (!mScrollable) return false;
        else return super.onInterceptTouchEvent(ev);
    }


    /**
     * The interface Scroll view listener.
     */
    public interface ScrollViewListener {
        /**
         * On scroll changed.
         *
         * @param scrollView the scroll view
         * @param x          the x
         * @param y          the y
         * @param oldx       the oldx
         * @param oldy       the oldy
         */
        void onScrollChanged(CustomNestedScrollView scrollView,
                             int x, int y, int oldx, int oldy);
    }

    /**
     * Sets scroll view listener.
     *
     * @param scrollViewListener the scroll view listener
     */
    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

    /**
     * Can scroll boolean.
     *
     * @return the boolean
     */
    public boolean canScroll() {
        View child = getChildAt(0);
        if (child != null) {
            int childHeight = child.getHeight();
            return getHeight() < childHeight + getPaddingTop() + getPaddingBottom();
        }
        return false;
    }

}