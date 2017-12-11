package com.homecaravan.android.consumer.consumerintro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homecaravan.android.consumer.activity.MainActivityConsumer;
import com.homecaravan.android.consumer.listener.IIntroListener;
import com.homecaravan.android.consumer.utils.Convert;
import com.homecaravan.android.consumer.utils.ImageLoader;

import butterknife.ButterKnife;


public abstract class FragmentIntroBase extends Fragment {
    public ImageLoader mLoader;
    public float mHeightLayout;
    public float mBoundView;
    public float mStartHeight, mEndHeight;
    private IIntroListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResourceFragmentIntroId(), container, false);
        ButterKnife.bind(this, view);
        mHeightLayout = MainActivityConsumer.sHeightFragment / 4;
        mBoundView = Convert.dpToPx(12, getActivity());
        mEndHeight = mHeightLayout * 2 + mBoundView;
        mStartHeight = mHeightLayout;
        mLoader = new ImageLoader(getActivity());
        return view;
    }

    public abstract int getLayoutResourceFragmentIntroId();

    public abstract void resetFragment();

    public void setListener(IIntroListener mListener) {
        this.mListener = mListener;
    }

    public void closeChooseLayout() {
        if (mListener != null) {
            mListener.closeChooseTypeFromFragment();
        }
    }
}
