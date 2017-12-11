package com.homecaravan.android.consumer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.adapter.ViewPagerAdapter;
import com.homecaravan.android.consumer.base.BaseActivity;
import com.homecaravan.android.consumer.widget.CustomTabLayout;

import butterknife.Bind;
import butterknife.OnClick;


public class ScanCodeActivity extends BaseActivity {

    private ViewPagerAdapter mAdapter;
    public static int sHeight;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    @Bind(R.id.tabLayout)
    CustomTabLayout mTabLayout;

    @OnClick(R.id.ivBack)
    public void back() {
        onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        //Em dung 2 fragment cho unlock agent o ConsumerRegisterLoginActivity roi nha anh
//        mAdapter.addFragment(new FragmentQrCode(), "Scan Qr code");
//        mAdapter.addFragment(new FragmentEnterCode(), "Enter you code");
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                sHeight = mViewPager.getHeight();
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_scan_code;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_close_activity_left, R.anim.anim_close_activity_right);
    }


}
