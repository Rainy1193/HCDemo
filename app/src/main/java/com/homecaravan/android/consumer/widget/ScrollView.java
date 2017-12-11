package com.homecaravan.android.consumer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import java.util.ArrayList;
import java.util.List;


public class ScrollView extends ObservableScrollView {

    private boolean mScrollable = false;


    public interface OnScrollChangedListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    private OnScrollChangedListener mOnScrollChangedListener;

    List<ParallaxViewHolder> viewsToMove = new ArrayList<>();
    ObservableScrollViewCallbacks callbacks;

    public ScrollView(Context context) {
        super(context);
    }

    public ScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void findParallaxViews(View view) {

        //view surrounded by ParallaxView
        if (view instanceof ParallaxView)
            viewsToMove.add(new ParallaxViewHolder(ParallaxView.class.cast(view)));

        //view contains android:tag="parallax=0.5"
        else if(view.getTag() != null && view.getTag().toString() != null && !view.getTag().toString().trim().isEmpty()){
            String[] subTags = view.getTag().toString().trim().split(";");
            for(String tag : subTags) {
                if (tag.contains("parallax=")) {
                    String floatString = tag.substring(tag.indexOf("=") + 1);
                    try {
                        Float value = Float.parseFloat(floatString);
                        viewsToMove.add(new ParallaxViewHolder(view, value));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)view;
            for (int i = 0; i < viewGroup.getChildCount(); ++i) {
                findParallaxViews(viewGroup.getChildAt(i));
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        findParallaxViews(getChildAt(0));

        super.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
            @Override
            public void onScrollChanged(int offset, boolean b, boolean b1) {
                if (callbacks != null)
                    callbacks.onScrollChanged(offset, b, b1);

                int count = viewsToMove.size();
                for (int i = 0; i < count; ++i)
                    viewsToMove.get(i).onParallax(offset);

            }

            @Override
            public void onDownMotionEvent() {
                if (callbacks != null) callbacks.onDownMotionEvent();
            }

            @Override
            public void onUpOrCancelMotionEvent(ScrollState scrollState) {
                if (callbacks != null) callbacks.onUpOrCancelMotionEvent(scrollState);
            }
        });
    }

    @Override
    public void setScrollViewCallbacks(ObservableScrollViewCallbacks listener) {
        this.callbacks = listener;
    }

    public void setOnScrollChangedListener(OnScrollChangedListener listener) {
        mOnScrollChangedListener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener.onScrollChanged(l, t, oldl, oldt);
        }
    }

    public void setScrollingEnabled(boolean enabled) {
        mScrollable = enabled;
    }

    public boolean isScrollable() {
        return mScrollable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mScrollable) return super.onTouchEvent(ev);
                return mScrollable;
            default:
                return super.onTouchEvent(ev);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mScrollable && super.onInterceptTouchEvent(ev);
    }

}
