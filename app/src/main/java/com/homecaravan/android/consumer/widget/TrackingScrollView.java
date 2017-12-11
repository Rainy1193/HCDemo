package com.homecaravan.android.consumer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class TrackingScrollView extends ScrollView {
	private boolean mScrollable = false;


	public interface OnScrollChangedListener {
		void onScrollChanged(TrackingScrollView source, int l, int t, int oldl, int oldt);
	}

	private OnScrollChangedListener mOnScrollChangedListener;

	public TrackingScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TrackingScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setOnScrollChangedListener(OnScrollChangedListener listener) {
		mOnScrollChangedListener = listener;
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);

		if (mOnScrollChangedListener != null) {
			mOnScrollChangedListener.onScrollChanged(this, l, t, oldl, oldt);
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