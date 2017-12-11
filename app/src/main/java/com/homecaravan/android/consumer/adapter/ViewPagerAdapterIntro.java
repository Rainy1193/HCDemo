package com.homecaravan.android.consumer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.homecaravan.android.consumer.consumerintro.FragmentIntroBase;

import java.util.ArrayList;

public class ViewPagerAdapterIntro extends SmartFragmentStatePagerAdapter {
    /**
     * Instantiates a new Smart fragment state pager adapter.
     *
     * @param fragmentManager the fragment manager
     */

    private ArrayList<FragmentIntroBase> mArrFragment;

    public ViewPagerAdapterIntro(FragmentManager fragmentManager, ArrayList<FragmentIntroBase> fragments) {
        super(fragmentManager);
        this.mArrFragment = fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return mArrFragment.get(position);
    }

    @Override
    public int getCount() {
        return mArrFragment.size();
    }

    public void resetFragment() {
        for (int i = 0; i < mArrFragment.size(); i++) {
            mArrFragment.get(i).resetFragment();
        }
    }

}
