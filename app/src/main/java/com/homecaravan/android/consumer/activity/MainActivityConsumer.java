package com.homecaravan.android.consumer.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.R;
import com.homecaravan.android.api.Constants;
import com.homecaravan.android.consumer.adapter.SearchResultAdapter;
import com.homecaravan.android.consumer.adapter.ViewPagerAdapterIntro;
import com.homecaravan.android.consumer.adapter.ViewPagerAdapterMain;
import com.homecaravan.android.consumer.consumerbase.ConsumerUser;
import com.homecaravan.android.consumer.consumerdashboard.FragmentDashboardMain;
import com.homecaravan.android.consumer.consumerdashboard.seachplacemvp.SearchPlacePresenter;
import com.homecaravan.android.consumer.consumerdashboard.seachplacemvp.SearchPlaceView;
import com.homecaravan.android.consumer.consumerdata.ConsumerDummyData;
import com.homecaravan.android.consumer.consumerdiscover.FragmentDiscover;
import com.homecaravan.android.consumer.consumerdiscover.FragmentSavedSearchDetail;
import com.homecaravan.android.consumer.consumerintro.FragmentBuyIntro;
import com.homecaravan.android.consumer.consumerintro.FragmentIntroBase;
import com.homecaravan.android.consumer.consumerintro.FragmentRentIntro;
import com.homecaravan.android.consumer.consumerintro.FragmentSellIntro;
import com.homecaravan.android.consumer.consumerintro.MenuFragmentConsumer;
import com.homecaravan.android.consumer.consumermvp.listingmvp.AddFavoritePresenter;
import com.homecaravan.android.consumer.consumermvp.listingmvp.AddFavoriteView;
import com.homecaravan.android.consumer.consumermvp.queuemvp.AddQueuePresenter;
import com.homecaravan.android.consumer.consumermvp.queuemvp.AddQueueView;
import com.homecaravan.android.consumer.consumermvp.queuemvp.GetQueuePresenter;
import com.homecaravan.android.consumer.consumermvp.queuemvp.GetQueueView;
import com.homecaravan.android.consumer.consumermvp.queuemvp.RemoveQueuePresenter;
import com.homecaravan.android.consumer.consumermvp.queuemvp.RemoveQueueView;
import com.homecaravan.android.consumer.consumernotifications.INotificationsView;
import com.homecaravan.android.consumer.consumernotifications.NotificationsPresenter;
import com.homecaravan.android.consumer.consumershedule.FragmentSchedule;
import com.homecaravan.android.consumer.consumershowing.FragmentShowing;
import com.homecaravan.android.consumer.consumerteam.FragmentTeam;
import com.homecaravan.android.consumer.fragment.FragmentAgentInformation;
import com.homecaravan.android.consumer.fragment.FragmentListingDetail;
import com.homecaravan.android.consumer.fragment.FragmentSubmitReviews;
import com.homecaravan.android.consumer.listener.IDialogListener;
import com.homecaravan.android.consumer.listener.IIntroListener;
import com.homecaravan.android.consumer.listener.IMainListener;
import com.homecaravan.android.consumer.listener.IMenuListener;
import com.homecaravan.android.consumer.listener.IPageChange;
import com.homecaravan.android.consumer.listener.ISearchListener;
import com.homecaravan.android.consumer.listener.MenuListener;
import com.homecaravan.android.consumer.message.messageloginmvp.ILoginView;
import com.homecaravan.android.consumer.message.messageloginmvp.LoginPresenter;
import com.homecaravan.android.consumer.model.CaravanQueue;
import com.homecaravan.android.consumer.model.ConsumerListingSchedule;
import com.homecaravan.android.consumer.model.CurrentFragment;
import com.homecaravan.android.consumer.model.CurrentListingSchedule;
import com.homecaravan.android.consumer.model.EventAgentDetail;
import com.homecaravan.android.consumer.model.EventCIA;
import com.homecaravan.android.consumer.model.EventDialog;
import com.homecaravan.android.consumer.model.EventFavorite;
import com.homecaravan.android.consumer.model.EventFavored;
import com.homecaravan.android.consumer.model.EventListingDetail;
import com.homecaravan.android.consumer.model.EventQueue;
import com.homecaravan.android.consumer.model.EventQueueRequest;
import com.homecaravan.android.consumer.model.EventQueueResponse;
import com.homecaravan.android.consumer.model.EventReloadSaveSearch;
import com.homecaravan.android.consumer.model.EventRequestShowing;
import com.homecaravan.android.consumer.model.EventReviewSubmit;
import com.homecaravan.android.consumer.model.Predictions;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.model.message.MessageAddResponse;
import com.homecaravan.android.consumer.model.message.SkeletonNewMessage;
import com.homecaravan.android.consumer.model.responseapi.Listing;
import com.homecaravan.android.consumer.model.responseapi.ResponseNotification;
import com.homecaravan.android.consumer.model.responseapi.ResponseQueue;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.homecaravan.android.consumer.utils.Convert;
import com.homecaravan.android.consumer.utils.TypeConsumer;
import com.homecaravan.android.consumer.utils.Utils;
import com.homecaravan.android.consumer.widget.ConsumerDialog;
import com.homecaravan.android.consumer.widget.FlipHorizontalTransformer;
import com.homecaravan.android.models.CurrentSaveSearch;
import com.homecaravan.android.ui.lib.SlidingMenu;
import com.homecaravan.android.ui.lib.app.SlidingFragmentActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import butterknife.OnTouch;
import io.realm.Realm;
import io.socket.emitter.Emitter;


