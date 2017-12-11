package com.homecaravan.android.consumer.widget;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;

public class CustomTabLayout extends TabLayout {

    /*
       * Variable to store invalid position.
       */
    private static final int INVALID_TAB_POS = -1;

    /*
    * Variable to store last selected position, init it with invalid tab position.
    */
    private int mLastSelectedTabPosition = INVALID_TAB_POS;

    public CustomTabLayout(Context context) {
        super(context);
    }

    public CustomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void removeAllTabs() {
        // Retain last selected position before removing all tabs
        mLastSelectedTabPosition = getSelectedTabPosition();
        super.removeAllTabs();
    }

    @Override
    public int getSelectedTabPosition() {
        // Override selected tab position to return your last selected tab position
        final int selectedTabPositionAtParent = super.getSelectedTabPosition();
        return selectedTabPositionAtParent == INVALID_TAB_POS ?
                mLastSelectedTabPosition : selectedTabPositionAtParent;
    }

    public void notifyDataSetChanged() {
        post(new Runnable() {
            @Override
            public void run() {
                TabLayout.Tab selectedTab = getTabAt(getSelectedTabPosition());
                if (selectedTab != null) {
                    selectedTab.select();
                }
            }
        });
    }
}