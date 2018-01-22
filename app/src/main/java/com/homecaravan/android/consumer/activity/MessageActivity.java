package com.homecaravan.android.consumer.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.adapter.ViewPagerAdapter;
import com.homecaravan.android.consumer.base.BaseActivity;
import com.homecaravan.android.consumer.consumernotifications.INotificationsView;
import com.homecaravan.android.consumer.consumernotifications.NotificationsPresenter;
import com.homecaravan.android.consumer.message.FragmentMessageAll;
import com.homecaravan.android.consumer.message.FragmentMessageContactv2;
import com.homecaravan.android.consumer.model.responseapi.ResponseNotification;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageActivity extends BaseActivity implements INotificationsView {

    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.vpMessage)
    ViewPager mViewPager;
    @Bind(R.id.tvNotificationCount)
    TextView mTvNotificationCount;
    public static final String TAG = "HC";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        FragmentMessageAll mFragmentMessageAll = new FragmentMessageAll();
//        FragmentMessageContact mFragmentMessageContact = new FragmentMessageContact();
        FragmentMessageContactv2 mFragmentMessageContact = new FragmentMessageContactv2();
        mViewPagerAdapter.addFragment(mFragmentMessageAll, "All");
        mViewPagerAdapter.addFragment(mFragmentMessageContact, "Contact");
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);

        NotificationsPresenter mNotificationsPresenter = new NotificationsPresenter(this);
        mNotificationsPresenter.getNotification("all", "1", "50", false);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_message;
    }

    @OnClick(R.id.ivBack)
    public void onBackClicked() {
        finish();
    }

    @OnClick(R.id.ivNotification)
    public void openNotification() {
        startActivity(new Intent(this, NotificationsActivity.class));
    }

    @Override
    public void getNotificationsSuccess(ResponseNotification.NotificationData data, String type, boolean isLoadMore) {
        int n = data.getNotifications().size();
        Log.e(TAG, "getNotificationsSuccess: " + data.getTotal() + " size: " + n);
        if (n != 0) {
            int notificationCount = 0;
            for (int i = 0; i < n; i++) {
                if ("no".equalsIgnoreCase(data.getNotifications().get(i).getSeenStatus())) {
                    notificationCount++;
                }
            }
            if (notificationCount != 0) {
                mTvNotificationCount.setVisibility(View.VISIBLE);
                mTvNotificationCount.setText(String.valueOf(notificationCount));
            } else {
                mTvNotificationCount.setVisibility(View.GONE);
            }
        } else {
            mTvNotificationCount.setVisibility(View.GONE);
        }
    }

    @Override
    public void getNotificationsFail(String message) {
        Log.e(TAG, "getNotificationsFail: " + message);
        mTvNotificationCount.setVisibility(View.GONE);
    }

    @Override
    public void getNotificationsFail(@StringRes int message) {
        Log.e(TAG, "getNotificationsFail: serverError");
        mTvNotificationCount.setVisibility(View.GONE);
    }
}