public class MainActivityConsumer extends SlidingFragmentActivity implements
        IMenuListener,
        IIntroListener,
        IPageChange,
        ISearchListener,
        SearchPlaceView,
        IMainListener,
        MenuListener,
        IDialogListener,
        AddQueueView,
        GetQueueView,
        RemoveQueueView,
        INotificationsView,
        AddFavoriteView,
        ILoginView {

    private final String TAG = "DaoDiDem";
    private ConsumerDialog mConsumerDialog;
    private SlidingMenu mSlidingMenu;
    private FragmentBuyIntro mFragmentBuy;
    private FragmentRentIntro mFragmentRent;
    private FragmentSellIntro mFragmentSell;
    private float mXBuy, mXRent, mXSell;
    private float mXCenterBuy, mXCenterRent, mXCenterSell;
    private float mXCenterDashboard, mXCenterDiscover, mXCenterSchedule, mXCenterShowing, mXCenterMyTeam;
    private FragmentManager mManager;
    private FragmentTransaction mTransaction;
    public DisplayMetrics mDisplayMetrics = new DisplayMetrics();
    public static float sHeightFragment;
    public boolean sOpenLayoutChange;
    private boolean mShowMenu;
    private int mTypeBefore = -1;
    private int mTypeAfter = 1;
    public static int mIndexPage = 0;
    private int mCurrentFragment = 0;
    private int mOldFragment = 0;
    private FragmentDiscover mFragmentDiscover = new FragmentDiscover();
    private FragmentDashboardMain mFragmentDashboard = new FragmentDashboardMain();
    private FragmentSchedule mFragmentSchedule = new FragmentSchedule();
    private FragmentTeam mFragmentTeam = new FragmentTeam();
    private FragmentShowing mFragmentShowing = new FragmentShowing();
    private ViewPagerAdapterIntro mAdapterIntro;
    private ViewPagerAdapterMain mAdapterMain;
    private ArrayList<FragmentIntroBase> mArrFragmentIntro = new ArrayList<>();
    private ArrayList<Fragment> mArrFragmentMain = new ArrayList<>();
    private SearchResultAdapter mSearchAdapter;
    private ArrayList<Predictions.Place> mArrPlace = new ArrayList<>();
    private SearchPlacePresenter mPlacePresenter;
    private AddQueuePresenter mAddQueuePresenter;
    private AddFavoritePresenter mAddFavoritePresenter;
    private GetQueuePresenter mGetQueuePresenter;
    private RemoveQueuePresenter mRemoveQueuePresenter;
    public static DrawerArrowDrawable mDrawerArrowDrawable;
    private FragmentListingDetail mFragmentListingDetail;
    private FragmentAgentInformation mAgentInformation;
    private FragmentSubmitReviews mSubmitReviews;
    private FragmentSavedSearchDetail mFragmentSavedSearchDetail;
    private boolean mSavedSearchDetail;
    public static boolean mIsCaravanInAction;
    private MenuFragmentConsumer mMenuFragmentConsumer;
    private SharedPreferences mPrefs;
    private String mFavoriteId;
    private String mQueueId;
    private ViewPager.OnPageChangeListener myOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        boolean first = true;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (first && positionOffset == 0 && positionOffsetPixels == 0) {
                onPageSelected(0);
                first = false;
            }
        }

        @Override
        public void onPageSelected(int position) {
            mOldFragment = mCurrentFragment;
            mCurrentFragment = position;
            resetFragmentApterPageChange(mOldFragment);
            if (position == 0) {
                initDataFragment(0);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == 0) {
                initDataFragment(mCurrentFragment);
            }
        }
    };

    @Bind(R.id.ivDashboard)
    ImageView mIvDashboard;
    @Bind(R.id.tvDashboard)
    TextView mTvDashboard;
    @Bind(R.id.ivDiscover)
    ImageView mIvDiscover;
    @Bind(R.id.tvDiscover)
    TextView mTvDiscover;
    @Bind(R.id.ivSchedule)
    ImageView mIvSchedule;
    @Bind(R.id.tvSchedule)
    TextView mTvSchedule;
    @Bind(R.id.ivShowing)
    ImageView mIvShowing;
    @Bind(R.id.tvShowing)
    TextView mTvShowing;
    @Bind(R.id.ivMyTeam)
    ImageView mIvMyTeam;
    @Bind(R.id.tvMyTeam)
    TextView mTvMyTeam;
    @Bind(R.id.layoutMove)
    LinearLayout mLayoutMove;
    @Bind(R.id.layoutChangeType)
    RelativeLayout mLayoutChangeType;
    @Bind(R.id.viewMove)
    View mViewMove;
    @Bind(R.id.layoutChooseType)
    FrameLayout mLayoutChooseType;
    @Bind(R.id.layoutBar)
    LinearLayout mLayoutBar;
    @Bind(R.id.layoutBarBottom)
    LinearLayout mLayoutBottom;
    @Bind(R.id.ivMove)
    ImageView mIvMove;
    @Bind(R.id.tvType)
    TextView mTvType;
    @Bind(R.id.tvBuy)
    TextView mBuy;

    @Bind(R.id.tvRent)
    TextView mRent;
    @Bind(R.id.tvSell)
    TextView mSell;
    @Bind(R.id.layoutDiscover)
    RelativeLayout mDiscover;
    @Bind(R.id.layoutDashboard)
    RelativeLayout mDashboard;
    @Bind(R.id.layoutSchedule)
    RelativeLayout mSchedule;
    @Bind(R.id.layoutShowing)
    RelativeLayout mShowing;
    @Bind(R.id.layoutMyTeam)
    RelativeLayout mMyTeam;
    @Bind(R.id.viewCurrentPage)
    View mCurrentPage;
    @Bind(R.id.tvScheduleCount)
    TextView mTvScheduleCount;
    @Bind(R.id.viewpagerIntro)
    ViewPager mViewPagerIntro;
    @Bind(R.id.viewpagerMain)
    ViewPager mViewPagerMain;
    @Bind(R.id.ivCloseSearch)
    ImageView mIvCloseSearch;
    @Bind(R.id.ivSearch)
    ImageView mIvSearch;
    @Bind(R.id.etSearch)
    EditText mEtSearch;
    @Bind(R.id.ivFilter)
    ImageView mIvFilter;
    @Bind(R.id.layoutSearchResult)
    RelativeLayout mLayoutSearchResult;
    @Bind(R.id.layoutAgentInformation)
    RelativeLayout mLayoutAgentInformation;
    @Bind(R.id.layoutReviewSubmit)
    RelativeLayout mLayoutReviewSubmit;
    @Bind(R.id.layoutSaveSearchDetail)
    RelativeLayout mLayoutSaveSearchDetail;
    @Bind(R.id.layoutListingDetail)
    RelativeLayout mLayoutListingDetail;
    @Bind(R.id.rvSearchResult)
    RecyclerView mRvSearchResult;
    @Bind(R.id.ivMenu)
    ImageView mIvMenu;
    @Bind(R.id.layoutTutorial)
    LinearLayout mLayoutTutorial;
    @Bind(R.id.layoutMainActivity)
    RelativeLayout mLayoutMainActivity;
    @Bind(R.id.layoutMainContent)
    RelativeLayout mLayoutMainContent;
    @Bind(R.id.layoutBg)
    LinearLayout mLayoutBg;
    @Bind(R.id.layoutBg1)
    LinearLayout mLayoutBg1;
    @Bind(R.id.layoutViewPager)
    RelativeLayout mLayoutViewPager;
    @Bind(R.id.layoutNotifications)
    RelativeLayout mLayoutNotifications;
    @Bind(R.id.tvNotificationCount)
    TextView mTvNotificationCount;

    //Socket
    private LoginPresenter mLoginPresenter;

    @OnTextChanged(value = R.id.etSearch, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterSearch(Editable editable) {
        if (mLayoutSearchResult.getVisibility() == View.VISIBLE) {
            if (editable.length() != 0) {
                mIvSearch.setVisibility(View.GONE);
                mIvCloseSearch.setVisibility(View.VISIBLE);
                mPlacePresenter.searchPlace(editable.toString());
            } else {
                mPlacePresenter.cancelSearch();
                mArrPlace.clear();
                mSearchAdapter.notifyDataSetChanged();
                mIvSearch.setVisibility(View.VISIBLE);
                mIvCloseSearch.setVisibility(View.GONE);
            }
        }
    }

    @OnClick(R.id.etSearch)
    public void onEditTextSearch() {
        if (mLayoutSaveSearchDetail.getVisibility() == View.VISIBLE) {
//            mEtSearch.setFocusable(true);
//            mEtSearch.setFocusableInTouchMode(true);
            if (!getFragmentSavedSearchDetail().isGetDetailSuccess()) {
                return;
            }
            mLayoutSaveSearchDetail.setVisibility(View.GONE);
            mIvFilter.setVisibility(View.GONE);
            mLayoutNotifications.setVisibility(View.VISIBLE);
            getFragmentSavedSearchDetail().clearViewPager();
            mSavedSearchDetail = false;
            mFragmentDiscover.openSaveSearchDetailInDiscover();
            AnimUtils.animationButtonMenuWithCloseView(mDrawerArrowDrawable);
            if (mViewPagerMain.getCurrentItem() == 0) {
                mViewPagerMain.setCurrentItem(1, true);
                mLayoutBottom.setVisibility(View.VISIBLE);
                moveViewCurrentPage(mCurrentPage, mXCenterDiscover, 2);
            }
        }
    }

    @OnFocusChange(value = R.id.etSearch)
    public void focusSearch(boolean b) {
        if (b) {
            if (sOpenLayoutChange) {
                AnimUtils.resizeLayout(mLayoutChooseType, Convert.dpToPx(50, this), 0, "layoutChooseType");
                sOpenLayoutChange = false;
            }
            if (mLayoutSearchResult.getVisibility() == View.GONE) {
                if (mFragmentDiscover.isOpenSavedSearch()) {
                    AnimUtils.animationButtonMenuWithOpenView(null, mLayoutSearchResult, true);
                } else if (mLayoutAgentInformation.getVisibility() == View.VISIBLE) {
                    AnimUtils.animationButtonMenuWithOpenView(null, mLayoutSearchResult, true);
                } else if (mLayoutReviewSubmit.getVisibility() == View.VISIBLE) {
                    AnimUtils.animationButtonMenuWithOpenView(null, mLayoutSearchResult, true);
                } else if (mLayoutListingDetail.getVisibility() == View.VISIBLE) {
                    AnimUtils.animationButtonMenuWithOpenView(null, mLayoutSearchResult, true);
                } else if (mLayoutSaveSearchDetail.getVisibility() == View.VISIBLE) {
                    AnimUtils.animationButtonMenuWithOpenView(null, mLayoutSearchResult, true);
                } else if (mFragmentShowing.isShowCaravanAction()) {
                    AnimUtils.animationButtonMenuWithOpenView(null, mLayoutSearchResult, true);
                    mFragmentShowing.hideCaravanAction(false);
                } else if (mFragmentSchedule.isShowStepSchedule()) {
                    AnimUtils.animationButtonMenuWithOpenView(null, mLayoutSearchResult, true);
                    mFragmentSchedule.closeStepSchedule(false);
                } else {
                    AnimUtils.animationButtonMenuWithOpenView(mDrawerArrowDrawable, mLayoutSearchResult, true);
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mLayoutListingDetail.getVisibility() == View.VISIBLE) {
                            mLayoutListingDetail.setVisibility(View.GONE);
                        }
                        if (mLayoutAgentInformation.getVisibility() == View.VISIBLE) {
                            mLayoutAgentInformation.setVisibility(View.GONE);
                        }
                        if (mLayoutReviewSubmit.getVisibility() == View.VISIBLE) {
                            mLayoutReviewSubmit.setVisibility(View.GONE);
                        }
                        if (mLayoutSaveSearchDetail.getVisibility() == View.VISIBLE) {
                            mLayoutSaveSearchDetail.setVisibility(View.GONE);
                        }
                    }
                }, 200);
            } else {
                AnimUtils.animationButtonMenuWithCloseView(mDrawerArrowDrawable, mLayoutSearchResult, true);
            }
        }
    }

    @OnTouch(R.id.layoutTutorial)
    public boolean touchTutorial(MotionEvent event) {
        mLayoutTutorial.setVisibility(View.GONE);
        return false;
    }


    @OnTouch(R.id.etSearch)
    public boolean touchSearch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mEtSearch.setHintTextColor(ContextCompat.getColor(MainActivityConsumer.this, R.color.colorDashboardText));
                break;
            case MotionEvent.ACTION_UP:
                mEtSearch.setHintTextColor(ContextCompat.getColor(MainActivityConsumer.this, R.color.colorHintSearch));
                break;
        }
        return false;
    }


    @OnClick(R.id.ivMenu)
    public void openMenu() {
        if (mLayoutSearchResult.getVisibility() == View.VISIBLE) {
            cancelSearch(false);
        } else {
            if (mViewPagerMain.getVisibility() == View.VISIBLE) {
                if (mFragmentDiscover.getCurrentStepInDiscover() == CurrentFragment.IN_FIlTER && mCurrentFragment == 1) {
                    mFragmentDiscover.cancelFilter();
                } else if (mFragmentDiscover.getCurrentStepInDiscover() == CurrentFragment.IN_OPEN_SEARCH && mCurrentFragment == 1
                        && mLayoutSaveSearchDetail.getVisibility() == View.GONE) {
                    mFragmentDiscover.cancelOpenSearch();
                } else if (mLayoutSaveSearchDetail.getVisibility() == View.VISIBLE) {
                    if (getFragmentSavedSearchDetail().isShowFilter()) {
                        getFragmentSavedSearchDetail().cancelFilter();
                    } else {
                        mIvFilter.setVisibility(View.GONE);
                        mLayoutNotifications.setVisibility(View.VISIBLE);
                        hideSaveSearchDetail();
                    }
                } else if (mLayoutListingDetail.getVisibility() == View.VISIBLE) {
                    mFragmentListingDetail.hideLayout();
                    AnimUtils.animationButtonMenuWithCloseView(mDrawerArrowDrawable, mLayoutListingDetail, true);
                } else if (mLayoutReviewSubmit.getVisibility() == View.VISIBLE) {
                    AnimUtils.animationButtonMenuWithCloseView(mDrawerArrowDrawable, mLayoutReviewSubmit, true);
                } else if (mLayoutAgentInformation.getVisibility() == View.VISIBLE) {
                    AnimUtils.animationButtonMenuWithCloseView(mDrawerArrowDrawable, mLayoutAgentInformation, true);
                } else if (mFragmentSchedule.isShowStepSchedule() && mCurrentFragment == 2) {
                    mFragmentSchedule.closeStepSchedule(true);
                } else if (mFragmentShowing.isShowCaravanAction() && mCurrentFragment == 3) {
                    mFragmentShowing.hideCaravanAction(true);
                } else {
                    mSlidingMenu.toggle();
                }
            } else {
                mSlidingMenu.toggle();
            }
        }
    }

    @OnClick(R.id.ivSearch)
    public void search() {

    }

    @OnClick(R.id.ivCloseSearch)
    public void closeSearch() {
        mEtSearch.setText("");
        mIvSearch.setVisibility(View.VISIBLE);
        mIvCloseSearch.setVisibility(View.GONE);
    }


    @OnClick(R.id.ivFilter)
    public void filter() {
        if (!getFragmentSavedSearchDetail().isGetDetailSuccess()) {
            return;
        }
        getFragmentSavedSearchDetail().showFilter();
    }

    @OnClick(R.id.layoutChangeType)
    public void openChange() {
        if (sOpenLayoutChange) {
            AnimUtils.resizeLayout(mLayoutChooseType, Convert.dpToPx(50, this), 0, "layoutChooseType");
            sOpenLayoutChange = false;
        } else {
            AnimUtils.resizeLayout(mLayoutChooseType, 0, Convert.dpToPx(50, this), "layoutChooseType");
            sOpenLayoutChange = true;
        }

    }

    @OnClick(R.id.layoutDashboard)
    public void openDashboard() {
        mFragmentDashboard.showLayout();
        showViewPagerMain();
        mViewPagerMain.setCurrentItem(0, true);
        moveViewCurrentPage(mCurrentPage, mXCenterDashboard, 1);
    }

    @OnClick(R.id.layoutDiscover)
    public void openDiscover() {
        //mFragmentDiscover.showLayout();
        showViewPagerMain();
        mViewPagerMain.setCurrentItem(1, true);
        moveViewCurrentPage(mCurrentPage, mXCenterDiscover, 2);
    }

    @OnClick(R.id.layoutSchedule)
    public void openSchedule() {
        showViewPagerMain();
        mViewPagerMain.setCurrentItem(2, true);
        mFragmentSchedule.initSchedule();
        moveViewCurrentPage(mCurrentPage, mXCenterSchedule, 3);
    }

    @OnClick(R.id.layoutShowing)
    public void openShowing() {
        showViewPagerMain();
        mViewPagerMain.setCurrentItem(3, true);
        moveViewCurrentPage(mCurrentPage, mXCenterShowing, 4);
    }

    @OnClick(R.id.layoutMyTeam)
    public void openMyTeam() {
        showViewPagerMain();
        mViewPagerMain.setCurrentItem(4, true);
        moveViewCurrentPage(mCurrentPage, mXCenterMyTeam, 5);
    }


    @OnClick(R.id.tvBuy)
    public void showBuy() {
        mTypeBefore = mTypeAfter;
        mTypeAfter = 1;
        moveView(mLayoutMove, mXBuy, TypeConsumer.BUY);
        moveView(mIvMove, mXCenterBuy, TypeConsumer.BUY);
        checkType(TypeConsumer.BUY);
        showViewPagerIntro();
        mViewPagerIntro.setCurrentItem(0);
        animationSelectTab(0);
        mCurrentPage.setVisibility(View.INVISIBLE);
    }


    @OnClick(R.id.tvRent)
    public void showRent() {
        mTypeBefore = mTypeAfter;
        mTypeAfter = 2;
        moveView(mLayoutMove, mXRent, TypeConsumer.RENT);
        moveView(mIvMove, mXCenterRent, TypeConsumer.RENT);
        checkType(TypeConsumer.RENT);
        showViewPagerIntro();
        mViewPagerIntro.setCurrentItem(1);
        animationSelectTab(0);
        mCurrentPage.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.tvSell)
    public void showSell() {
        mTypeBefore = mTypeAfter;
        mTypeAfter = 3;
        moveView(mLayoutMove, mXSell, TypeConsumer.SELL);
        moveView(mIvMove, mXCenterSell, TypeConsumer.SELL);
        checkType(TypeConsumer.SELL);
        showViewPagerIntro();
        mViewPagerIntro.setCurrentItem(2);
        animationSelectTab(0);
        mCurrentPage.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        checkNotificationClickedBefore();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_consumer);
        HomeCaravanApplication.mSocket.connect();
        ButterKnife.bind(this);
        mPrefs = getSharedPreferences(Constants.getInstance().HOME_CARAVAN_CONSUMER, Context.MODE_PRIVATE);
        checkTutorial();
        EventBus.getDefault().register(this);
        setBehindView();
        setUpPositionAnimation();
        mConsumerDialog = new ConsumerDialog(this);
        mFragmentBuy = new FragmentBuyIntro();
        mFragmentBuy.setListener(this);
        mFragmentSell = new FragmentSellIntro();
        mFragmentSell.setListener(this);
        mFragmentRent = new FragmentRentIntro();
        mFragmentRent.setListener(this);
        mFragmentListingDetail = new FragmentListingDetail();
        mAgentInformation = new FragmentAgentInformation();
        mSubmitReviews = new FragmentSubmitReviews();
        mFragmentSavedSearchDetail = new FragmentSavedSearchDetail();
        mAddQueuePresenter = new AddQueuePresenter(this);
        mAddFavoritePresenter = new AddFavoritePresenter(this);
        mRemoveQueuePresenter = new RemoveQueuePresenter(this);
        init();
        mSlidingMenu = getSlidingMenu();
        mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        mSlidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_offset);
        mSlidingMenu.setFadeDegree(0.35f);
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        mSlidingMenu.setSlidingEnabled(false);
        setupViewpagerIntro();
        setupViewpagerMain();
        setupSearch();
        initButtonMenu();
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (mViewPagerIntro.getVisibility() == View.VISIBLE) {
                    showViewPagerMain();
                    mViewPagerMain.setCurrentItem(1, true);
                    moveViewCurrentPage(mCurrentPage, mXCenterDiscover, 2);
                    mFragmentDiscover.openDiscoverAfterSearch(mEtSearch.getText().toString(), false);
                } else {
                    mViewPagerMain.setCurrentItem(1, true);
                    moveViewCurrentPage(mCurrentPage, mXCenterDiscover, 2);
                    mFragmentDiscover.openDiscoverAfterSearch(mEtSearch.getText().toString(), false);
                }
                cancelSearch(true);
                return false;
            }
        });
        getNotificationCount();
        mSlidingMenu.setOnOpenedListener(new SlidingMenu.OnOpenedListener() {
            @Override
            public void onOpened() {

            }
        });
        mSlidingMenu.setOnClosedListener(new SlidingMenu.OnClosedListener() {
            @Override
            public void onClosed() {
                mLayoutBg.setEnabled(true);
                mShowMenu = false;
                mLayoutBg1.setVisibility(View.GONE);
            }
        });
        mSlidingMenu.setOnOpenListener(new SlidingMenu.OnOpenListener() {
            @Override
            public void onOpen() {
                mLayoutBg.setVisibility(View.VISIBLE);
                mLayoutBg1.setVisibility(View.VISIBLE);
                AnimUtils.changeBgMain(MainActivityConsumer.this, mLayoutBg, true, mIvMenu, mDrawerArrowDrawable, R.drawable.ic_close_menu);
            }
        });
        mSlidingMenu.setOnCloseListener(new SlidingMenu.OnCloseListener() {
            @Override
            public void onClose() {
                mLayoutBg.setEnabled(false);
                mShowMenu = true;
                AnimUtils.changeBgMain(MainActivityConsumer.this, mLayoutBg, false, mIvMenu, mDrawerArrowDrawable, R.drawable.ic_close_menu);
            }
        });
        mLayoutBg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mSlidingMenu.toggle();
                return false;
            }
        });
        mLayoutBg1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return mShowMenu;
            }
        });

        //Socket
        mLoginPresenter = new LoginPresenter(this);
        loginSocket();
    }

    @Override
    public void onBackPressed() {
        if (mLayoutSearchResult.getVisibility() == View.VISIBLE) {
            cancelSearch(false);
        } else {
            if (mViewPagerMain.getVisibility() == View.VISIBLE) {
                if (mFragmentDiscover.getCurrentStepInDiscover() == CurrentFragment.IN_FIlTER && mCurrentFragment == 1) {
                    mFragmentDiscover.cancelFilter();
                } else if (mFragmentDiscover.getCurrentStepInDiscover() == CurrentFragment.IN_OPEN_SEARCH && mCurrentFragment == 1 && mLayoutSaveSearchDetail.getVisibility() == View.GONE) {
                    mFragmentDiscover.cancelOpenSearch();
                } else if (mLayoutSaveSearchDetail.getVisibility() == View.VISIBLE) {
                    if (getFragmentSavedSearchDetail().isShowFilter()) {
                        getFragmentSavedSearchDetail().cancelFilter();
                    } else {
                        hideSaveSearchDetail();
                        mIvFilter.setVisibility(View.GONE);
                        mLayoutNotifications.setVisibility(View.VISIBLE);
                    }
                } else if (mLayoutListingDetail.getVisibility() == View.VISIBLE) {
                    mFragmentListingDetail.hideLayout();
                    AnimUtils.animationButtonMenuWithCloseView(mDrawerArrowDrawable, mLayoutListingDetail, true);
                } else if (mLayoutReviewSubmit.getVisibility() == View.VISIBLE) {
                    AnimUtils.animationButtonMenuWithCloseView(mDrawerArrowDrawable, mLayoutReviewSubmit, true);
                } else if (mLayoutAgentInformation.getVisibility() == View.VISIBLE) {
                    AnimUtils.animationButtonMenuWithCloseView(mDrawerArrowDrawable, mLayoutAgentInformation, true);
                } else if (mFragmentSchedule.isShowStepSchedule() && mCurrentFragment == 2) {
                    mFragmentSchedule.closeStepSchedule(true);
                } else if (mFragmentShowing.isShowCaravanAction() && mCurrentFragment == 3) {
                    mFragmentShowing.hideCaravanAction(true);
                } else {
                    super.onBackPressed();
                }
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("onStop", "onStopMain");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("onPause", "onPause");
        HomeCaravanApplication.mSocket.off(Constants.getInstance().THREAD);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume", "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        socketListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void addFragmentAgentInformation() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(mLayoutAgentInformation.getId(), mAgentInformation, "agentInformation");
        fragmentTransaction.commit();
    }

    public void showAgentInformation(final String agentId) {
        ObjectAnimator objectAnimator = AnimUtils.animationButtonMenuWithOpenViewWithListener(mDrawerArrowDrawable, mLayoutAgentInformation, true);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mAgentInformation.initAgentInformation(agentId);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        objectAnimator.start();
    }

    public void addFragmentReviewSubmit() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(mLayoutReviewSubmit.getId(), mSubmitReviews, "reviewSubmit");
        fragmentTransaction.commit();
    }

    public void showReviewSubmit() {
        ObjectAnimator objectAnimator = AnimUtils.animationButtonMenuWithOpenViewWithListener(mDrawerArrowDrawable, mLayoutReviewSubmit, true);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mSubmitReviews.showLayout();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        objectAnimator.start();
    }

    public void addFragmentListingDetail() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(mLayoutListingDetail.getId(), mFragmentListingDetail, "listingDetail");
        fragmentTransaction.commit();
    }

    public void showListingDetail(final String listingId) {
        ObjectAnimator objectAnimator = AnimUtils.animationButtonMenuWithOpenViewWithListener(mDrawerArrowDrawable, mLayoutListingDetail, true);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mFragmentListingDetail.showLayout(listingId);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        objectAnimator.start();
    }

    public void addFragmentSaveSearchDetail() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(mLayoutSaveSearchDetail.getId(), mFragmentSavedSearchDetail, "savedSearchDetail");
        fragmentTransaction.commit();
    }

    public void showSaveSearchDetail(final String id, boolean b) {
        mLayoutNotifications.setVisibility(View.GONE);
        mIvFilter.setVisibility(View.VISIBLE);
        mEtSearch.setFocusable(false);
        mEtSearch.setFocusableInTouchMode(false);
        ObjectAnimator objectAnimator;
        if (b) {
            objectAnimator = AnimUtils.animationButtonMenuWithOpenViewWithListener(MainActivityConsumer.mDrawerArrowDrawable, mLayoutSaveSearchDetail, true);
        } else {
            objectAnimator = AnimUtils.animationButtonMenuWithOpenViewWithListener(null, mLayoutSaveSearchDetail, true);
        }
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                updateEditTextSearch(Utils.getNameSavedSearch(CurrentSaveSearch.getInstance().getName(), MainActivityConsumer.this));
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getFragmentSavedSearchDetail().updateViewPager(id);
                        mSavedSearchDetail = true;
                        mLayoutBottom.setVisibility(View.GONE);
                    }
                }, 200);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        objectAnimator.start();

    }

    public void hideSaveSearchDetail() {
        mLayoutNotifications.setVisibility(View.VISIBLE);
        mIvFilter.setVisibility(View.GONE);
        if (mFragmentDiscover.isOpenSavedSearch()) {
            AnimUtils.animationButtonMenuWithCloseView(null, mLayoutSaveSearchDetail, true);
            mEtSearch.setText("");
            mEtSearch.setFocusable(true);
            mEtSearch.setFocusableInTouchMode(true);
//            mFragmentDiscover.setShowListingDetail(false);
        } else {
            if (mViewPagerMain.getCurrentItem() == 0) {
                mEtSearch.setText("");
                mEtSearch.setFocusable(true);
                mEtSearch.setFocusableInTouchMode(true);
                mFragmentDiscover.setShowListingDetail(false);
            } else {
                //mFragmentDiscover.openSaveSearchDetailInDiscover();
            }
            AnimUtils.animationButtonMenuWithCloseView(mDrawerArrowDrawable, mLayoutSaveSearchDetail, true);
        }
        getFragmentSavedSearchDetail().clearViewPager();
        mSavedSearchDetail = false;
        mLayoutBottom.setVisibility(View.VISIBLE);

    }

    @Override
    public void openSaveSearchDetailFromDiscover(String searchId, boolean b) {
        showSaveSearchDetail(searchId, b);
    }

    public FragmentSavedSearchDetail getFragmentSavedSearchDetail() {
        return (FragmentSavedSearchDetail) getSupportFragmentManager().findFragmentByTag("savedSearchDetail");
    }

    public void updateEditTextSearch(SpannableStringBuilder s) {
        EditText editText = (EditText) findViewById(R.id.etSearch);
        if (s == null) {
            editText.setText("");
        } else {
            editText.setText(s);
        }

    }

    public void moveViewCurrentPage(View view, float x, final int position) {
        animationSelectTab(position);
        view.setVisibility(View.VISIBLE);
        ViewCompat.animate(view).translationX(x).setDuration(200).start();
        mIndexPage = position;
    }

    public void moveView(View view, float x, final TypeConsumer type) {
        ViewCompat.animate(view).translationX(x).setDuration(200).start();
    }

    public void setUpPositionAnimation() {
        mBuy.post(new Runnable() {
            @Override
            public void run() {
                mXBuy = mBuy.getX();
                mXCenterBuy = mBuy.getWidth() / 2 + mBuy.getX() - Convert.dpToPx(30, MainActivityConsumer.this) / 2;
                moveView(mIvMove, mXCenterBuy, TypeConsumer.BUY);
            }
        });
        mRent.post(new Runnable() {
            @Override
            public void run() {
                mXRent = mRent.getX();
                mXCenterRent = mRent.getWidth() / 2 + mRent.getX() - Convert.dpToPx(30, MainActivityConsumer.this) / 2;
            }
        });
        mSell.post(new Runnable() {
            @Override
            public void run() {
                mXSell = mSell.getX();
                mXCenterSell = mSell.getWidth() / 2 + mSell.getX() - Convert.dpToPx(30, MainActivityConsumer.this) / 2;
                addFragment();
            }
        });

        mDashboard.post(new Runnable() {
            @Override
            public void run() {
                mXCenterDashboard = mDashboard.getWidth() / 2 + mDashboard.getX() + Convert.dpToPx(15, MainActivityConsumer.this) / 2;
            }
        });
        mDiscover.post(new Runnable() {
            @Override
            public void run() {
                mXCenterDiscover = mDiscover.getWidth() / 2 + mDiscover.getX() + Convert.dpToPx(15, MainActivityConsumer.this) / 2;
            }
        });
        mSchedule.post(new Runnable() {
            @Override
            public void run() {
                mXCenterSchedule = mSchedule.getWidth() / 2 + mSchedule.getX() + Convert.dpToPx(15, MainActivityConsumer.this) / 2;
            }
        });
        mShowing.post(new Runnable() {
            @Override
            public void run() {
                mXCenterShowing = mShowing.getWidth() / 2 + mShowing.getX() + Convert.dpToPx(15, MainActivityConsumer.this) / 2;
            }
        });
        mMyTeam.post(new Runnable() {
            @Override
            public void run() {
                mXCenterMyTeam = mMyTeam.getWidth() / 2 + mMyTeam.getX() + Convert.dpToPx(15, MainActivityConsumer.this) / 2;
            }
        });
    }

    public void checkType(TypeConsumer type) {
        if (type == TypeConsumer.BUY) {
            openLayoutBuy();
        }
        if (type == TypeConsumer.RENT) {
            openLayoutRent();
        }
        if (type == TypeConsumer.SELL) {
            openLayoutSell();
        }
    }

    public void openLayoutBuy() {
        changeColorWhenChangeType();
        AnimUtils.changeTextColor(this, R.color.text_view_consumer_non_selector, R.color.text_view_consumer_selector, mBuy);
        mRent.setTextColor(Utils.getColorResources(this, R.color.text_view_consumer_non_selector));
        mSell.setTextColor(Utils.getColorResources(this, R.color.text_view_consumer_non_selector));
    }

    public void openLayoutRent() {
        changeColorWhenChangeType();
        mBuy.setTextColor(Utils.getColorResources(this, R.color.text_view_consumer_non_selector));
        AnimUtils.changeTextColor(this, R.color.text_view_consumer_non_selector, R.color.text_view_consumer_selector, mRent);
        mSell.setTextColor(Utils.getColorResources(this, R.color.text_view_consumer_non_selector));

    }

    public void openLayoutSell() {
        changeColorWhenChangeType();
        mBuy.setTextColor(Utils.getColorResources(this, R.color.text_view_consumer_non_selector));
        mRent.setTextColor(Utils.getColorResources(this, R.color.text_view_consumer_non_selector));
        AnimUtils.changeTextColor(this, R.color.text_view_consumer_non_selector, R.color.text_view_consumer_selector, mSell);
    }

    public void addFragment() {
        AnimUtils.resizeLayout(mLayoutChooseType, Convert.dpToPx(50, this), 0, "layoutChooseType");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLayoutChooseType.setVisibility(View.VISIBLE);
            }
        }, 400);
    }

    public void createDataConsumer() {
        ConsumerDummyData consumerDummyData = new ConsumerDummyData(this);
        consumerDummyData.createDummyData();
    }

    public void init() {
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        Utils.heightScreen = mDisplayMetrics.heightPixels;
        Utils.widthScreen = mDisplayMetrics.widthPixels;
        sHeightFragment = mDisplayMetrics.heightPixels - Convert.dpToPx(146, this);
        mManager = getSupportFragmentManager();
    }

    public void changeColorWhenChangeType() {
        int fromDrawable = 0, toDrawable = 0;
        int fromColor = 0, toColor = 0;
        String type = "";
        if (mTypeBefore == 1) {
            fromColor = R.color.colorBuy;
            fromDrawable = R.drawable.bg_buy_pick;
            type = "B";
        }
        if (mTypeBefore == 2) {
            fromColor = R.color.colorRent;
            fromDrawable = R.drawable.bg_rent_pick;
            type = "R";
        }
        if (mTypeBefore == 3) {
            fromColor = R.color.colorSell;
            fromDrawable = R.drawable.bg_sell_pick;
            type = "S";
        }
        if (mTypeAfter == 1) {
            toColor = R.color.colorBuy;
            toDrawable = R.drawable.bg_buy_pick;
            type = "B";
        }
        if (mTypeAfter == 2) {
            toColor = R.color.colorRent;
            toDrawable = R.drawable.bg_rent_pick;
            type = "R";
        }
        if (mTypeAfter == 3) {
            toColor = R.color.colorSell;
            toDrawable = R.drawable.bg_sell_pick;
            type = "S";
        }


        AnimUtils.scaleView(mLayoutChangeType, 1f, 0.9f, 1f, 0.9f);
        AnimUtils.changeBackgroundColor(this, fromColor, toColor, mLayoutBar);
        AnimUtils.changeColorFilter(this, fromColor, toColor, mIvMove);
        AnimUtils.changeBackgroundDrawable(this, fromDrawable, toDrawable, mLayoutChangeType);
        AnimUtils.changeBackgroundDrawable(this, fromDrawable, toDrawable, mViewMove);
        AnimUtils.changeStatusBarColor(this, fromColor, toColor, this);
        mTvType.setText(type);

    }

    public void animationSelectTab(int position) {
        if (mIndexPage != 0) {
            if (mIndexPage == 1) {
                AnimUtils.changeColorFilter(this, R.color.colorBottomBar, R.color.colorDashboardText, mIvDashboard);
                AnimUtils.changeTextColor(this, R.color.colorBottomBar, R.color.colorDashboardText, mTvDashboard);
            }
            if (mIndexPage == 2) {
                AnimUtils.changeColorFilter(this, R.color.colorBottomBar, R.color.colorDashboardText, mIvDiscover);
                AnimUtils.changeTextColor(this, R.color.colorBottomBar, R.color.colorDashboardText, mTvDiscover);
            }
            if (mIndexPage == 3) {
                AnimUtils.changeColorFilter(this, R.color.colorBottomBar, R.color.colorDashboardText, mIvSchedule);
                AnimUtils.changeTextColor(this, R.color.colorBottomBar, R.color.colorDashboardText, mTvSchedule);
            }
            if (mIndexPage == 4) {
                AnimUtils.changeColorFilter(this, R.color.colorBottomBar, R.color.colorDashboardText, mIvShowing);
                AnimUtils.changeTextColor(this, R.color.colorBottomBar, R.color.colorDashboardText, mTvShowing);
            }
            if (mIndexPage == 5) {
                AnimUtils.changeColorFilter(this, R.color.colorBottomBar, R.color.colorDashboardText, mIvMyTeam);
                AnimUtils.changeTextColor(this, R.color.colorBottomBar, R.color.colorDashboardText, mTvMyTeam);
            }
        }

        if (position == 1) {
            AnimUtils.scaleView(mDashboard, 1f, 1.1f, 1f, 1.1f);
            AnimUtils.changeColorFilter(this, R.color.colorDashboardText, R.color.colorBottomBar, mIvDashboard);
            AnimUtils.changeTextColor(this, R.color.colorDashboardText, R.color.colorBottomBar, mTvDashboard);
        }
        if (position == 2) {
            AnimUtils.scaleView(mDiscover, 1f, 1.1f, 1f, 1.1f);
            AnimUtils.changeColorFilter(this, R.color.colorDashboardText, R.color.colorBottomBar, mIvDiscover);
            AnimUtils.changeTextColor(this, R.color.colorDashboardText, R.color.colorBottomBar, mTvDiscover);
        }
        if (position == 3) {
            AnimUtils.scaleView(mSchedule, 1f, 1.1f, 1f, 1.1f);
            AnimUtils.changeColorFilter(this, R.color.colorDashboardText, R.color.colorBottomBar, mIvSchedule);
            AnimUtils.changeTextColor(this, R.color.colorDashboardText, R.color.colorBottomBar, mTvSchedule);
        }
        if (position == 4) {
            AnimUtils.scaleView(mShowing, 1f, 1.1f, 1f, 1.1f);
            AnimUtils.changeColorFilter(this, R.color.colorDashboardText, R.color.colorBottomBar, mIvShowing);
            AnimUtils.changeTextColor(this, R.color.colorDashboardText, R.color.colorBottomBar, mTvShowing);
        }
        if (position == 5) {
            AnimUtils.scaleView(mMyTeam, 1f, 1.1f, 1f, 1.1f);
            AnimUtils.changeColorFilter(this, R.color.colorDashboardText, R.color.colorBottomBar, mIvMyTeam);
            AnimUtils.changeTextColor(this, R.color.colorDashboardText, R.color.colorBottomBar, mTvMyTeam);
        }
    }

    public void setBehindView() {
        setBehindContentView(R.layout.slide_menu_consumer);
        mMenuFragmentConsumer = new MenuFragmentConsumer();
        mMenuFragmentConsumer.setListener(this);
        transactionFragments(mMenuFragmentConsumer, R.id.menu_slide);
        String serialized = mPrefs.getString(Constants.getInstance().NEW_MESSAGE_THREAD_ID_LIST, null);
        if (serialized != null && !serialized.isEmpty()) {
            SkeletonNewMessage.getInstance().setData(Arrays.asList(TextUtils.split(serialized, ",")));
            mMenuFragmentConsumer.setNewMessageCount(SkeletonNewMessage.getInstance().getData().size());
        }
    }

    public void transactionFragments(Fragment fragment, int viewResource) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(viewResource, fragment);
        ft.commit();
        toggle();
    }

    public void checkTutorial() {

        SharedPreferences mCheckLogin = getSharedPreferences("firstOpen", MODE_PRIVATE);
        if (!mCheckLogin.getBoolean("firstOpen", false)) {
            mLayoutTutorial.setVisibility(View.VISIBLE);
            SharedPreferences.Editor edit = mCheckLogin.edit();
            edit.putBoolean("firstOpen", true);
            edit.apply();
        }
    }

    @Override
    public void disableSlidingMenu(boolean b) {
        mSlidingMenu.setSlidingEnabled(!b);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            mFragmentDiscover.onActivityResult(requestCode, requestCode, data);
        }
        if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                yesAction("");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            mFragmentDiscover.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void initButtonMenu() {
        mDrawerArrowDrawable = new DrawerArrowDrawable(this);
        mDrawerArrowDrawable.setColor(Color.WHITE);
        mIvMenu.setImageDrawable(mDrawerArrowDrawable);
    }

    public void setupSearch() {
        mPlacePresenter = new SearchPlacePresenter(this);
        mSearchAdapter = new SearchResultAdapter(mArrPlace, this, this);
        mRvSearchResult.setLayoutManager(new LinearLayoutManager(this));
        mRvSearchResult.setAdapter(mSearchAdapter);
    }

    public void setupViewpagerIntro() {
        mArrFragmentIntro.add(mFragmentBuy);
        mArrFragmentIntro.add(mFragmentRent);
        mArrFragmentIntro.add(mFragmentSell);
        mAdapterIntro = new ViewPagerAdapterIntro(getSupportFragmentManager(), mArrFragmentIntro);
        mViewPagerIntro.setAdapter(mAdapterIntro);
        mViewPagerIntro.setPageTransformer(true, new FlipHorizontalTransformer());
        mViewPagerIntro.setOffscreenPageLimit(3);
    }

    public void setupViewpagerMain() {
        Log.e("setupViewpagerMain", "setupViewpagerMain");
        mFragmentDashboard.setPageChange(this);
        mFragmentDiscover.setPageChange(this);
        mArrFragmentMain.add(mFragmentDashboard);
        mArrFragmentMain.add(mFragmentDiscover);
        mFragmentDiscover.setMainListener(this);
        mArrFragmentMain.add(mFragmentSchedule);
        mFragmentSchedule.setPageChange(this);
        mArrFragmentMain.add(mFragmentShowing);
        mFragmentShowing.setPageChange(this);
        mArrFragmentMain.add(mFragmentTeam);

        mAdapterMain = new ViewPagerAdapterMain(getSupportFragmentManager(), mArrFragmentMain);
        mViewPagerMain.setAdapter(mAdapterMain);
        mViewPagerMain.setOffscreenPageLimit(5);
        mViewPagerMain.addOnPageChangeListener(myOnPageChangeListener);
        mGetQueuePresenter = new GetQueuePresenter(this);
        mGetQueuePresenter.getQueue();
    }

    public void initDataFragment(int position) {
        if (position == 0) {
            mFragmentDashboard.initData();
        }
        if (position == 1) {
            mFragmentDiscover.initData();
        }
        if (position == 2) {

        }
        if (position == 3) {
            mFragmentShowing.initData();
        }
        if (position == 4) {
            mFragmentTeam.initData();
        }
    }

    public void resetFragmentApterPageChange(int position) {
        if (position == 0) {
            mFragmentDashboard.resetFragment();
        }
        if (position == 1) {
            mIvCloseSearch.setVisibility(View.GONE);
            mIvSearch.setVisibility(View.VISIBLE);
            mFragmentDiscover.resetFragment(true);
        }
        if (position == 2) {
            mFragmentSchedule.resetFragment();
        }
        if (position == 3) {
            mFragmentShowing.resetFragment();
        }
        if (position == 4) {
            mFragmentTeam.resetFragment();
        }
    }

    public void showViewPagerMain() {
        mAdapterIntro.resetFragment();
        if (sOpenLayoutChange) {
            AnimUtils.resizeLayout(mLayoutChooseType, Convert.dpToPx(50, this), 0, "layoutChooseType");
            sOpenLayoutChange = false;
        }
        mViewPagerMain.setVisibility(View.VISIBLE);
        mViewPagerIntro.setVisibility(View.GONE);
    }

    public void showViewPagerIntro() {
        mViewPagerMain.setVisibility(View.GONE);
        mViewPagerIntro.setVisibility(View.VISIBLE);
    }

    @Override
    public void closeChooseTypeFromFragment() {
        openChange();
    }

    @Override
    public void pageChange(int position) {
        if (position == 4) {
            mViewPagerMain.setCurrentItem(3, true);
            moveViewCurrentPage(mCurrentPage, mXCenterShowing, 4);
        }
    }

    @Override
    public void openAllSavedSearch() {
        mViewPagerMain.setCurrentItem(1, true);
        moveViewCurrentPage(mCurrentPage, mXCenterDiscover, 2);
        mFragmentDiscover.onLayoutOpenSavedSearchClicked();
    }

    @Override
    public void openSavedSearchItem(String id) {
        //mViewPagerMain.setCurrentItem(1, true);
        showSaveSearchDetail(id, true);
        //mFragmentDiscover.openSavedSearchItemFromDashboard(id);
    }

    @Override
    public void openAllShowing() {
        mViewPagerMain.setCurrentItem(3, true);
        moveViewCurrentPage(mCurrentPage, mXCenterShowing, 4);
    }

    @Override
    public void openAllAgent() {
        mViewPagerMain.setCurrentItem(4, true);
        moveViewCurrentPage(mCurrentPage, mXCenterMyTeam, 5);
    }

    @Override
    public void openAgent(int position) {

    }

    @Override
    public void openDiscover(String searchName) {
//        mViewPagerMain.setCurrentItem(1, true);
//        moveViewCurrentPage(mCurrentPage, mXCenterDiscover, 2);
//        mIvSearch.setVisibility(View.GONE);
//        mIvCloseSearch.setVisibility(View.VISIBLE);
        //onEditTextSearch();
        // trending and just list
    }

    @Override
    public void submitSchedule(String day) {
        showViewPagerMain();
        mViewPagerMain.setCurrentItem(3, true);
        moveViewCurrentPage(mCurrentPage, mXCenterShowing, 4);
        mFragmentShowing.onSubmitSchedule(day);
    }

    @Override
    public void editCaravanFromShowing() {
        mViewPagerMain.setCurrentItem(2, true);
        moveViewCurrentPage(mCurrentPage, mXCenterSchedule, 3);
        mFragmentSchedule.editCaravan();
    }

    @Override
    public void showSearchResult(String result) {
        if (mViewPagerIntro.getVisibility() == View.VISIBLE) {
            showViewPagerMain();
            mViewPagerMain.setCurrentItem(1, true);
            moveViewCurrentPage(mCurrentPage, mXCenterDiscover, 2);
            mFragmentDiscover.openDiscoverAfterSearch(result, true);
        } else {
            mViewPagerMain.setCurrentItem(1, true);
            moveViewCurrentPage(mCurrentPage, mXCenterDiscover, 2);
            mFragmentDiscover.openDiscoverAfterSearch(result, true);
        }
        cancelSearch(true);
    }

    @Override
    public void showListPlace(Predictions predictions) {
        mArrPlace.clear();
        mArrPlace.addAll(predictions.getPlaces());
        mSearchAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissSnackBar() {

    }

    @Override
    public void showError(String e) {
        Log.e("error", e);
    }

    @Override
    public void showEmpty() {

    }

    public void cancelSearch(boolean clickItem) {
        if (!clickItem) {
            mIvSearch.setVisibility(View.VISIBLE);
            mIvCloseSearch.setVisibility(View.GONE);
            //mEtSearch.setText("");
        }
        mEtSearch.clearFocus();
        if (mFragmentDiscover.isOpenSavedSearch()) {
            AnimUtils.animationButtonMenuWithCloseView(null, mLayoutSearchResult, true);
        } else {
            AnimUtils.animationButtonMenuWithCloseView(mDrawerArrowDrawable, mLayoutSearchResult, true);
        }
        HomeCaravanApplication.getInstance().getInput().hideSoftInputFromWindow(mEtSearch.getWindowToken(), 0);
    }

    @Override
    public String getNameSearch(String action) {
        return mEtSearch.getText().toString();
    }

    @Override
    public void hideBottomBar(boolean b) {
        if (b) {
            mLayoutBottom.setVisibility(View.GONE);
        } else {
            mLayoutBottom.setVisibility(View.VISIBLE);
        }
    }

    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventDialog(EventDialog event) {

    }

    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventListingDetail(EventListingDetail event) {
        showListingDetail(event.listingId);
    }

    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventAgentDetail(EventAgentDetail event) {
        showAgentInformation(event.agentId);
    }

    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventReviewSubmit(EventReviewSubmit event) {
        showReviewSubmit();
    }

    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onReloadSaveSearch(EventReloadSaveSearch reloadSaveSearch) {
        mFragmentDashboard.reloadSaveSearch();
    }

    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onFavoriteEvent(EventFavorite listing) {
        mFavoriteId = listing.id;
        mAddFavoritePresenter.addFavoriteListing(listing.id);
    }

    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onQueueEvent(EventQueue eventQueue) {
        mQueueId = eventQueue.id;
        if (eventQueue.addQueue) {
            mAddQueuePresenter.addQueue(eventQueue.id);
            mTvScheduleCount.setText(String.valueOf(Integer.parseInt(mTvScheduleCount.getText().toString()) + 1));
            mTvScheduleCount.setVisibility(View.VISIBLE);
            CaravanQueue.getInstance().getIds().add(eventQueue.id);
            //EventBus.getDefault().post(new EventRequestShowing(eventQueue.id));
        } else {
            mRemoveQueuePresenter.removeQueue(eventQueue.id);
            mTvScheduleCount.setText(String.valueOf(Integer.parseInt(mTvScheduleCount.getText().toString()) - 1));
            if (Integer.parseInt(mTvScheduleCount.getText().toString()) == 0) {
                mTvScheduleCount.setVisibility(View.GONE);
            }
            CaravanQueue.getInstance().setIdRemove(eventQueue.id);
            CaravanQueue.getInstance().removeQueueInSingleton();
        }
    }


    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onQueueEventRequest(EventQueueRequest eventQueue) {
        mQueueId = eventQueue.id;
        mAddQueuePresenter.addQueue(eventQueue.id);
        mTvScheduleCount.setText(String.valueOf(Integer.parseInt(mTvScheduleCount.getText().toString()) + 1));
        mTvScheduleCount.setVisibility(View.VISIBLE);
        CaravanQueue.getInstance().getIds().add(eventQueue.id);
        EventBus.getDefault().post(new EventRequestShowing(eventQueue.id));
    }


    @Override
    public void logOut() {
        mConsumerDialog.setAction("singOut");
        mConsumerDialog.setType(TypeDialog.CONFIRM);
        mConsumerDialog.setMessage("Do you want sign out ?");
        mConsumerDialog.setListener(this);
        mConsumerDialog.show();

    }

    @Override
    public void goMyAccount() {
        Intent intent = new Intent(this, UserProfileConsumerActivity.class);
        startActivityForResult(intent, 3);
        overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
    }

    @Override
    public void goMessage() {
        Intent intent = new Intent(this, MessageActivity.class);
        SkeletonNewMessage.getInstance().getData().clear();
        if (mPrefs != null) {
            SharedPreferences.Editor edit = mPrefs.edit();
            edit.remove(Constants.getInstance().NEW_MESSAGE_THREAD_ID_LIST);
            edit.putInt(Constants.getInstance().NEW_MESSAGE_COUNT, 0);
            edit.apply();
        }
        startActivity(intent);
        overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
    }

    @Override
    public void goSettings() {
        Intent intent = new Intent(this, AppSettingsActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
    }

    @Override
    public void closeMenu() {
        mSlidingMenu.toggle();
    }

    @Override
    public void goContact() {
        startActivity(new Intent(this, ContactsManagerActivity.class));
    }

    @Override
    public void goMyTeam() {
        openMyTeam();
    }

    @Override
    public void goSaveSearch() {
        showViewPagerMain();
        openAllSavedSearch();
    }

    @Override
    public void goShowing() {
        openShowing();
    }

    @Override
    public void goDiscover() {
        openDiscover();
    }

    @Override
    public void goSchedule() {
        openSchedule();
    }

    @Override
    public void goFavorite() {

    }

    @Override
    public void goProperty() {
        Intent intent = new Intent(this, SellerActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
    }

    @Override
    public void yesAction(String action) {
        mPrefs = getSharedPreferences(Constants.getInstance().HOME_CARAVAN_CONSUMER, Context.MODE_PRIVATE);
        if (mPrefs != null) {
            SharedPreferences.Editor edit = mPrefs.edit();
            edit.putString("mobileEmail", "");
            edit.putString("driverToken", "");
            edit.putString("password", "");
            edit.putString("idFacebook", "");
            edit.putString("idLinkedIn", "");
            edit.putString("fcm_token", "");
            edit.putString("fcm_user", "");
            edit.putString("fcm_user", "");
            edit.apply();
        }

        if (mPrefs != null) {
            SharedPreferences.Editor edit = mPrefs.edit();
            edit.putInt(Constants.getInstance().CIA_FATEST_INTERVAL, 60000);
            edit.apply();
        }

        Realm realm = HomeCaravanApplication.getInstance().getRealm();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();

        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);

        HomeCaravanApplication.mSocket.disconnect();
        HomeCaravanApplication.mSocket.close();
        HomeCaravanApplication.mLoginSocketSuccess = false;

        if (mPrefs != null) {
            SharedPreferences.Editor edit = mPrefs.edit();
            edit.remove(Constants.getInstance().NEW_MESSAGE_THREAD_ID_LIST);
            edit.putInt(Constants.getInstance().NEW_MESSAGE_COUNT, 0);
            edit.apply();
        }
        SkeletonNewMessage.getInstance().getData().clear();
        finish();
    }

    @Override
    public void noAction(String action) {

    }

    @Override
    public void otherAction(String action) {

    }

    @Override
    public void addQueueSuccess(Listing listing) {
        CaravanQueue.getInstance().getListings().add(listing);
        EventBus.getDefault().post(new EventQueueResponse(listing.getId(), true));
//        CaravanQueue.getInstance().getListings().add(listing);
//        CaravanQueue.getInstance().getIds().add(listing.getId());
//        Log.e("CaravanQueue", CaravanQueue.getInstance().getIds().toString());
    }

    @Override
    public void addQueueFail() {
//        int count = Integer.parseInt(mTvScheduleCount.getText().toString()) - 1;
//        mTvScheduleCount.setText(String.valueOf(count));
//        if (count == 0) {
//            mTvScheduleCount.setVisibility(View.GONE);
//            mTvScheduleCount.setText("0");
//        }
    }

    @Override
    public void getQueueSuccess(ArrayList<ResponseQueue.QueueData> listings) {

        for (int i = 0; i < listings.size(); i++) {
            CaravanQueue.getInstance().getIds().add(listings.get(i).getListing().getId());
            ConsumerListingSchedule listingSchedule = new ConsumerListingSchedule();
            listingSchedule.setListing(listings.get(i).getListing());
            CurrentListingSchedule.getInstance().getArrListing().add(listingSchedule);
            CaravanQueue.getInstance().getListings().add(listings.get(i).getListing());
        }

        int countQueue = CaravanQueue.getInstance().getListings().size();
        if (countQueue > 0) {
            mTvScheduleCount.setVisibility(View.VISIBLE);
            mTvScheduleCount.setText(String.valueOf(countQueue));
        }
    }

    @Override
    public void getQueueFail() {
        Log.e("getQueueFail", "getQueueFail");
    }

    @Override
    public void removeQueueSuccess() {
        EventBus.getDefault().post(new EventQueueResponse(mQueueId, false));
//        CaravanQueue.getInstance().removeQueueInSingleton();
//        Log.e("CaravanQueue", CaravanQueue.getInstance().getIds().toString());
    }

    @Override
    public void removeQueueFail() {
//        int count = Integer.parseInt(mTvScheduleCount.getText().toString()) + 1;
//        mTvScheduleCount.setText(String.valueOf(count));
//        if (count == 0) {
//            mTvScheduleCount.setVisibility(View.GONE);
//            mTvScheduleCount.setText("0");
//        }
    }

    @Override
    public void clearQueue(ArrayList<String> queueIds) {
        mTvScheduleCount.setText("0");
        mTvScheduleCount.setVisibility(View.GONE);
        for (int i = 0; i < queueIds.size(); i++) {
            CaravanQueue.getInstance().setIdRemove(queueIds.get(i));
            CaravanQueue.getInstance().removeQueueInSingleton();
        }
        CaravanQueue.getInstance().getIds().clear();
        CaravanQueue.getInstance().getListings().clear();
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        HomeCaravanApplication.mIsCaravanInAction = intent.getBooleanExtra("CARAVAN_IN_ACTION", false);
        HomeCaravanApplication.mCaravanID = intent.getStringExtra("CARAVAN_IN_ACTION_ID");
        Log.e(TAG, "Main onNewIntent - mIsCaravanInAction: " + HomeCaravanApplication.mIsCaravanInAction
                + " mCaravanID: " + HomeCaravanApplication.mCaravanID);
        if (HomeCaravanApplication.mIsCaravanInAction) {
            if (mViewPagerMain.getCurrentItem() != 3) {
                openAllShowing();
            } else {
                mFragmentShowing.checkToShowPopupWhenCurrentItemIsShowing();
            }
        }
    }

    private void checkNotificationClickedBefore() {
        HomeCaravanApplication.mIsCaravanInAction = getIntent().getBooleanExtra("CARAVAN_IN_ACTION", false);
        HomeCaravanApplication.mCaravanID = getIntent().getStringExtra("CARAVAN_IN_ACTION_ID");
        Log.e(TAG, "Main checkNotificationClickedBefore - mIsCaravanInAction: " + HomeCaravanApplication.mIsCaravanInAction
                + " mCaravanID: " + HomeCaravanApplication.mCaravanID);
        if (HomeCaravanApplication.mIsCaravanInAction) {
            if (ConsumerUser.getInstance().getData().getToken() == null
                    && !getIntent().getBooleanExtra("START_FROM_SPLASH_ACTIVITY", false)) {
                finish();
                Intent intent = new Intent(this, ConsumerSplashActivity.class);
                intent.putExtra("START_FROM_MAIN_ACTIVITY", true);
                startActivity(intent);
            }
            if (MainActivityConsumer.mIsCaravanInAction) {
                EventBus.getDefault().post(new EventCIA());
                MainActivityConsumer.mIsCaravanInAction = false;
            }
        }
    }

    private void checkNotificationClickedAfter() {
        Log.e(TAG, "Main checkNotificationClickedAfter - mIsCaravanInAction: " + HomeCaravanApplication.mIsCaravanInAction
                + " mCaravanID: " + HomeCaravanApplication.mCaravanID);
        if (HomeCaravanApplication.mIsCaravanInAction) {
            if (mViewPagerMain.getCurrentItem() != 3) {
                mFragmentShowing.initData();
                openShowing();
            } else {
                mFragmentShowing.checkToShowPopupWhenCurrentItemIsShowing();
            }
        }
    }

    @Override
    public void loadFragmentCompleted(int position) {
        if (position == 3)
            checkNotificationClickedAfter();
    }

    @Override
    public void initFragment() {
        addFragmentListingDetail();
        addFragmentReviewSubmit();
        addFragmentAgentInformation();
        addFragmentSaveSearchDetail();
        mFragmentDiscover.showLayout();
    }

    private void getNotificationCount() {
        NotificationsPresenter mNotificationsPresenter = new NotificationsPresenter(this);
        mNotificationsPresenter.getNotification("all", "1", "50", false);
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
    }

    @Override
    public void getNotificationsFail(@StringRes int message) {
        Log.e(TAG, "getNotificationsFail: serverError");
    }

    @Override
    public void loginSuccess() {
        HomeCaravanApplication.mLoginSocketSuccess = true;
    }

    @Override
    public void loginFail() {
        HomeCaravanApplication.mLoginSocketSuccess = false;
    }

    private void loginSocket() {
        if (HomeCaravanApplication.isNetAvailable(this) && !HomeCaravanApplication.mLoginSocketSuccess) {
            if (ConsumerUser.getInstance().getData().getPnUID() == null || ConsumerUser.getInstance().getData().getPnUID().isEmpty()) {
                HomeCaravanApplication.mLoginSocketSuccess = false;
            } else {
                Log.e(TAG, "loginSocket-ID: " + ConsumerUser.getInstance().getData().getPnUID());
                mLoginPresenter.login(ConsumerUser.getInstance().getData().getPnUID());
            }
        }
    }

    @Override
    public void addFavoriteSuccess(boolean b) {
        EventBus.getDefault().post(new EventFavored(mFavoriteId, b));
    }

    @Override
    public void addFavoriteFail() {

    }

    private void socketListening() {
        List<String> mArr = null;
        String serialized = mPrefs.getString(Constants.getInstance().THREAD_ID_TURN_OFF_NOTIFICATION_LIST, null);
        if (serialized != null && !serialized.isEmpty()) {
            mArr = Arrays.asList(TextUtils.split(serialized, ","));
        }
        final List<String> mArrThreadIdTurnOffNotification = mArr;

        HomeCaravanApplication.mSocket.on(Constants.getInstance().THREAD, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.e(TAG, "Main socketListening: " + args[0].toString());
                final JSONObject data = (JSONObject) args[0];
                String key, command;
                try {
                    key = data.getString(Constants.getInstance().MESSAGE_KEY);
                    command = data.getString(Constants.getInstance().MESSAGE_COMMAND);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }

                //Receiver message
                if (command.equals(Constants.getInstance().MESSAGE_COMMAND_ADD)
                        && key.equals(Constants.getInstance().MESSAGE_MESSAGE)) {

                    //turn off all notification message
                    if (!HomeCaravanApplication.mReceiverMessageNotification) {
                        return;
                    }

                    final MessageAddResponse messageAddResponse = new Gson().fromJson(args[0].toString(), MessageAddResponse.class);
                    if (messageAddResponse != null) {
                        final String mCurrentThreadId = messageAddResponse.getMessageItem().getMessageThread().getId();
                        //My message send
                        if (messageAddResponse.getMessageItem().getMessageThreadView().getId()
                                .equals(ConsumerUser.getInstance().getData().getPnUID())) {
                            return;
                        }

                        //turn off notification thread of ThreadTurnOff List
                        if (mArrThreadIdTurnOffNotification != null) {
                            for (String threadTurnOff : mArrThreadIdTurnOffNotification) {
                                if (mCurrentThreadId.equals(threadTurnOff)) {
                                    return;
                                }
                            }
                        }

                        //Do not notify sent thread
                        for (String threadId : SkeletonNewMessage.getInstance().getData()) {
                            if (mCurrentThreadId.equals(threadId)) {
                                return;
                            }
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e(TAG, "Main socketListening: new message from " + messageAddResponse.getMessageItem().getMessageThreadView().getName());
                                showSnackBar(mLayoutMainActivity, TypeDialog.MESSAGES,
                                        "You have new message from " + messageAddResponse.getMessageItem().getMessageThreadView().getName(), "MessageNotifications");

                                SkeletonNewMessage.getInstance().getData().add(mCurrentThreadId);

                                mMenuFragmentConsumer.setNewMessageCount(SkeletonNewMessage.getInstance().getData().size());
                            }
                        });
                    }
                }
            }
        });
    }
}