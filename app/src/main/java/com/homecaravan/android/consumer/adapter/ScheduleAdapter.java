package com.homecaravan.android.consumer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;

public class ScheduleAdapter extends SmartFragmentStatePagerAdapter {
    /**
     * Instantiates a new Smart fragment state pager adapter.
     *
     * @param fragmentManager the fragment manager
     */
    private ArrayList<Fragment> mArrFragment = new ArrayList<>();

    public ScheduleAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return mArrFragment.get(position);
    }

    @Override
    public int getCount() {
        return mArrFragment.size();
    }

    public void addFragment(Fragment fragment) {
        mArrFragment.add(fragment);
    }

    public Fragment getFragment(int position) {
        return mArrFragment.get(position);
    }
}
