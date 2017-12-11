package com.homecaravan.android.consumer.consumerteam;


import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.adapter.ViewPagerAdapter;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.listener.IPageChangeMyTeam;
import com.homecaravan.android.consumer.listener.ITeamListener;
import com.homecaravan.android.consumer.listener.TeamMainListener;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.homecaravan.android.consumer.widget.CustomViewPager;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.Bind;
import butterknife.OnClick;

public class FragmentTeam extends BaseFragment implements IPageChangeMyTeam, ITeamListener, TeamMainListener {

    private ViewPagerAdapter mViewPagerAdapter;
    private FragmentTeamChild mFragmentTeamChild;
    private FragmentHistory mFragmentHistory;
    private int mActiveFragment;
    private boolean mInitData;
    @Bind(R.id.vpMyTeam)
    CustomViewPager mViewPager;

    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;

    @Bind(R.id.layoutTeam)
    RelativeLayout mLayoutTeam;

    @Bind(R.id.frmActionSort)
    RelativeLayout mFrmActionSort;

    @Bind(R.id.layoutMenuContent)
    LinearLayout mLayoutMenuContent;

    @Bind(R.id.layoutMenu)
    LinearLayout mLayoutMenu;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        mFragmentTeamChild = new FragmentTeamChild();
        mFragmentHistory = new FragmentHistory();
        mViewPagerAdapter.addFragment(mFragmentTeamChild, "My Team");
        mViewPagerAdapter.addFragment(mFragmentHistory, "History");
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);
        mFragmentTeamChild.setPageChange(this);
        mFragmentTeamChild.setTeamMainListener(this);
        mFragmentTeamChild.setHistoryListener(this);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mFragmentHistory.setFullHistory();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    @OnClick(R.id.frmActionSort)
    public void showSortMenu() {

        if (mLayoutMenu.getVisibility() == View.VISIBLE) {
            AnimUtils.collapseView(mLayoutMenuContent, mLayoutMenu);

        } else {
            mLayoutMenu.setVisibility(View.VISIBLE);
            AnimUtils.expandView(mLayoutMenuContent);
        }
    }

    @OnClick(R.id.layoutMenu)
    public void onLayoutMenuClicked() {
        closeMenu();
    }

    @OnClick({R.id.layoutNewestToOldest, R.id.layoutOldestToNewest, R.id.layoutByCaravan, R.id.layoutByAppointments, R.id.layoutSomething})
    public void onViewClicked(View view) {

        mActiveFragment = mViewPager.getCurrentItem();

        if (mActiveFragment == 0) {
            switch (view.getId()) {
                case R.id.layoutNewestToOldest:
                    showPopupConfirm();
                    closeMenu();
                    break;
                case R.id.layoutOldestToNewest:
                    showPopupNotification();
                    closeMenu();
                    break;
                case R.id.layoutByCaravan:
                    showPopupSuccess();
                    closeMenu();
                    break;
                case R.id.layoutByAppointments:
                    showPopupError();
                    closeMenu();
                    break;
                case R.id.layoutSomething:
                    showPopupWarning();
                    closeMenu();
                    break;
            }
        }

        if (mActiveFragment == 1) {
            switch (view.getId()) {
                case R.id.layoutNewestToOldest:
                    ciaAppointmentUpdates();
                    closeMenu();
                    break;
                case R.id.layoutOldestToNewest:
                    ciaShowPopupCaravan();
                    closeMenu();
                    break;
                case R.id.layoutByCaravan:
                    ciaAppointmentCommencement();
                    closeMenu();
                    break;
                case R.id.layoutByAppointments:
                    ciaAppointmentWarning();
                    closeMenu();
                    break;
                case R.id.layoutSomething:
                    closeMenu();
                    break;
            }
        }

    }

    public void initData() {
        if (!mInitData) {
            mViewPager.setAdapter(mViewPagerAdapter);
            mInitData = true;
        }
    }

    public void closeMenu() {
        if (mLayoutMenu.getVisibility() == View.VISIBLE) {
            AnimUtils.collapseView(mLayoutMenuContent, mLayoutMenu);
        }
    }

    public void resetFragment() {
        mLayoutMenu.setVisibility(View.GONE);
        mLayoutMenuContent.setVisibility(View.GONE);
    }

    private void showPopupConfirm() {
        LayoutInflater layoutInflater1 = LayoutInflater.from(getActivity());
        View view1 = layoutInflater1.inflate(R.layout.dialog_consumer_popup_confirm, null);
        FrameLayout frmButtonNo = (FrameLayout) view1.findViewById(R.id.frmButtonNo);
        FrameLayout frmButtonYes = (FrameLayout) view1.findViewById(R.id.frmButtonYes);

        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setView(view1).create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.TeamTabSearchDialog;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
        wmlp.y = -200;   //y position

        frmButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        frmButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void showPopupNotification() {
        LayoutInflater layoutInflater1 = LayoutInflater.from(getActivity());
        View view1 = layoutInflater1.inflate(R.layout.dialog_consumer_popup_notification, null);
        FrameLayout frmButtonNo = (FrameLayout) view1.findViewById(R.id.frmButtonNo);
        FrameLayout frmButtonYes = (FrameLayout) view1.findViewById(R.id.frmButtonYes);

        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setView(view1).create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.TeamTabSearchDialog;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
        wmlp.y = -200;   //y position

        frmButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        frmButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void showPopupSuccess() {
        LayoutInflater layoutInflater1 = LayoutInflater.from(getActivity());
        View view1 = layoutInflater1.inflate(R.layout.dialog_consumer_popup_success, null);
        FrameLayout frmButtonOk = (FrameLayout) view1.findViewById(R.id.frmButtonOk);

        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setView(view1).create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.TeamTabSearchDialog;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
        wmlp.y = -200;   //y position

        frmButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void showPopupError() {
        LayoutInflater layoutInflater1 = LayoutInflater.from(getActivity());
        View view1 = layoutInflater1.inflate(R.layout.dialog_consumer_popup_error, null);
        FrameLayout frmButtonOk = (FrameLayout) view1.findViewById(R.id.frmButtonOk);

        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setView(view1).create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.TeamTabSearchDialog;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
        wmlp.y = -200;   //y position

        frmButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void showPopupWarning() {
        LayoutInflater layoutInflater1 = LayoutInflater.from(getActivity());
        View view1 = layoutInflater1.inflate(R.layout.dialog_consumer_popup_warning, null);
        FrameLayout frmButtonOk = (FrameLayout) view1.findViewById(R.id.frmButtonOk);

        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setView(view1).create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.TeamTabSearchDialog;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
        wmlp.y = -200;   //y position

        frmButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void ciaAppointmentUpdates(){
        LayoutInflater layoutInflater1 = LayoutInflater.from(getActivity());
        View view1 = layoutInflater1.inflate(R.layout.dialog_consumer_caravan_popup_appointment_updates, null);
        FrameLayout frmButton1 = (FrameLayout) view1.findViewById(R.id.frmButton1);
        FrameLayout frmButton2 = (FrameLayout) view1.findViewById(R.id.frmButton2);
        FrameLayout frmButton3 = (FrameLayout) view1.findViewById(R.id.frmButton3);
        TextView tvPopupMessage = (TextView) view1.findViewById(R.id.tvPopupMessage);
        tvPopupMessage.setText(fromHtml(getActivity().getString(R.string.caravan_popup_appointment_updates)));

        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setView(view1).create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.TeamTabSearchDialog;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
        wmlp.y = -200;   //y position

        frmButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void ciaShowPopupCaravan(){
        LayoutInflater layoutInflater1 = LayoutInflater.from(getActivity());
        View view1 = layoutInflater1.inflate(R.layout.dialog_consumer_caravan_popup_show_popup_caravan, null);
        FrameLayout rlButton1 = (FrameLayout) view1.findViewById(R.id.rlButton1);
        TextView tvPopupMessage = (TextView) view1.findViewById(R.id.tvPopupMessage);
        tvPopupMessage.setText(fromHtml(getActivity().getString(R.string.caravan_popup_show_popup_caravan)));

        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setView(view1).create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.TeamTabSearchDialog;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
        wmlp.y = -200;   //y position

        rlButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void ciaAppointmentCommencement(){
        LayoutInflater layoutInflater1 = LayoutInflater.from(getActivity());
        View view1 = layoutInflater1.inflate(R.layout.dialog_consumer_caravan_popup_appointment_commencement, null);
        FrameLayout frmButton1 = (FrameLayout) view1.findViewById(R.id.frmButton1);
        FrameLayout frmButton2 = (FrameLayout) view1.findViewById(R.id.frmButton2);
        TextView tvPopupMessage = (TextView) view1.findViewById(R.id.tvPopupMessage);
        RoundedImageView imgAppointmentHome = (RoundedImageView) view1.findViewById(R.id.imgAppointmentHome);
        Glide.with(getContext()).load(R.drawable.house_demo_1).asBitmap().fitCenter()
                .dontAnimate()
                .into(imgAppointmentHome);
        tvPopupMessage.setText(fromHtml(getActivity().getString(R.string.caravan_popup_appointment_commencement)));

        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setView(view1).create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.TeamTabSearchDialog;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
        wmlp.y = -200;   //y position

        frmButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void ciaAppointmentWarning(){
        LayoutInflater layoutInflater1 = LayoutInflater.from(getActivity());
        View view1 = layoutInflater1.inflate(R.layout.dialog_consumer_caravan_popup_appointment_warning, null);
        FrameLayout frmButton1 = (FrameLayout) view1.findViewById(R.id.frmButton1);
        FrameLayout frmButton2 = (FrameLayout) view1.findViewById(R.id.frmButton2);
        TextView tvPopupMessage = (TextView) view1.findViewById(R.id.tvPopupMessage);
        RoundedImageView imgAppointmentHome = (RoundedImageView) view1.findViewById(R.id.imgAppointmentHome);
        Glide.with(getContext()).load(R.drawable.house_demo_1).asBitmap().fitCenter()
                .dontAnimate()
                .into(imgAppointmentHome);
        tvPopupMessage.setText(fromHtml(getActivity().getString(R.string.caravan_popup_appointment_warning)));

        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setView(view1).create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.TeamTabSearchDialog;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
        wmlp.y = -200;   //y position

        frmButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_consumer_team;
    }

    @Override
    public void openMyTeam() {
        mViewPager.setCurrentItem(0, true);
    }

    @Override
    public void openHistory() {
        mViewPager.setCurrentItem(1, true);
    }

    @Override
    public void filterHistory(String id) {
        mFragmentHistory.filter(id);
    }

    @Override
    public void showLayout() {
        mLayoutTeam.setVisibility(View.VISIBLE);
    }
}
