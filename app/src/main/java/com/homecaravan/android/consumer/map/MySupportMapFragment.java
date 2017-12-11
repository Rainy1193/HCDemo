package com.homecaravan.android.consumer.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;

/**
 * The type My support map fragment.
 * @author Dau Hung
 */
public class MySupportMapFragment extends SupportMapFragment {
    /**
     * The M original content view.
     */
    public View mOriginalContentView;
    /**
     * The M touch view.
     */
    public TouchableWrapper mTouchView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mOriginalContentView = super.onCreateView(inflater, parent, savedInstanceState);
        mTouchView = new TouchableWrapper(getActivity());
        mTouchView.addView(mOriginalContentView);
        return mTouchView;
    }

    @Override
    public View getView() {
        return mOriginalContentView;
    }

    /**
     * Sets on event map.
     *
     * @param onEventMap the on event map
     */
    public void setOnEventMap(TouchableWrapper.EventOnMap onEventMap) {
        mTouchView.setEventOnMap(onEventMap);
    }

}
