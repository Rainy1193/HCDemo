package com.homecaravan.android.consumer.consumershedule;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.activity.BookSingleActivity;
import com.homecaravan.android.consumer.activity.MainActivityConsumer;
import com.homecaravan.android.consumer.adapter.ScheduleAdapter;
import com.homecaravan.android.consumer.adapter.ScheduleConsumerAdapter;
import com.homecaravan.android.consumer.adapter.SelectAdapter;
import com.homecaravan.android.consumer.adapter.ViewPagerAdapter;
import com.homecaravan.android.consumer.api.CaravanApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.consumermvp.caravanmvp.SaveCaravanPresenter;
import com.homecaravan.android.consumer.consumermvp.caravanmvp.SaveCaravanView;
import com.homecaravan.android.consumer.consumermvp.listingmvp.GetListingDetailPresenter;
import com.homecaravan.android.consumer.consumermvp.listingmvp.GetListingDetailView;
import com.homecaravan.android.consumer.consumermvp.searchmvp.GetListSearchPresenter;
import com.homecaravan.android.consumer.consumermvp.searchmvp.GetListSearchView;
import com.homecaravan.android.consumer.listener.IPageChange;
import com.homecaravan.android.consumer.listener.IScheduleListener;
import com.homecaravan.android.consumer.listener.ISchedulePickListener;
import com.homecaravan.android.consumer.listener.ISchedulePropertyListener;
import com.homecaravan.android.consumer.listener.ISelectListener;
import com.homecaravan.android.consumer.model.CaravanQueue;
import com.homecaravan.android.consumer.model.ConsumerListingSchedule;
import com.homecaravan.android.consumer.model.CurrentCaravan;
import com.homecaravan.android.consumer.model.CurrentListingSchedule;
import com.homecaravan.android.consumer.model.EventDeleteSearch;
import com.homecaravan.android.consumer.model.EventFavored;
import com.homecaravan.android.consumer.model.EventNewSaveSearch;
import com.homecaravan.android.consumer.model.EventQueue;
import com.homecaravan.android.consumer.model.EventRequestShowing;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.model.responseapi.Listing;
import com.homecaravan.android.consumer.model.responseapi.ResponseAllSearch;
import com.homecaravan.android.consumer.model.responseapi.ResponseCaravan;
import com.homecaravan.android.consumer.model.responseapi.ResponseSaveCaravan;
import com.homecaravan.android.consumer.model.responseapi.Search;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.homecaravan.android.consumer.utils.Utils;
import com.homecaravan.android.consumer.widget.CustomLayoutManager;
import com.homecaravan.android.consumer.widget.CustomTabLayout;
import com.homecaravan.android.consumer.widget.CustomViewPager;
import com.homecaravan.android.models.ItemSelect;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSchedule extends BaseFragment implements ISchedulePropertyListener, ISchedulePickListener,
        ISelectListener, IScheduleListener, GetListSearchView, SaveCaravanView, GetListingDetailView {

    private FragmentStepInviteOther mFragmentInviteOther;
    private FragmentStepReviewAndSubmit mFragmentReviewAndSubmit;
    private FragmentStepRoute mFragmentRoute;
    private FragmentStepScheduleShowing mFragmentScheduleShowing;
    private FragmentStepSelectAgent mFragmentSelectAgent;
    private GetListingDetailPresenter mGetListingDetailPresenter;
    private boolean mIsShowStepSchedule;
    private ScheduleAdapter mScheduleAdapter;
    private ViewPagerAdapter mSavedSearchAdapter;
    private IPageChange mPageChange;
    private GetListSearchPresenter mGetListSearchPresenter;
    private SaveCaravanPresenter mSaveCaravanPresenter;
    private ArrayList<FragmentPageSchedule> mPageSchedule = new ArrayList<>();
    private ArrayList<Listing> mArrListing = new ArrayList<>();
    private ArrayList<Search> mArrSearch = new ArrayList<>();
    private ScheduleConsumerAdapter mAdapter;
    private boolean mInit;
    private int mCountSchedule = 0;
    private int mPageFavorite = 1;
    private String mStartHour;
    private String mStartMin;
    private String mStartHalf;

    private ArrayList<ItemSelect> mArrHour = new ArrayList<>();
    private ArrayList<ItemSelect> mArrMin = new ArrayList<>();
    private ArrayList<ItemSelect> mArrAmPm = new ArrayList<>();
    private ArrayList<ItemSelect> mArrDuration = new ArrayList<>();

    private SelectAdapter mHourAdapter;
    private SelectAdapter mMinAdapter;
    private SelectAdapter mAmPmAdapter;
    private SelectAdapter mDurationAdapter;

    private CustomLayoutManager mHourLayoutManager;
    private CustomLayoutManager mMinLayoutManager;
    private CustomLayoutManager mAmPmLayoutManager;
    private CustomLayoutManager mDurationLayoutManager;

    private int mCurrentStep = 0;
    private int mOldHour = 0;
    private int mCurrentHour = 0;
    private int mOldMin = 0;
    private int mCurrentMin = 0;
    private int mOldAmPm = 0;
    private int mCurrentAmPm = 0;
    private int mCurrentDuration = 0;
    private int mPage = 1;
    private int mTotalSearch = 0;
    private boolean mLoadMoreSearch;
    private boolean mIsEnd;
    private boolean mUpdate;
    private boolean mAddFavorite;

    @Bind(R.id.tvNeedSelectDay)
    TextView mNeedSelectDay;
    @Bind(R.id.tvNeedSelectAgent)
    TextView mNeedSelectAgent;
    @Bind(R.id.layoutNext)
    LinearLayout mNextStep;
    @Bind(R.id.layoutSubmit)
    LinearLayout mSubmit;
    @Bind(R.id.iv)
    ImageView mIv;
    @Bind(R.id.rvHour)
    RecyclerView mRvHour;
    @Bind(R.id.rvMin)
    RecyclerView mRvMin;
    @Bind(R.id.rvAmPm)
    RecyclerView mRvAmPm;
    @Bind(R.id.rvDuration)
    RecyclerView mRvDuration;
    @Bind(R.id.layoutStepSchedule)
    RelativeLayout mLayoutStepSchedule;
    @Bind(R.id.tvNoItem)
    TextView mTvNoItem;
    @Bind(R.id.rvSchedule)
    RecyclerView mRvSchedule;
    @Bind(R.id.layoutSchedule)
    LinearLayout mLayoutSchedule;
    @Bind(R.id.layoutScheduleBg)
    RelativeLayout mLayoutScheduleBg;
    @Bind(R.id.totalSchedule)
    FrameLayout mLayoutTotalSchedule;
    @Bind(R.id.tvNumSchedule)
    TextView mNumSchedule;
    @Bind(R.id.vpSavedSearch)
    ViewPager mViewPagerSavedSearch;
    @Bind(R.id.vpSchedule)
    CustomViewPager mViewPagerSchedule;
    @Bind(R.id.tabLayout)
    CustomTabLayout mTabLayout;
    @Bind(R.id.layoutOpenSchedule)
    LinearLayout mLayoutOpenSchedule;
    @Bind(R.id.layoutChangeTime)
    RelativeLayout mLayoutChangeTime;
    @Bind(R.id.layoutChangeTimeContent)
    LinearLayout mLayoutChangeTimeContent;
    @Bind(R.id.layoutMain)
    RelativeLayout mLayoutMain;

    @OnClick(R.id.layoutDone)
    public void onLayoutDone() {
        mFragmentRoute.updateTimeRoute();
        mLayoutChangeTime.setVisibility(View.GONE);
        AnimUtils.hideViewFromBottom(mLayoutChangeTimeContent);
    }

    @OnClick(R.id.layoutChangeTime)
    public void onLayoutChangeTime() {
        mLayoutChangeTime.setVisibility(View.GONE);
        AnimUtils.hideViewFromBottom(mLayoutChangeTimeContent);
    }

    @OnClick(R.id.tvBack)
    public void backStep() {
        mCurrentStep--;
        if (mCurrentStep < 0) {
            if (mFragmentScheduleShowing.isPickDay()) {
                mCurrentStep++;
                mFragmentScheduleShowing.backPickDay();
            } else {
                closeStepSchedule(true);
            }
            return;
        }
        if (mCurrentStep == 0) {
            mViewPagerSchedule.setCurrentItem(0);
            return;
        }
        if (mCurrentStep == 1) {
            mViewPagerSchedule.setCurrentItem(1);
            return;
        }
        if (mCurrentStep == 2) {
            if (mFragmentSelectAgent.isShowExclusive()) {
                mFragmentSelectAgent.hideExclusiveAgent();
                mCurrentStep++;
            } else {
                mNeedSelectDay.setVisibility(View.GONE);
                mNeedSelectAgent.setVisibility(View.GONE);
                mNextStep.setVisibility(View.VISIBLE);
                mViewPagerSchedule.setCurrentItem(2);
            }
            return;
        }
        if (mCurrentStep == 3) {
            mViewPagerSchedule.setCurrentItem(3);
            mNextStep.setVisibility(View.VISIBLE);
            mSubmit.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.layoutNext)
    public void nextStep() {
        mCurrentStep++;
        if (mCurrentStep == 1) {
            mFragmentScheduleShowing.createCaravanFromQueue();
            //mViewPagerSchedule.setCurrentItem(1);
//            mFragmentRoute.setUpMapAndListRoute(mStartHour, mStartMin, mStartHalf);
            return;
        }
        if (mCurrentStep == 2) {
//            try {
//                mSaveCaravanPresenter.saveCaravan("", Utils.createDestination(CurrentListingSchedule.getInstance().getArrListing()));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            mViewPagerSchedule.setCurrentItem(2);
            return;
        }
        if (mCurrentStep == 3) {
            mNeedSelectDay.setVisibility(View.GONE);
            mNeedSelectAgent.setVisibility(View.VISIBLE);
            mNextStep.setVisibility(View.GONE);
            mFragmentSelectAgent.hideExclusiveAgent();
            mViewPagerSchedule.setCurrentItem(3);
            return;
        }
        if (mCurrentStep == 4) {
            mFragmentReviewAndSubmit.setUpListAndMap();
            mViewPagerSchedule.setCurrentItem(4);
            if (mFragmentSelectAgent.getAgent() != null) {
                mFragmentReviewAndSubmit.initAgent(mFragmentSelectAgent.getAgent().getName(), mFragmentSelectAgent.getAgent().getAvatar(), true);
            } else {
                mFragmentReviewAndSubmit.initAgent("", 0, false);
            }
            mSubmit.setVisibility(View.VISIBLE);
            mNextStep.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.layoutSubmit)
    public void submit() {
        try {
            showLoading();
            mSaveCaravanPresenter.saveCaravan(CurrentCaravan.getInstance().getData().getId(), Utils.createDestination(CurrentListingSchedule.getInstance().getArrListing()));
        } catch (Exception e) {
            hideLoading();
            showSnackBar(mLayoutMain, TypeDialog.ERROR, R.string.error_server, "error");
        }
    }

    @OnClick(R.id.layoutScheduleBg)
    public void onLayoutScheduleBg() {
        if (mLayoutScheduleBg.getVisibility() == View.VISIBLE) {
            mLayoutScheduleBg.setVisibility(View.GONE);
            AnimUtils.slideDown(getActivity(), mLayoutSchedule);
        }
    }

    @OnClick(R.id.totalSchedule)
    public void onLayoutTotalSchedule() {
        mLayoutScheduleBg.setVisibility(View.VISIBLE);
        mLayoutSchedule.setVisibility(View.VISIBLE);
        AnimUtils.slideUp(getActivity(), mLayoutSchedule);
    }


    @OnClick(R.id.layoutOpenSchedule)
    public void onLayoutOpenSchedule() {
        if (mArrListing.size() == 1) {
            Intent intent = new Intent(getContext(), BookSingleActivity.class);
            intent.putExtra("id", mArrListing.get(0).getId());
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
        } else {
            mUpdate = false;
            openLayoutCreateCaravan();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGetListSearchPresenter = new GetListSearchPresenter(this);
        mSaveCaravanPresenter = new SaveCaravanPresenter(this);
        mGetListingDetailPresenter = new GetListingDetailPresenter(this);
        mSavedSearchAdapter = new ViewPagerAdapter(getChildFragmentManager());
        mViewPagerSavedSearch.setAdapter(mSavedSearchAdapter);
        mTabLayout.setupWithViewPager(mViewPagerSavedSearch, true);
        mTabLayout.setSmoothScrollingEnabled(true);
        mViewPagerSavedSearch.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                FragmentPageSchedule pageSchedule = (FragmentPageSchedule) mSavedSearchAdapter.getFragment(position);
                pageSchedule.setAdapter();
                if (position + 1 == mTotalSearch && !mLoadMoreSearch && !mIsEnd) {
                    mPage++;
                    mLoadMoreSearch = true;
                    mGetListSearchPresenter.getListSearch(String.valueOf(mPage), "", "", "");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setUpDataSelect();
        setUpRvSelect();
    }


    public void addListingFromQueue() {
        for (int i = 0; i < CaravanQueue.getInstance().getListings().size(); i++) {
            mArrListing.add(CaravanQueue.getInstance().getListings().get(i));
        }
        mAdapter = new ScheduleConsumerAdapter(getActivity(), mArrListing, this);
        mRvSchedule.setAdapter(mAdapter);
        mRvSchedule.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        if (mArrListing.size() != 0) {
            mRvSchedule.setVisibility(View.VISIBLE);
            mTvNoItem.setVisibility(View.INVISIBLE);
            mLayoutOpenSchedule.setVisibility(View.VISIBLE);
            mCountSchedule = mArrListing.size();
            mNumSchedule.setVisibility(View.VISIBLE);
            mNumSchedule.setText(String.valueOf(mCountSchedule));
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_schedule;
    }

    public void setPageChange(IPageChange mPageChange) {
        this.mPageChange = mPageChange;
    }

    public void resetFragment() {
        if (mIsShowStepSchedule) {
            closeStepSchedule(true);
        }
    }

    public void initViewPager() {
        mScheduleAdapter = new ScheduleAdapter(getChildFragmentManager());
        mFragmentScheduleShowing = new FragmentStepScheduleShowing();
        mFragmentScheduleShowing.setPickListener(this);
        if (mUpdate) {
            mFragmentScheduleShowing.setCreatedCaravan(true);
            mFragmentScheduleShowing.setUpdate(true);
        }
        mFragmentScheduleShowing.setListener(this);
        mScheduleAdapter.addFragment(mFragmentScheduleShowing);
        mFragmentRoute = new FragmentStepRoute();
        if (mUpdate) {
            mFragmentRoute.setUpdate(true);
        }
        mFragmentRoute.setListener(this);
        mScheduleAdapter.addFragment(mFragmentRoute);
        mFragmentInviteOther = new FragmentStepInviteOther();
        mScheduleAdapter.addFragment(mFragmentInviteOther);
        mFragmentSelectAgent = new FragmentStepSelectAgent();
        mFragmentSelectAgent.setListener(this);
        mScheduleAdapter.addFragment(mFragmentSelectAgent);
        mFragmentReviewAndSubmit = new FragmentStepReviewAndSubmit();
        mFragmentReviewAndSubmit.setListener(this);
        mScheduleAdapter.addFragment(mFragmentReviewAndSubmit);
        mViewPagerSchedule.setAdapter(mScheduleAdapter);
        mViewPagerSchedule.setOffscreenPageLimit(5);
    }


    public void initSchedule() {
        if (!mInit) {
            addListingFromQueue();
//            FragmentPageSchedule pageSchedule = new FragmentPageSchedule();
//            pageSchedule.setListener(FragmentSchedule.this);
//            pageSchedule.setSearchId("Favorite");
//            mPageSchedule.add(pageSchedule);
//            pageSchedule.setFavorite(true);
//            mSavedSearchAdapter.addFragment(pageSchedule, "Favorite");
//            mViewPagerSavedSearch.getAdapter().notifyDataSetChanged();
//            pageSchedule.setAdapter();
//            Search search = new Search();
//            search.setId("Favorite");
//            mArrSearch.add(search);
            mGetListSearchPresenter.getListSearch(String.valueOf(mPage), "", "", "");
            mInit = true;
        }
    }

    @Override
    public void pickSchedule(Listing listing) {
        mCountSchedule++;
        mNumSchedule.setVisibility(View.VISIBLE);
        AnimUtils.scaleView(mIv, 1f, 1.1f, 1f, 1.1f);
        mNumSchedule.setText(String.valueOf(mCountSchedule));
        updateSchedule(listing.getId(), true);
        mArrListing.add(listing);
        ConsumerListingSchedule listingSchedule = new ConsumerListingSchedule();
        listingSchedule.setListing(listing);
        CurrentListingSchedule.getInstance().getArrListing().add(listingSchedule);
        mAdapter.notifyItemInserted(mAdapter.getItemCount());
        mAdapter.notifyDataSetChanged();
        mLayoutOpenSchedule.setVisibility(View.VISIBLE);
        mRvSchedule.setVisibility(View.VISIBLE);
        mTvNoItem.setVisibility(View.INVISIBLE);
        EventBus.getDefault().post(new EventQueue(true, listing.getId(), listing, null, null));
    }

    @Override
    public void removeSchedule(Listing listing, int position) {
        mCountSchedule--;
        if (mCountSchedule == 0) {
            mNumSchedule.setVisibility(View.INVISIBLE);
            mLayoutOpenSchedule.setVisibility(View.INVISIBLE);
            mRvSchedule.setVisibility(View.INVISIBLE);
            mTvNoItem.setVisibility(View.VISIBLE);
        } else {
            mNumSchedule.setText(String.valueOf(mCountSchedule));
        }
        mArrListing.remove(position);
        mAdapter.notifyDataSetChanged();
        ArrayList<ConsumerListingSchedule> arrListingSchedule = CurrentListingSchedule.getInstance().getArrListing();
        for (int i = 0; i < arrListingSchedule.size(); i++) {
            if (arrListingSchedule.get(i).getListing().getId().equalsIgnoreCase(listing.getId())) {
                arrListingSchedule.remove(i);
                break;
            }
        }
        updateSchedule(listing.getId(), false);
        EventBus.getDefault().post(new EventQueue(false, listing.getId(), listing, null, null));
    }

    public void setUpDataSelect() {
        for (int i = 1; i <= 12; i++) {
            ItemSelect itemSelect = new ItemSelect();
            itemSelect.setPosition(0);
            if (i == 1) {
                itemSelect.setShowViewTop(true);
                itemSelect.setSelect(true);
            }
            if (i < 10) {
                itemSelect.setValue("0" + String.valueOf(i));
            } else {
                itemSelect.setValue(String.valueOf(i));
            }
            mArrHour.add(itemSelect);
        }
        for (int i = 0; i < 12; i++) {
            ItemSelect itemSelect = new ItemSelect();
            itemSelect.setPosition(1);
            if (i == 0) {
                itemSelect.setShowViewTop(true);
                itemSelect.setSelect(true);
            }
            if (i < 2) {
                itemSelect.setValue("0" + String.valueOf(i * 5));
            } else {
                itemSelect.setValue(String.valueOf(i * 5));
            }
            mArrMin.add(itemSelect);
        }
        for (int i = 1; i <= 24; i++) {
            ItemSelect itemSelect = new ItemSelect();
            itemSelect.setPosition(1);
            if (i == 1) {
                itemSelect.setShowViewTop(true);
                itemSelect.setSelect(true);
            }
            if (i < 2) {
                itemSelect.setValue("0" + String.valueOf(i * 5) + " mins");
            } else {
                itemSelect.setValue(String.valueOf(i * 5) + " mins");
            }
            mArrDuration.add(itemSelect);
        }

        ItemSelect itemSelect = new ItemSelect();
        itemSelect.setPosition(2);
        itemSelect.setSelect(true);
        itemSelect.setShowViewTop(true);
        itemSelect.setValue("AM");
        mArrAmPm.add(itemSelect);

        ItemSelect itemSelect1 = new ItemSelect();
        itemSelect1.setPosition(2);
        itemSelect1.setSelect(false);
        itemSelect1.setValue("PM");
        mArrAmPm.add(itemSelect1);
    }

    public void setUpRvSelect() {
        mHourAdapter = new SelectAdapter(mArrHour, getActivity(), this);
        mMinAdapter = new SelectAdapter(mArrMin, getActivity(), this);
        mAmPmAdapter = new SelectAdapter(mArrAmPm, getActivity(), this);
        mDurationAdapter = new SelectAdapter(mArrDuration, getActivity(), this);

        mHourLayoutManager = new CustomLayoutManager(CustomLayoutManager.VERTICAL);
        mMinLayoutManager = new CustomLayoutManager(CustomLayoutManager.VERTICAL);
        mAmPmLayoutManager = new CustomLayoutManager(CustomLayoutManager.VERTICAL);
        mDurationLayoutManager = new CustomLayoutManager(CustomLayoutManager.VERTICAL);

        mHourLayoutManager.attach(mRvHour);
        mMinLayoutManager.attach(mRvMin);
        mAmPmLayoutManager.attach(mRvAmPm);
        mDurationLayoutManager.attach(mRvDuration);


        mHourLayoutManager.setCallbackInFling(true);
        mHourLayoutManager.setOnItemSelectedListener(new CustomLayoutManager.OnItemSelectedListener() {
            @Override
            public void onItemSelected(RecyclerView recyclerView, View item, int position) {
                mCurrentHour = position;
                Log.e("mArrHour", mArrHour.get(mCurrentHour).getValue());
                if (mFragmentRoute != null) {
                    mFragmentRoute.updateHourRoute(mArrHour.get(mCurrentHour).getValue());
                }
            }
        });

        mMinLayoutManager.setCallbackInFling(true);
        mMinLayoutManager.setOnItemSelectedListener(new CustomLayoutManager.OnItemSelectedListener() {
            @Override
            public void onItemSelected(RecyclerView recyclerView, View item, int position) {
                mCurrentMin = position;
                Log.e("mArrMin", mArrMin.get(mCurrentMin).getValue());
                if (mFragmentRoute != null) {
                    mFragmentRoute.updateMinRoute(mArrMin.get(mCurrentMin).getValue());
                }
            }
        });

        mAmPmLayoutManager.setCallbackInFling(true);
        mAmPmLayoutManager.setOnItemSelectedListener(new CustomLayoutManager.OnItemSelectedListener() {
            @Override
            public void onItemSelected(RecyclerView recyclerView, View item, int position) {
                mCurrentAmPm = position;
                Log.e("mCurrentAmPm", mArrAmPm.get(mCurrentAmPm).getValue());
                if (mFragmentRoute != null) {
                    mFragmentRoute.updateHaftRoute(mArrAmPm.get(mCurrentAmPm).getValue());
                }
            }
        });

        mDurationLayoutManager.setCallbackInFling(true);
        mDurationLayoutManager.setOnItemSelectedListener(new CustomLayoutManager.OnItemSelectedListener() {
            @Override
            public void onItemSelected(RecyclerView recyclerView, View item, int position) {
                mCurrentDuration = position;
                Log.e("mCurrentDuration", mArrDuration.get(mCurrentDuration).getValue());
                if (mFragmentRoute != null) {
                    mFragmentRoute.updateDurationRoute(mArrDuration.get(mCurrentDuration).getValue());
                }
            }
        });

        mRvHour.setAdapter(mHourAdapter);
        mRvMin.setAdapter(mMinAdapter);
        mRvAmPm.setAdapter(mAmPmAdapter);
        mRvDuration.setAdapter(mDurationAdapter);
        //scrollTimePick();
    }


    public void updateSchedule(String listingId, boolean b) {
        for (int i = 0; i < mPageSchedule.size(); i++) {
            mPageSchedule.get(i).updateAdapter(listingId, b);
        }
    }

    public boolean isShowStepSchedule() {
        return mIsShowStepSchedule;
    }


    public void closeStepSchedule(boolean animation) {
        ObjectAnimator objectAnimator;
        if (animation) {
            objectAnimator = AnimUtils.animationButtonMenuWithCloseViewListener(MainActivityConsumer.mDrawerArrowDrawable, mLayoutStepSchedule, true);
        } else {
            objectAnimator = AnimUtils.animationButtonMenuWithCloseViewListener(null, mLayoutStepSchedule, true);
        }
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mViewPagerSchedule.removeAllViews();
                mLayoutStepSchedule.setVisibility(View.GONE);
                mIsShowStepSchedule = false;
                mSubmit.setVisibility(View.GONE);
                mNextStep.setVisibility(View.GONE);
                mNeedSelectDay.setVisibility(View.VISIBLE);
                mNeedSelectAgent.setVisibility(View.GONE);
                onLayoutScheduleBg();
                mUpdate = false;
                CurrentListingSchedule.getInstance().getArrListing().clear();
                CurrentListingSchedule.getInstance().getAgentSchedule().clear();
                ArrayList<Listing> listings = CaravanQueue.getInstance().getListings();
                for (int i = 0; i < listings.size(); i++) {
                    ConsumerListingSchedule listingSchedule = new ConsumerListingSchedule();
                    listingSchedule.setListing(listings.get(i));
                    CurrentListingSchedule.getInstance().getArrListing().add(listingSchedule);
                }
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

    @Override
    public void hourPick(String hour) {
        mStartHour = hour;
    }

    @Override
    public void minPick(String min) {
        mStartMin = min;
    }

    @Override
    public void halfPick(String half) {
        mStartHalf = half;
    }

    @Override
    public void selectHour(int position) {

    }

    @Override
    public void selectMin(int position) {

    }

    @Override
    public void selectAmPm(int position) {

    }

    @Override
    public void selectDuration(int duration) {

    }

    @Override
    public void openPickTimeRoute(final int hour, final int min, final String haft, final String duration) {

        Log.e("pickhour", String.valueOf(hour));
        Log.e("pickmin", String.valueOf(min));
        Log.e("pickhaft", String.valueOf(haft));
        Log.e("pickduration", String.valueOf(duration));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mHourLayoutManager.setNoAnimationScroll(true);
                for (int i = 0; i < mArrHour.size(); i++) {
                    if (Integer.parseInt(mArrHour.get(i).getValue()) == hour) {
                        mRvHour.smoothScrollToPosition(i);
                        break;
                    }
                }
            }
        }, 50);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mMinLayoutManager.setNoAnimationScroll(true);
                for (int i = 0; i < mArrMin.size(); i++) {
                    if (Integer.parseInt(mArrMin.get(i).getValue()) > (min - 5)) {
                        mRvMin.smoothScrollToPosition(i);
                        break;
                    }
                }
            }
        }, 50);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAmPmLayoutManager.setNoAnimationScroll(true);
                for (int i = 0; i < mArrAmPm.size(); i++) {
                    if (mArrAmPm.get(i).getValue().equalsIgnoreCase(haft)) {
                        mRvAmPm.smoothScrollToPosition(i);
                        break;
                    }
                }
            }
        }, 50);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDurationLayoutManager.setNoAnimationScroll(true);
                for (int i = 0; i < mArrDuration.size(); i++) {
                    if (mArrDuration.get(i).getValue().equalsIgnoreCase(duration)) {
                        mRvDuration.smoothScrollToPosition(i);
                        break;
                    }
                }

            }
        }, 50);
        mLayoutChangeTime.setVisibility(View.VISIBLE);
        AnimUtils.showViewFromBottom(mLayoutChangeTimeContent);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mLayoutChangeTime.setVisibility(View.VISIBLE);
