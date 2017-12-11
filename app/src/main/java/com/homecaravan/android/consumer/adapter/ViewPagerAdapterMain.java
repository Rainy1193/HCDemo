package com.homecaravan.android.consumer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;


public class ViewPagerAdapterMain extends SmartFragmentStatePagerAdapter {
    /**
     * Instantiates a new Smart fragment state pager adapter.
     *
     * @param fragmentManager the fragment manager
     */

    private ArrayList<Fragment> mArrFragment;

    public ViewPagerAdapterMain(FragmentManager fragmentManager, ArrayList<Fragment> fragments) {
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
}
