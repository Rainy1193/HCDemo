package com.homecaravan.android.consumer.activity;


import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.R;
import com.homecaravan.android.api.Constants;
import com.homecaravan.android.consumer.adapter.ViewPagerAdapter;
import com.homecaravan.android.consumer.base.BaseActivity;
import com.homecaravan.android.consumer.consumernotifications.INotificationsView;
import com.homecaravan.android.consumer.consumernotifications.NotificationsPresenter;
import com.homecaravan.android.consumer.message.FragmentMessageAll;
import com.homecaravan.android.consumer.message.FragmentMessageContactv2;
import com.homecaravan.android.consumer.model.message.ConsumerMessageAll;
import com.homecaravan.android.consumer.model.message.ConsumerMessages;
import com.homecaravan.android.consumer.model.message.MessageAddResponse;
import com.homecaravan.android.consumer.model.responseapi.ResponseNotification;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.socket.emitter.Emitter;


public class MessageActivity extends BaseActivity implements INotificationsView {

    private final String TAG = "DaoDiDem";

    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.vpMessage)
    ViewPager mViewPager;
    @Bind(R.id.tvNotificationCount)
    TextView mTvNotificationCount;

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

    private void socketListener() {
        Log.e(TAG, "socketListener: enable");

        HomeCaravanApplication.mSocket.on(Constants.getInstance().THREAD, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.e(TAG, "Socket thread: " + args[0].toString());
//                if (args[0] == null) {
//                    return;
//                }
//                final JSONObject data = (JSONObject) args[0];
//                String key, command;
//                try {
//                    key = data.getString(Constants.getInstance().MESSAGE_KEY);
//                    command = data.getString(Constants.getInstance().MESSAGE_COMMAND);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    return;
//                }
//                if (command.equals(Constants.getInstance().MESSAGE_COMMAND_ADD)
//                        && key.equals(Constants.getInstance().MESSAGE_MESSAGE)) {
//                    MessageAddResponse messageAddResponse = new Gson().fromJson(args[0].toString(), MessageAddResponse.class);
//                    if (messageAddResponse != null) {
//                        messageAddResponse.getMessageItem().setCommand(messageAddResponse.getCommand());
//                        ConsumerMessages consumerMessages = new ConsumerMessages();
//                        consumerMessages.setMessageItem(messageAddResponse.getMessageItem());
//
//                        if (mArrConsumerMessageAll.size() != 0) {
//                            for (ConsumerMessageAll cma : mArrConsumerMessageAll) {
//                                if (cma.getMessageThread().getId()
//                                        .equals(consumerMessages.getMessageItem().getMessageThread().getId())) {
//                                    ConsumerMessageAll consumerMessageAll = new ConsumerMessageAll();
//                                    if (cma.getMessageThread().getParticipants().size() > 2) {
//                                        consumerMessageAll.setType("group");
//                                    } else if (cma.getMessageThread().getParticipants().size() == 2) {
//                                        consumerMessageAll.setType("personal");
//                                    } else
//                                        return;
//                                    consumerMessageAll.setMessageThread(cma.getMessageThread());
//                                    mArrConsumerMessageAll.remove(cma);
//                                    mArrConsumerMessageAll.add(0, consumerMessageAll);
//                                    ArrayList<ConsumerMessageAll> arr = mArrConsumerMessageAll;
//                                    for (ConsumerMessageAll a : arr) {
//                                        Log.e(TAG, "call: " + a.getMessageThread().getId());
//                                    }
//                                    mMessageAllAdapter.notifyItemInserted(0);
////                                    saveNewThread(consumerMessageAll, false);
//                                    break;
//                                }
//                            }
//                        } else {
//                            ConsumerMessageAll consumerMessageAll = new ConsumerMessageAll();
//                            int sizeParticipants = consumerMessages.getMessageItem().getMessageThread().getParticipants().size();
//                            if (sizeParticipants > 2)
//                                consumerMessageAll.setType("group");
//                            else if (sizeParticipants == 2)
//                                consumerMessageAll.setType("personal");
//                            else
//                                return;
//                            consumerMessageAll.setMessageThread(consumerMessages.getMessageItem().getMessageThread());
//                            mArrConsumerMessageAll.add(consumerMessageAll);
//                            ArrayList<ConsumerMessageAll> arr = mArrConsumerMessageAll;
//                            int n = arr.size();
//                            mMessageAllAdapter.notifyItemInserted(0);
////                            saveNewThread(consumerMessageAll, false);
//                        }
//                    }
//                }

                // TODO: 12/1/2017 chưa làm thread update
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            //socketListener();
            Log.e("onStart", "onStart");
        } catch (Exception e) {
            Log.e("E", e.getMessage());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("onStop","onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("onPause","onPause");
        HomeCaravanApplication.mSocket.off(Constants.getInstance().THREAD);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume","onResume");
    }
}