//            }
//        }, 150);

    }


    @Override
    public void updateListAfterSwap(int posFrom, int posTo) {
        mFragmentScheduleShowing.updateListAfterSwap(posFrom, posTo);

    }

    @Override
    public void showNext() {
        mNeedSelectDay.setVisibility(View.GONE);
        mNextStep.setVisibility(View.VISIBLE);
    }

    @Override
    public void createCaravanSuccess(boolean clearData) {
        mViewPagerSchedule.setCurrentItem(1);
        mFragmentRoute.setUpMapAndListRoute(mStartHour, mStartMin, mStartHalf);
        if (clearData) {
            clearQueue();
        }
    }

    @Override
    public void createCaravanFail() {
        mCurrentStep--;
    }

    @Override
    public void disableButton() {
        mNextStep.setClickable(false);
    }

    @Override
    public void enableButton() {
        mNextStep.setClickable(true);
    }

    @Override
    public void showNextWhenSelectAgent(boolean b) {
        if (b) {
            mNeedSelectAgent.setVisibility(View.GONE);
            mNextStep.setVisibility(View.VISIBLE);
        } else {
            mNeedSelectAgent.setVisibility(View.VISIBLE);
            mNextStep.setVisibility(View.GONE);
        }
    }

    @Override
    public void backSelectAgent() {
        backStep();
    }

    @Override
    public void getListSearchSuccess(final ResponseAllSearch.ResponseAllSearchData searchData) {
        ArrayList<Search> arrSearch = searchData.getArrSearch();
        if (arrSearch.size() < 10) {
            mIsEnd = true;
        } else {
            mIsEnd = false;
        }
        int count = 0;
        mLoadMoreSearch = false;
        mTotalSearch = arrSearch.size() + mTotalSearch;
        mInit = true;

        if (!mAddFavorite) {
            FragmentPageSchedule pageFavorite = new FragmentPageSchedule();
            pageFavorite.setListener(FragmentSchedule.this);
            pageFavorite.setSearchId("Favorite");
            mPageSchedule.add(pageFavorite);
            pageFavorite.setFavorite(true);
            mSavedSearchAdapter.addFragment(pageFavorite, "Favorite");
            Search search = new Search();
            search.setId("Favorite");
            mArrSearch.add(search);
            mTotalSearch++;
        }

        for (int i = 0; i < arrSearch.size(); i++) {
            for (int j = 0; j < mArrSearch.size(); j++) {
                if (!arrSearch.get(i).getId().equalsIgnoreCase(mArrSearch.get(j).getId())) {
                    count++;
                }
            }
            if (count == mArrSearch.size()) {
                FragmentPageSchedule pageSchedule = new FragmentPageSchedule();
                pageSchedule.setListener(FragmentSchedule.this);
                pageSchedule.setSearchId(arrSearch.get(i).getId());
                mPageSchedule.add(pageSchedule);
                mSavedSearchAdapter.addFragment(pageSchedule, Utils.handlerNameSearch(arrSearch.get(i).getName()));
                mArrSearch.add(arrSearch.get(i));
            }
            count = 0;
        }

        mViewPagerSavedSearch.getAdapter().notifyDataSetChanged();
        mViewPagerSavedSearch.setOffscreenPageLimit(mViewPagerSavedSearch.getAdapter().getCount());
        if (mSavedSearchAdapter.getCount() != 0) {
            if (mSavedSearchAdapter.getCount() <= 10) {
                FragmentPageSchedule pageSchedule = (FragmentPageSchedule) mSavedSearchAdapter.getFragment(mTotalSearch - arrSearch.size());
                pageSchedule.setAdapter();
                mViewPagerSavedSearch.setCurrentItem(mTotalSearch - arrSearch.size(), true);
            }
        }
        if (!mAddFavorite) {
            FragmentPageSchedule pageSchedule = (FragmentPageSchedule) mSavedSearchAdapter.getFragment(0);
            pageSchedule.setAdapter();
            mViewPagerSavedSearch.setCurrentItem(0, true);
            mAddFavorite = true;
        }
    }

    @Override
    public void getListSearchFail(String message) {
        mLoadMoreSearch = false;
        if (MainActivityConsumer.mIndexPage == 3) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "getListSearchFail");
        }
    }

    @Override
    public void getListSearchFail(@StringRes int message) {
        mLoadMoreSearch = false;
        if (MainActivityConsumer.mIndexPage == 3) {
            showSnackBar(mLayoutMain, TypeDialog.ERROR, message, "getListSearchFail");
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }


    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventListingDetail(EventNewSaveSearch newSearch) {
        if (mIsEnd) {
            mGetListSearchPresenter.getListSearch(String.valueOf(mPage), "", "", "");
        }
    }

    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventDeleteSearch(EventDeleteSearch search) {
        if (mIsEnd) {
            for (int i = 0; i < mPageSchedule.size(); i++) {
                if (!mPageSchedule.get(i).isFavorite() && mPageSchedule.get(i).getSearchId().equalsIgnoreCase(search.id)) {
                    mPageSchedule.remove(i);
                    break;
                }
            }
        }
    }

    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventRequestShowing(EventRequestShowing requestShowing) {
        mCountSchedule++;
        mNumSchedule.setVisibility(View.VISIBLE);
        AnimUtils.scaleView(mIv, 1f, 1.1f, 1f, 1.1f);
        mNumSchedule.setText(String.valueOf(mCountSchedule));
        mLayoutOpenSchedule.setVisibility(View.VISIBLE);
        mRvSchedule.setVisibility(View.VISIBLE);
        mTvNoItem.setVisibility(View.INVISIBLE);
        if (mInit) {
            mGetListingDetailPresenter.getListingDetail(requestShowing.getId());
        }
    }

    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventFavorite(EventFavored favorite) {
        if (mInit) {
            FragmentPageSchedule pageSchedule = (FragmentPageSchedule) mSavedSearchAdapter.getFragment(0);
            pageSchedule.resetAdapter();
        }
    }

    @Override
    public void saveCaravanSuccess(ResponseSaveCaravan caravan) {
        CurrentCaravan.getInstance().getData().setJson(caravan.getCaravanJsons());
        hideLoading();
        mSubmit.setVisibility(View.GONE);
        mPageChange.submitSchedule(caravan.getCaravanJsons().get(0).getTimeFrom());
        mLayoutScheduleBg.setVisibility(View.GONE);
        AnimUtils.slideDown(getActivity(), mLayoutSchedule);
        closeStepSchedule(true);
    }

    @Override
    public void saveCaravanFail(@StringRes int message) {
        hideLoading();
        showSnackBar(mLayoutMain, TypeDialog.ERROR, message, "saveCaravan");
    }

    @Override
    public void saveCaravanFail(String message) {
        hideLoading();
        showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "saveCaravan");
    }

    public void editCaravan() {
        initSchedule();
        CaravanApi caravanApi = ServiceGeneratorConsumer.createService(CaravanApi.class);
        caravanApi.getCaravanDetail(CurrentCaravan.getInstance().getData().getId()).enqueue(new Callback<ResponseCaravan>() {
            @Override
            public void onResponse(Call<ResponseCaravan> call, Response<ResponseCaravan> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        CurrentCaravan.getInstance().setData(response.body().getData());
                        CurrentListingSchedule.getInstance().getArrListing().clear();
                        CurrentListingSchedule.getInstance().getAgentSchedule().clear();
                        String startTime = CurrentCaravan.getCaravan().getData().getStartingTime().getData();
                        CurrentListingSchedule.getInstance().setDay(Utils.getSingleDay(Utils.createDateFromString(startTime)));
                        CurrentListingSchedule.getInstance().setMonth(Utils.getSingleMonth(Utils.createDateFromString(startTime)));
                        CurrentListingSchedule.getInstance().setYear(Utils.getSingleYear(Utils.createDateFromString(startTime)));
                        CurrentListingSchedule.getInstance().setHour(Utils.getSingleHour(Utils.createDateFromString(startTime)));
                        CurrentListingSchedule.getInstance().setMin(Utils.getSingleMin(Utils.createDateFromString(startTime)));
                        CurrentListingSchedule.getInstance().setHalf(Utils.getSingleHalf(Utils.createDateFromString(startTime)));
                        ArrayList<ResponseCaravan.CaravanDestinations> destinations = CurrentCaravan.getCaravan().getData().getDestinations();
                        for (int i = 0; i < destinations.size(); i++) {
                            ConsumerListingSchedule listingSchedule = new ConsumerListingSchedule();
                            listingSchedule.setListing(destinations.get(i).getListing());
                            CurrentListingSchedule.getInstance().getArrListing().add(listingSchedule);
                        }
                        mUpdate = true;
                        openLayoutCreateCaravan();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseCaravan> call, Throwable t) {

            }
        });
    }

    public void clearQueue() {
        mCountSchedule = 0;
        mNumSchedule.setVisibility(View.INVISIBLE);
        mLayoutOpenSchedule.setVisibility(View.INVISIBLE);
        mRvSchedule.setVisibility(View.INVISIBLE);
        mTvNoItem.setVisibility(View.VISIBLE);
        mArrListing.clear();
        mAdapter.notifyDataSetChanged();
        ArrayList<ConsumerListingSchedule> arrListingSchedule = CurrentListingSchedule.getInstance().getArrListing();
        ArrayList<String> queueIds = CaravanQueue.getInstance().getIds();
        for (int j = 0; j < queueIds.size(); j++) {
            for (int i = 0; i < arrListingSchedule.size(); i++) {
                if (arrListingSchedule.get(i).getListing().getId().equalsIgnoreCase(queueIds.get(j))) {
                    //arrListingSchedule.remove(i);
                    updateSchedule(queueIds.get(j), false);
                }
            }
        }
        mPageChange.clearQueue(queueIds);
    }

    public void openLayoutCreateCaravan() {
        ObjectAnimator objectAnimator = AnimUtils.animationButtonMenuWithOpenViewWithListener(MainActivityConsumer.mDrawerArrowDrawable, mLayoutStepSchedule, true);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mNeedSelectDay.setVisibility(View.VISIBLE);
                mNextStep.setVisibility(View.GONE);
                mCurrentStep = 0;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initViewPager();
                        mIsShowStepSchedule = true;
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

    @Override
    public void getListingDetailSuccess(Listing listing) {
        updateSchedule(listing.getId(), true);
        mArrListing.add(listing);
        ConsumerListingSchedule listingSchedule = new ConsumerListingSchedule();
        listingSchedule.setListing(listing);
        CurrentListingSchedule.getInstance().getArrListing().add(listingSchedule);
        mAdapter.notifyItemInserted(mAdapter.getItemCount());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getListingDetailFail(String message) {

    }

    @Override
    public void getListingDetailFail(@StringRes int message) {

    }
}
