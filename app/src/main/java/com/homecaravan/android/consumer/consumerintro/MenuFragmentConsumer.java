package com.homecaravan.android.consumer.consumerintro;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.listener.MenuListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MenuFragmentConsumer extends Fragment {

    private MenuListener mListener;
    @Bind(R.id.scrollView)
    ScrollView mScrollView;
    @Bind(R.id.layoutMenu)
    LinearLayout mLayoutMenu;
    @Bind(R.id.tvNewMessageCount)
    TextView mTvNewMessageCount;
    public void setListener(MenuListener mListener) {
        this.mListener = mListener;
    }

    public void setNewMessageCount(int count){
        mTvNewMessageCount.setText(String.valueOf(count));
        if(count == 0){
            mTvNewMessageCount.setVisibility(View.GONE);
        }else{
            mTvNewMessageCount.setVisibility(View.VISIBLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.navigation_content_consumer, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.unbind(this);
    }


    @OnClick({R.id.menuDiscover, R.id.menuSchedule, R.id.menuShowing, R.id.menuSaveSearch, R.id.menuFavorite,
            R.id.menuTeam, R.id.menuMessage, R.id.menuSetting, R.id.menuMyAccount, R.id.menuLogout, R.id.menuProperty})
    public void onViewClicked(final View view) {
        view.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mListener.closeMenu();
                view.setEnabled(true);
            }
        }, 500);
        switch (view.getId()) {
            case R.id.menuDiscover:
                mListener.goDiscover();
                break;
            case R.id.menuSchedule:
                mListener.goSchedule();
                break;
            case R.id.menuShowing:
                mListener.goShowing();
                break;
            case R.id.menuSaveSearch:
                mListener.goSaveSearch();
                break;
            case R.id.menuFavorite:
                mListener.goContact();
                break;
            case R.id.menuTeam:
                mListener.goMyTeam();
                break;
            case R.id.menuMessage:
                mListener.goMessage();
                mTvNewMessageCount.setVisibility(View.GONE);
                mTvNewMessageCount.setText(String.valueOf(0));
                break;
            case R.id.menuSetting:
                mListener.goSettings();
                break;
            case R.id.menuMyAccount:
                mListener.goMyAccount();
                break;
            case R.id.menuProperty:
                mListener.goProperty();
                break;
            case R.id.menuLogout:
                mListener.logOut();
                break;
        }
    }
}
