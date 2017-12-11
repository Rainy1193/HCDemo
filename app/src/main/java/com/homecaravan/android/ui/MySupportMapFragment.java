package com.homecaravan.android.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by RAINY on 5/8/2016.
 */
public class MySupportMapFragment extends SupportMapFragment {
    public View mOriginalContentView;
    public MapWrapperLayout mMapWrapperLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        mOriginalContentView = super.onCreateView(inflater, parent, savedInstanceState);
        mMapWrapperLayout = new MapWrapperLayout(getActivity());
        mMapWrapperLayout.addView(mOriginalContentView);
        return mMapWrapperLayout;
    }

    @Override
    public View getView() {
        return mOriginalContentView;
    }

    public void setOnDragListener(MapWrapperLayout.OnDragListener onDragListener) {
        mMapWrapperLayout.setOnDragListener(onDragListener);
    }
}