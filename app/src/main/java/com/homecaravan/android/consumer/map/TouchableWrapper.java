package com.homecaravan.android.consumer.map;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * The type Touchable wrapper.
 * @author Dau Hung
 */
public class TouchableWrapper extends FrameLayout {
    private String TAG = TouchableWrapper.this.getClass().getSimpleName();
    private boolean mTouchMap = true;
    private GestureDetector mGestureDetector;
    private EventOnMap mEventOnMap;


    /**
     * Instantiates a new Touchable wrapper.
     *
     * @param context the context
     */
    public TouchableWrapper(Context context) {
        super(context);
        mGestureDetector = new GestureDetector(context, new GestureListener());
    }

    /**
     * Sets disable touch.
     *
     * @param disableTouch the disable touch
     */
    public void setDisableTouch(boolean disableTouch) {
        this.mTouchMap = disableTouch;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (mTouchMap) {
            mGestureDetector.onTouchEvent(event);
            return super.dispatchTouchEvent(event);
        }
        return false;
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);

    }

    /**
     * The interface Event on map.
     */
    public interface EventOnMap {
        /**
         * On event map.
         *
         * @param event the event
         * @param e     the e
         */
        void onEventMap(String event, MotionEvent e);
    }

    /**
     * Sets event on map.
     *
     * @param eventOnMap the event on map
     */
    public void setEventOnMap(EventOnMap eventOnMap) {
        this.mEventOnMap = eventOnMap;
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.e("onSingleTapUp", "onSingleTapUp");
            return super.onSingleTapUp(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.e("onLongPress", "onLongPress");
            super.onLongPress(e);
        }


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            mEventOnMap.onEventMap("onFling",e1);
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.e("onShowPress", "onShowPress");
            mEventOnMap.onEventMap("onShowPress",e);
            super.onShowPress(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            mEventOnMap.onEventMap("onDown",e);
            return super.onDown(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.e("onSingleTapConfirmed", "onSingleTapConfirmed");
            mEventOnMap.onEventMap("onSingleTap",e);
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onContextClick(MotionEvent e) {
            return super.onContextClick(e);
        }


        @Override
        public boolean onDoubleTap(MotionEvent e) {
            mEventOnMap.onEventMap("onDoubleTap",e);
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            mEventOnMap.onEventMap("onScroll",e1);
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

    }
}