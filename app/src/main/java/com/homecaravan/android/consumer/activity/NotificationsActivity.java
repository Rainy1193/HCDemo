package com.homecaravan.android.consumer.activity;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.adapter.NotificationsAdapter;
import com.homecaravan.android.consumer.base.BaseActivity;
import com.homecaravan.android.consumer.consumernotifications.INotificationsView;
import com.homecaravan.android.consumer.consumernotifications.NotificationsPresenter;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.model.responseapi.ResponseNotification;
import com.homecaravan.android.consumer.utils.AnimUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class NotificationsActivity extends BaseActivity implements INotificationsView {

    //notification
    private NotificationsPresenter mNotificationsPresenter;
    private ArrayList<ResponseNotification.Notifications> mArrNotifications = new ArrayList<>();
    private int mTotalNotifications = 0;
    private NotificationsAdapter mNotificationsAdapter;
    @Bind(R.id.layoutNotification)
    RelativeLayout mLayoutNotification;
    @Bind(R.id.rvNotifications)
    RecyclerView mRvNotifications;
    @Bind(R.id.layoutNotificationMenuContent)
    LinearLayout mLayoutNotificationMenuContent;
    @Bind(R.id.layoutNotificationEmpty)
    LinearLayout mLayoutNotificationEmpty;
    @Bind(R.id.layoutNotificationMenu)
    LinearLayout mLayoutNotificationMenu;
    @Bind(R.id.pbLoadNotification)
    ProgressBar mPbLoadNotification;
    @Bind(R.id.imgNotificationAll)
    ImageView mImgNotificationAll;
    @Bind(R.id.tvNotificationAll)
    TextView mTvNotificationAll;
    @Bind(R.id.imgNotificationShowings)
    ImageView mImgNotificationShowings;
    @Bind(R.id.tvNotificationShowings)
    TextView mTvNotificationShowings;
    @Bind(R.id.imgNotificationListings)
    ImageView mImgNotificationListings;
    @Bind(R.id.tvNotificationListings)
    TextView mTvNotificationListings;
    @Bind(R.id.imgNotificationContact)
    ImageView mImgNotificationContact;
    @Bind(R.id.tvNotificationContact)
    TextView mTvNotificationContact;
    @Bind(R.id.imgNotificationCaravan)
    ImageView mImgNotificationCaravan;
    @Bind(R.id.tvNotificationCaravan)
    TextView mTvNotificationCaravan;
    @Bind(R.id.imgNotificationSaveSearch)
    ImageView mImgNotificationSaveSearch;
    @Bind(R.id.tvNotificationSaveSearch)
    TextView mTvNotificationSaveSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupNotification();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_notifications;
    }

    private void setupNotification() {
        mNotificationsPresenter = new NotificationsPresenter(this);
        mRvNotifications.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mNotificationsAdapter = new NotificationsAdapter(mRvNotifications, this, mArrNotifications);
        mRvNotifications.setAdapter(mNotificationsAdapter);
        mNotificationsPresenter.getNotification("all", "1", "50", false);
        mNotificationsAdapter.resetPage();

        mNotificationsAdapter.setOnLoadMoreListener(new NotificationsAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore(String type, int page) {
                if(mTotalNotifications <= 50 * page){
//                    showSnackBar(mLayoutNotification, TypeDialog.MESSAGES, "You have no more notifications", "getNotificationsSuccess");
                }else{
                    mArrNotifications.add(null);
                    mNotificationsAdapter.notifyItemInserted(mArrNotifications.size() - 1);
                    mNotificationsPresenter.getNotification(type, page+"", "50", true);
                }
            }
        });
    }

    @OnClick(R.id.ivNotificationClose)
    public void onNotificationCloseClicked() {
        onBackPressed();
    }

    @OnClick({R.id.ivNotificationFilter, R.id.layoutNotificationAll,
            R.id.layoutNotificationShowings, R.id.layoutNotificationListings,
            R.id.layoutNotificationCaravan, R.id.layoutNotificationMenu,
            R.id.layoutNotificationSaveSearch, R.id.layoutNotificationContact})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivNotificationFilter:
                if (mLayoutNotificationMenu.getVisibility() == View.VISIBLE) {
                    AnimUtils.collapseView(mLayoutNotificationMenuContent, mLayoutNotificationMenu);
                } else {
                    mLayoutNotificationMenu.setVisibility(View.VISIBLE);
                    AnimUtils.expandView(mLayoutNotificationMenuContent);
                }
                break;
            case R.id.layoutNotificationAll:
                showLoadNotification();
                mNotificationsPresenter.getNotification("all", "1", "50", false);
                mNotificationsAdapter.resetPage();
                mNotificationsAdapter.setLoaded();
                closeNotificationMenu();
                changeColorFilter(mTvNotificationAll, mImgNotificationAll);
                break;
            case R.id.layoutNotificationShowings:
                showLoadNotification();
                mNotificationsPresenter.getNotification("appointment", "1", "50", false);
                mNotificationsAdapter.resetPage();
                mNotificationsAdapter.setLoaded();
                closeNotificationMenu();
                changeColorFilter(mTvNotificationShowings, mImgNotificationShowings);
                break;
            case R.id.layoutNotificationListings:
                showLoadNotification();
                mNotificationsPresenter.getNotification("listing", "1", "50", false);
                mNotificationsAdapter.resetPage();
                mNotificationsAdapter.setLoaded();
                closeNotificationMenu();
                changeColorFilter(mTvNotificationListings, mImgNotificationListings);
                break;
            case R.id.layoutNotificationContact:
                showLoadNotification();
                mNotificationsPresenter.getNotification("user", "1", "50", false);
                mNotificationsAdapter.resetPage();
                mNotificationsAdapter.setLoaded();
                closeNotificationMenu();
                changeColorFilter(mTvNotificationContact, mImgNotificationContact);
                break;
            case R.id.layoutNotificationCaravan:
                showLoadNotification();
                mNotificationsPresenter.getNotification("caravan", "1", "50", false);
                mNotificationsAdapter.resetPage();
                mNotificationsAdapter.setLoaded();
                closeNotificationMenu();
                changeColorFilter(mTvNotificationCaravan, mImgNotificationCaravan);
                break;
            case R.id.layoutNotificationSaveSearch:
                showLoadNotification();
                mNotificationsPresenter.getNotification("cbs", "1", "50", false);
                mNotificationsAdapter.resetPage();
                mNotificationsAdapter.setLoaded();
                closeNotificationMenu();
                changeColorFilter(mTvNotificationSaveSearch, mImgNotificationSaveSearch);
                break;
            case R.id.layoutNotificationMenu:
                closeNotificationMenu();
                break;
        }
    }

    private void changeColorFilter(TextView tv, ImageView img) {
        resetColorFilter();
        int fromColor = R.color.colorTextMain;
        int toColor = R.color.color_text_popup_menu_notifications;
        AnimUtils.changeColorFilter(this, fromColor, toColor, img);
        AnimUtils.changeTextColor(this, fromColor, toColor, tv);
    }

    private void resetColorFilter() {
        int fromColor = R.color.colorTextMain;
        int toColor = R.color.colorTextMain;
        AnimUtils.changeColorFilter(this, fromColor, toColor, mImgNotificationAll);
        AnimUtils.changeTextColor(this, fromColor, toColor, mTvNotificationAll);
        AnimUtils.changeColorFilter(this, fromColor, toColor, mImgNotificationShowings);
        AnimUtils.changeTextColor(this, fromColor, toColor, mTvNotificationShowings);
        AnimUtils.changeColorFilter(this, fromColor, toColor, mImgNotificationListings);
        AnimUtils.changeTextColor(this, fromColor, toColor, mTvNotificationListings);
        AnimUtils.changeColorFilter(this, fromColor, toColor, mImgNotificationContact);
        AnimUtils.changeTextColor(this, fromColor, toColor, mTvNotificationContact);
        AnimUtils.changeColorFilter(this, fromColor, toColor, mImgNotificationCaravan);
        AnimUtils.changeTextColor(this, fromColor, toColor, mTvNotificationCaravan);
        AnimUtils.changeColorFilter(this, fromColor, toColor, mImgNotificationSaveSearch);
        AnimUtils.changeTextColor(this, fromColor, toColor, mTvNotificationSaveSearch);
    }

    private void closeNotificationMenu() {
        if (mLayoutNotificationMenu.getVisibility() == View.VISIBLE) {
            AnimUtils.collapseView(mLayoutNotificationMenuContent, mLayoutNotificationMenu);
        }
    }

    public void resetNotificationMenu() {
        mLayoutNotificationMenu.setVisibility(View.GONE);
        mLayoutNotificationMenuContent.setVisibility(View.GONE);
    }

    private void showNotificationEmptyLayout() {
        mLayoutNotificationEmpty.setVisibility(View.VISIBLE);
        mRvNotifications.setVisibility(View.GONE);
        mPbLoadNotification.setVisibility(View.GONE);
    }

    private void showNotifications() {
        mRvNotifications.setVisibility(View.VISIBLE);
        mPbLoadNotification.setVisibility(View.GONE);
        mLayoutNotificationEmpty.setVisibility(View.GONE);
    }

    private void showLoadNotification() {
        mPbLoadNotification.setVisibility(View.VISIBLE);
        mRvNotifications.setVisibility(View.GONE);
        mLayoutNotificationEmpty.setVisibility(View.GONE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        resetNotificationMenu();
    }

    @Override
    public void getNotificationsSuccess(ResponseNotification.NotificationData data, String type, boolean isLoadMore) {
        int n = data.getNotifications().size();
        Log.e("DaoDiDem", "getNotificationsSuccess: " + data.getTotal() + " size: " + n);
        if(isLoadMore){
            mArrNotifications.remove(mArrNotifications.size() - 1);
            mNotificationsAdapter.notifyItemRemoved(mArrNotifications.size());
            if(n != 0){
                for (int i = 0; i < n; i++) {
                    data.getNotifications().get(i).setFilterType(type);
                    mArrNotifications.add(data.getNotifications().get(i));
                }
                mNotificationsAdapter.notifyDataSetChanged();
                mNotificationsAdapter.setLoaded();

                showNotifications();
            }else{
                showSnackBar(mLayoutNotification, TypeDialog.MESSAGES, "You have no more notifications", "getNotificationsSuccess");
            }
        }else{
            mArrNotifications.clear();
            mTotalNotifications = data.getTotal();
            if (n != 0) {
                int notificationCount = 0;
                for (int i = 0; i < n; i++) {
                    data.getNotifications().get(i).setFilterType(type);
                    mArrNotifications.add(data.getNotifications().get(i));
                    if ("all".equalsIgnoreCase(type)
                            && "no".equalsIgnoreCase(data.getNotifications().get(i).getSeenStatus())) {
                        notificationCount++;
                    }
                }
                mNotificationsAdapter.notifyDataSetChanged();
                showNotifications();
//                if (notificationCount != 0) {
//                    mTvNotificationCount.setVisibility(View.VISIBLE);
//                    mTvNotificationCount.setText(String.valueOf(notificationCount));
//                } else {
//                    mTvNotificationCount.setVisibility(View.GONE);
//                }
            } else {
                showNotificationEmptyLayout();
//                mTvNotificationCount.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void getNotificationsFail(String message) {
        Log.e("DaoDiDem", "getNotificationsFail: "+message);
        showSnackBar(mLayoutNotification, TypeDialog.WARNING, message, "getNotificationsFail");
    }

    @Override
    public void getNotificationsFail(@StringRes int message) {
        showSnackBar(mLayoutNotification, TypeDialog.ERROR, message, "getNotificationsFail");
    }
}
