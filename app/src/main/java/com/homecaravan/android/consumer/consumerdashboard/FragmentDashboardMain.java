package com.homecaravan.android.consumer.consumerdashboard;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.activity.ConversationActivity;
import com.homecaravan.android.consumer.activity.MessageActivity;
import com.homecaravan.android.consumer.adapter.AgentDashboardAdapter;
import com.homecaravan.android.consumer.adapter.FeaturedAgentAdapter;
import com.homecaravan.android.consumer.adapter.JustListedDashboardAdapter;
import com.homecaravan.android.consumer.adapter.ShowingHistoryDashboardAdapter;
import com.homecaravan.android.consumer.adapter.TrendingDashboardAdapter;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.consumerbase.ConsumerUser;
import com.homecaravan.android.consumer.consumerdashboard.featuredagentsmvp.FeatureAgentsHelper;
import com.homecaravan.android.consumer.consumerdashboard.featuredagentsmvp.FeatureAgentsPresenter;
import com.homecaravan.android.consumer.consumerdashboard.featuredagentsmvp.FeatureAgentsView;
import com.homecaravan.android.consumer.consumerdashboard.showinghistorymvp.ShowingHistoryView;
import com.homecaravan.android.consumer.consumerdata.ConsumerListingFullStatus;
import com.homecaravan.android.consumer.consumermvp.listingmvp.GetJustListPresenter;
import com.homecaravan.android.consumer.consumermvp.listingmvp.GetJustListView;
import com.homecaravan.android.consumer.consumermvp.listingmvp.GetTrendingPresenter;
import com.homecaravan.android.consumer.consumermvp.listingmvp.GetTrendingView;
import com.homecaravan.android.consumer.consumermvp.loginmvp.GetFeaturedPresenter;
import com.homecaravan.android.consumer.consumermvp.loginmvp.GetFeaturedView;
import com.homecaravan.android.consumer.consumermvp.loginmvp.SetAgentPresenter;
import com.homecaravan.android.consumer.consumermvp.loginmvp.SetAgentView;
import com.homecaravan.android.consumer.consumermvp.searchmvp.GetFavoriteListingView;
import com.homecaravan.android.consumer.consumermvp.searchmvp.GetListSearchPresenter;
import com.homecaravan.android.consumer.consumermvp.searchmvp.GetListSearchView;
import com.homecaravan.android.consumer.consumermvp.showingmvp.GetListShowingPastPresenter;
import com.homecaravan.android.consumer.consumermvp.showingmvp.GetListShowingPastView;
import com.homecaravan.android.consumer.fastadapter.SaveSearchDashboardItem;
import com.homecaravan.android.consumer.fragment.FragmentDashboardNew;
import com.homecaravan.android.consumer.listener.IAgentListener;
import com.homecaravan.android.consumer.listener.IDashboardListener;
import com.homecaravan.android.consumer.listener.IPageChange;
import com.homecaravan.android.consumer.message.messagegetthreadidmvp.GetThreadIdPresenter;
import com.homecaravan.android.consumer.message.messagegetthreadidmvp.IGetThreadIdView;
import com.homecaravan.android.consumer.model.BaseDataRecyclerView;
import com.homecaravan.android.consumer.model.ConsumerTeam;
import com.homecaravan.android.consumer.model.EventAgentDetail;
import com.homecaravan.android.consumer.model.HeaderRvData;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.model.ViewAllRvData;
import com.homecaravan.android.consumer.model.listitem.ListingDashboard;
import com.homecaravan.android.consumer.model.responseapi.CaravanShowing;
import com.homecaravan.android.consumer.model.responseapi.Listing;
import com.homecaravan.android.consumer.model.responseapi.ListingFull;
import com.homecaravan.android.consumer.model.responseapi.ResponseAllSearch;
import com.homecaravan.android.consumer.model.responseapi.ResponseFeatured;
import com.homecaravan.android.consumer.model.responseapi.ResponseMessageGetThreadId;
import com.homecaravan.android.consumer.model.responseapi.Search;
import com.homecaravan.android.consumer.utils.Utils;
import com.homecaravan.android.consumer.widget.CustomNestedScrollView;
import com.homecaravan.android.consumer.widget.CustomViewPager;
import com.homecaravan.android.models.CurrentSaveSearch;
import com.homecaravan.android.ui.CircleImageView;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class FragmentDashboardMain extends BaseFragment implements ShowingHistoryView,
        FeatureAgentsView, IDashboardListener, GetListSearchView, GetJustListView, GetTrendingView,
        GetListShowingPastView, GetFavoriteListingView,
        GetFeaturedView, SetAgentView, IAgentListener, IGetThreadIdView {
    public static final String TAG = FragmentDashboardMain.class.getSimpleName();
    private JustListedDashboardAdapter mJustListedAdapter;
    private TrendingDashboardAdapter mTrendingAdapter;
    private ShowingHistoryDashboardAdapter mShowingHistoryAdapter;
    private FeaturedAgentAdapter mFeaturedAgentAdapter;
    private AgentDashboardAdapter mAgentAdapter;

    private FastItemAdapter<SaveSearchDashboardItem> mSaveSearchAdapter;
    private ArrayList<Search> mArrSearch = new ArrayList<>();
    private ArrayList<ConsumerTeam> mArrTeam = new ArrayList<>();
    private ArrayList<ConsumerListingFullStatus> mArrListing = new ArrayList<>();
    private ArrayList<BaseDataRecyclerView> mArrBaseArrTeam = new ArrayList<>();
    private ArrayList<BaseDataRecyclerView> mArrBaseListing = new ArrayList<>();

    private ArrayList<BaseDataRecyclerView> mArrListingTrending = new ArrayList<>();
    private ArrayList<BaseDataRecyclerView> mArrListingJustList = new ArrayList<>();
    private ArrayList<View> mArrView = new ArrayList<>();
    // mvp

    private GetTrendingPresenter mGetTrendingPresenter;
    private GetJustListPresenter mGetJustListPresenter;
    private GetListSearchPresenter mGetListSearchPresenter;
    private GetListShowingPastPresenter mGetShowingPastPresenter;
    private FeatureAgentsPresenter mFeatureAgentsPresenter;
    private FeatureAgentsHelper mFeatureAgentsHelper;
    private GetFeaturedPresenter mGetFeaturedPresenter;
    private SetAgentPresenter mSetAgentPresenter;
    private ResponseFeatured.Featured mFeaturedAgent;
    private IPageChange mPageChange;
    private boolean mInitData;
    private int mCurrentPosition = 0;
    private int mOldPosition = -1;
    private float mPositionMortgage;
    private float mPositionTitle;
    private float mPositionEscrow;
    private float mPositionAgent;
    private float mPositionHcHelp;
    private float mPositionHomeInspector;
    private int mWidthMortgage;
    private int mWidthTitle;
    private int mWidthEscrow;
    private int mWidthAgent;
    private int mWidthHcHelp;
    private int mWidthHomeInspector;
    private GetThreadIdPresenter mGetThreadIdPresenter;

    @Bind(R.id.layoutMain)
    LinearLayout mLayoutMain;
    @Bind(R.id.rvSaveSearch)
    RecyclerView mSaveSearch;
    @Bind(R.id.rvJustListed)
    RecyclerView mJustListed;
    @Bind(R.id.rvShowingHistory)
    RecyclerView mShowingHistory;
    @Bind(R.id.rvSaveTrending)
    RecyclerView mTrending;
    @Bind(R.id.rvAgent)
    RecyclerView mAgent;
    @Bind(R.id.rvAgentShowing)
    RecyclerView mRvAgentShowing;
    @Bind(R.id.layoutSavedSearch)
    FrameLayout mLayoutSavedSearch;
    @Bind(R.id.scrollView)
    CustomNestedScrollView mScrollView;
    @Bind(R.id.scrollViewNew)
    HorizontalScrollView mScrollViewNew;
    @Bind(R.id.tvNumSavedSearch)
    TextView mNumSavedSearch;
    @Bind(R.id.tvMortgage)
    TextView mTvMortgage;
    @Bind(R.id.tvTitle)
    TextView mTvTitle;
    @Bind(R.id.tvEscrow)
    TextView mTvEscrow;
    @Bind(R.id.tvAgent)
    TextView mTvAgent;
    @Bind(R.id.tvHcHelp)
    TextView mTvHcHelp;
    @Bind(R.id.tvHomeInspector)
    TextView mTvHomeInspector;
    @Bind(R.id.viewPager)
    CustomViewPager mViewPager;
    @Bind(R.id.layoutViewAllSearch)
    RelativeLayout mLayoutViewAllSearch;
    @Bind(R.id.view1)
    View mView1;
    @Bind(R.id.view2)
    View mView2;
    @Bind(R.id.view3)
    View mView3;
    @Bind(R.id.view4)
    View mView4;
    @Bind(R.id.view5)
    View mView5;
    @Bind(R.id.view6)
    View mView6;
    @Bind(R.id.layoutAgent)
    LinearLayout mLayoutAgent;
    @Bind(R.id.ivAgent)
    CircleImageView mIvAgent;
    @Bind(R.id.tvAgentName)
    TextView mAgentName;
    @Bind(R.id.tvCompany)
    TextView mAgentCompany;
    @Bind(R.id.tvNewMessagesCount)
    TextView mTvNewMessagesCount;


    @OnClick(R.id.rlMessageOnDashBoard)
    public void onMessageOnDashBoardClicked() {
        Intent intent = new Intent(getActivity(), MessageActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
    }

    @OnClick(R.id.tvMortgage)
    public void onTvMortgageClicked() {
        mViewPager.setCurrentItem(0);
        mOldPosition = mCurrentPosition;
        mCurrentPosition = 0;
        scrollAndUpdateButton();
    }

    @OnClick(R.id.tvTitle)
    public void onTvTitleClicked() {
        mViewPager.setCurrentItem(1);
        mOldPosition = mCurrentPosition;
        mCurrentPosition = 1;
        scrollAndUpdateButton();
    }

    @OnClick(R.id.tvEscrow)
    public void onTvEscrowClicked() {
        mViewPager.setCurrentItem(2);
        mOldPosition = mCurrentPosition;
        mCurrentPosition = 2;
        scrollAndUpdateButton();
    }

    @OnClick(R.id.tvAgent)
    public void onTvAgentClicked() {
        mViewPager.setCurrentItem(3);
        mOldPosition = mCurrentPosition;
        mCurrentPosition = 3;
        scrollAndUpdateButton();
    }

    @OnClick(R.id.tvHcHelp)
    public void onTvHcHelpClicked() {
        mViewPager.setCurrentItem(4);
        mOldPosition = mCurrentPosition;
        mCurrentPosition = 4;
        scrollAndUpdateButton();
    }

    @OnClick(R.id.tvHomeInspector)
    public void onTvHomeInspectorClicked() {
        mViewPager.setCurrentItem(5);
        mOldPosition = mCurrentPosition;
        mCurrentPosition = 5;
    }


    @OnClick(R.id.layoutSavedSearchFavorites)
    public void openSavedSearchFavorites() {
        mPageChange.openAllSavedSearch();
    }

    @OnClick(R.id.layoutAddNewSearch)
    public void addNewSearch() {

    }

    @OnClick(R.id.layoutViewAll)
    public void viewAllSearch() {
        mPageChange.openAllSavedSearch();
    }

    @OnClick(R.id.layoutHeaderSavedSearch)
    public void onLayoutHeaderSavedSearch() {
        mPageChange.openAllSavedSearch();
    }

    @OnClick(R.id.layoutUpcomingShowings)
    public void openShowings() {
        mPageChange.pageChange(4);
    }

    @OnClick(R.id.layoutNewMessage)
    public void openMessage() {

    }

    @OnClick(R.id.ivMessage)
    public void sendMessageAgent() {
        mGetThreadIdPresenter.getThreadId("", "", "", ConsumerUser.getInstance().getData().getAgentFullName(), ConsumerUser.getInstance().getData().getAgentId());
        showLoading();
    }

    @OnClick(R.id.ivCall)
    public void callAgent() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mArrView.add(mView1);
        mArrView.add(mView2);
        mArrView.add(mView3);
        mArrView.add(mView4);
        mArrView.add(mView5);
        mArrView.add(mView6);
        mGetThreadIdPresenter = new GetThreadIdPresenter(this);
    }

    public void initData() {
        if (!mInitData) {
            setupMvp();
            mInitData = true;
            //mLayoutMain.setVisibility(View.VISIBLE);
        }
    }

    public void setPageChange(IPageChange mPageChange) {
        this.mPageChange = mPageChange;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_dashboard_main;
    }

    public LinearLayoutManager createLayoutManager() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
    }


    public void setupMvp() {

        setUpViewPager();
        mGetTrendingPresenter = new GetTrendingPresenter(this);
        mGetJustListPresenter = new GetJustListPresenter(this);
        mJustListedAdapter = new JustListedDashboardAdapter(getActivity(), mArrListingJustList, this);
        mJustListed.setLayoutManager(createLayoutManager());

        mTrendingAdapter = new TrendingDashboardAdapter(getActivity(), mArrListingTrending, this);
        mTrending.setLayoutManager(createLayoutManager());

        mShowingHistoryAdapter = new ShowingHistoryDashboardAdapter(getActivity(), mArrBaseListing, this);
        mShowingHistory.setLayoutManager(createLayoutManager());

        mAgentAdapter = new AgentDashboardAdapter(getActivity(), mArrTeam);
        mRvAgentShowing.setLayoutManager(createLayoutManager());

        mSaveSearchAdapter = new FastItemAdapter<>();

        mJustListed.setAdapter(mJustListedAdapter);
        mTrending.setAdapter(mTrendingAdapter);
        mShowingHistory.setAdapter(mShowingHistoryAdapter);

        mRvAgentShowing.setAdapter(mAgentAdapter);
        mSaveSearch.setLayoutManager(createLayoutManager());
        mSaveSearch.setAdapter(mSaveSearchAdapter);

        mGetListSearchPresenter = new GetListSearchPresenter(this);
        mGetListSearchPresenter.getListSearch("1", "", "", "");

        mGetShowingPastPresenter = new GetListShowingPastPresenter(this);
        mGetShowingPastPresenter.getListShowingPast("caravan");

        mFeatureAgentsHelper = new FeatureAgentsHelper();
        mFeatureAgentsPresenter = new FeatureAgentsPresenter(mFeatureAgentsHelper, this);
        mFeatureAgentsPresenter.getFeatureAgents();

        mGetFeaturedPresenter = new GetFeaturedPresenter(this);
        mGetFeaturedPresenter.getFeature();
        mGetTrendingPresenter.getTrending("10");
        mGetJustListPresenter.getJustList("10");

        mSetAgentPresenter = new SetAgentPresenter(this);

        if (ConsumerUser.getInstance().getData().getHasAgent().equalsIgnoreCase("yes")) {
            mLayoutAgent.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(ConsumerUser.getInstance().getData().getAgentPhoto())
                    .asBitmap().fitCenter().into(mIvAgent);
            if(ConsumerUser.getInstance().getData().getAgentCompany() != null){
                if (ConsumerUser.getInstance().getData().getAgentCompany().getJobTitle() != null) {
                    mAgentCompany.setText(ConsumerUser.getInstance().getData().getAgentCompany().getJobTitle());
                }
            }
            mAgentName.setText(String.format(getString(R.string.concat_two_word),
                    ConsumerUser.getInstance().getData().getAgentFirstName(),
                    ConsumerUser.getInstance().getData().getAgentLastName()));

        }
    }


    @Override
    public void showShowingHistory(ArrayList<ConsumerListingFullStatus> showing) {

        mArrListing.clear();
        mArrBaseListing.clear();
        mArrListing.addAll(showing);
        mArrBaseListing.add(new HeaderRvData());
        for (int i = 0; i < mArrListing.size(); i++) {
            mArrBaseListing.add(mArrListing.get(i));
        }
        mArrBaseListing.add(new ViewAllRvData());
        mShowingHistoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showFeatureAgents(ArrayList<ConsumerTeam> teams) {
        mArrTeam.clear();
        mArrTeam.addAll(teams);
        mAgentAdapter.notifyDataSetChanged();
        mArrBaseArrTeam.clear();
        mArrBaseArrTeam.add(new HeaderRvData());
        for (int i = 0; i < teams.size(); i++) {
            mArrBaseArrTeam.add(teams.get(i));
        }
        mArrBaseArrTeam.add(new ViewAllRvData());

    }


    @Override
    public void showError(String message) {

    }

    public void resetFragment() {

    }


    @Override
    public void openAllSavedSearch() {
        mPageChange.openAllSavedSearch();
    }

    @Override
    public void openSavedSearchItem(String id) {
        //mPageChange.openSavedSearchItem(id);
    }

    @Override
    public void openAllShowing() {
        mPageChange.openAllShowing();
    }

    @Override
    public void openAllAgent() {

    }

    @Override
    public void openAgent(final ResponseFeatured.Featured featuredAgent) {
        mFeaturedAgent = featuredAgent;
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setNegativeButton("View Information", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EventBus.getDefault().post(new EventAgentDetail(featuredAgent.getId()));
                        dialogInterface.dismiss();
                    }
                })
                .setNeutralButton("Set Exclusive", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mSetAgentPresenter.setAgent(ConsumerUser.getInstance().getData().getId(), featuredAgent.getId());
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
        alertDialog.show();
        //PageChange.openAgent(position);
    }

    @Override
    public void openMyTeam() {
        mPageChange.openAllAgent();
    }

    @Override
    public void openDiscover(String searchName) {
        mPageChange.openDiscover(searchName);
    }

    @Override
    public void getListSearchSuccess(ResponseAllSearch.ResponseAllSearchData searchData) {
        ArrayList<Search> searches = searchData.getArrSearch();
        mArrSearch.clear();
        mArrSearch.addAll(searches);
        mSaveSearchAdapter.clear();
        int count = 0;
        for (Search search : searches) {
            Object ft = search.getConditions().get(0).getFt();
            if (ft != null) {
                if (ft instanceof ArrayList) {
                    ArrayList<String> arr = Utils.objectToArray(ft);
                    search.getConditions().get(0).setArrFt(arr);
                } else {
                    search.getConditions().get(0).setsFt(ft.toString());
                }
            }
            SaveSearchDashboardItem saveSearchItem = new SaveSearchDashboardItem();
            saveSearchItem.setSearch(search);
            saveSearchItem.setContext(getActivity());
            mSaveSearchAdapter.add(saveSearchItem);
            count++;
            if (count == 3) {
                break;
            }
        }
        mSaveSearchAdapter.withOnClickListener(new FastAdapter.OnClickListener<SaveSearchDashboardItem>() {
            @Override
            public boolean onClick(View v, IAdapter<SaveSearchDashboardItem> adapter, SaveSearchDashboardItem item, int position) {
                CurrentSaveSearch.getInstance().setName(item.getSearch().getName());
                CurrentSaveSearch.getInstance().setId(item.getSearch().getId());
                mPageChange.openSavedSearchItem(item.getSearch().getId());
                return false;
            }
        });
        if (!searches.isEmpty()) {
            mNumSavedSearch.setText(searchData.getTotal());
            mLayoutViewAllSearch.setVisibility(View.VISIBLE);
        }
        mLayoutSavedSearch.setVisibility(View.VISIBLE);
    }

    @Override
    public void getListSearchFail(String message) {
        Log.e(TAG, message);
    }

    @Override
    public void getListSearchFail(@StringRes int message) {

    }


    @Override
    public void getJustListSuccess(ArrayList<ListingFull> listingFulls) {
        mArrListingJustList.add(new HeaderRvData());
        for (int i = 0; i < listingFulls.size(); i++) {
            ListingDashboard listingDashboard = new ListingDashboard();
            listingDashboard.setListingFull(listingFulls.get(i));
            mArrListingJustList.add(listingDashboard);
            if (i == 3) {
                break;
            }
        }
        mArrListingJustList.add(new ViewAllRvData());
        mJustListedAdapter.notifyDataSetChanged();
    }

    @Override
    public void getJustListFail() {
        Log.e("getJustListFail", "getJustListFail");
    }

    @Override
    public void getTrendingSuccess(ArrayList<ListingFull> listingFulls) {
        mArrListingTrending.add(new HeaderRvData());
        for (int i = 0; i < listingFulls.size(); i++) {
            ListingDashboard listingDashboard = new ListingDashboard();
            listingDashboard.setListingFull(listingFulls.get(i));
            mArrListingTrending.add(listingDashboard);
            if (i == 3) {
                break;
            }
        }
        mArrListingTrending.add(new ViewAllRvData());
        mTrendingAdapter.notifyDataSetChanged();
    }

    @Override
    public void getTrendingFail() {
        Log.e("getTrendingFail", "getTrendingFail");
    }


    public void reloadSaveSearch() {
        mGetListSearchPresenter.getListSearch("1", "", "", "");
    }

    public void setUpPositionAnimation() {
        mTvMortgage.post(new Runnable() {
            @Override
            public void run() {
                mPositionMortgage = mTvMortgage.getX();
                mWidthMortgage = mTvMortgage.getWidth();
            }
        });
        mTvTitle.post(new Runnable() {
            @Override
            public void run() {
                mPositionTitle = mTvTitle.getX();
                mWidthTitle = mTvTitle.getWidth();
            }
        });
        mTvEscrow.post(new Runnable() {
            @Override
            public void run() {
                mPositionEscrow = mTvEscrow.getX();
                mWidthEscrow = mTvEscrow.getWidth();
            }
        });
        mTvAgent.post(new Runnable() {
            @Override
            public void run() {
                mPositionAgent = mTvAgent.getX();
                mWidthAgent = mTvAgent.getWidth();
            }
        });
        mTvHcHelp.post(new Runnable() {
            @Override
            public void run() {
                mPositionHcHelp = mTvHcHelp.getX();
                mWidthHcHelp = mTvHcHelp.getWidth();
            }
        });
        mTvHomeInspector.post(new Runnable() {
            @Override
            public void run() {
                mPositionHomeInspector = mTvHomeInspector.getX();
                mWidthHomeInspector = mTvHomeInspector.getWidth();
            }
        });
    }

    public void updateView(int position) {
        for (int i = 0; i < mArrView.size(); i++) {
            if (i == position) {
                mArrView.get(i).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.view_dashboard_selected));
            } else {
                mArrView.get(i).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.view_dashboard));
            }
        }
    }

    public void scrollAndUpdateButton() {
        if (mCurrentPosition == mOldPosition) {
            return;
        }
        if (mCurrentPosition == 0) {
            mTvMortgage.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_dashboard));
            mTvMortgage.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
            scrolling(mScrollViewNew, (int) (mPositionMortgage + mWidthMortgage / 2 - Utils.widthScreen / 2));
        }
        if (mCurrentPosition == 1) {
            mTvTitle.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_dashboard));
            mTvTitle.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
            scrolling(mScrollViewNew, (int) (mPositionTitle + mWidthTitle / 2 - Utils.widthScreen / 2));
        }
        if (mCurrentPosition == 2) {
            mTvEscrow.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_dashboard));
            mTvEscrow.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
            scrolling(mScrollViewNew, (int) (mPositionEscrow + mWidthEscrow / 2 - Utils.widthScreen / 2));
        }
        if (mCurrentPosition == 3) {
            mTvAgent.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_dashboard));
            mTvAgent.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
            scrolling(mScrollViewNew, (int) (mPositionAgent + mWidthAgent / 2 - Utils.widthScreen / 2));
        }
        if (mCurrentPosition == 4) {
            mTvHcHelp.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_dashboard));
            mTvHcHelp.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
            scrolling(mScrollViewNew, (int) (mPositionHcHelp + mWidthHcHelp / 2 - Utils.widthScreen / 2));
        }
        if (mCurrentPosition == 5) {
            mTvHomeInspector.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_dashboard));
            mTvHomeInspector.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
            scrolling(mScrollViewNew, (int) (mPositionHomeInspector + mWidthHomeInspector / 2 - Utils.widthScreen / 2));
        }
        if (mOldPosition == 0) {
            mTvMortgage.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_dashboard1));
            mTvMortgage.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorTextMain));
        }
        if (mOldPosition == 1) {
            mTvTitle.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_dashboard1));
            mTvTitle.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorTextMain));
        }
        if (mOldPosition == 2) {
            mTvEscrow.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_dashboard1));
            mTvEscrow.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorTextMain));
        }
        if (mOldPosition == 3) {
            mTvAgent.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_dashboard1));
            mTvAgent.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorTextMain));
        }
        if (mOldPosition == 4) {
            mTvHcHelp.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_dashboard1));
            mTvHcHelp.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorTextMain));
        }
        if (mOldPosition == 5) {
            mTvHomeInspector.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_dashboard1));
            mTvHomeInspector.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorTextMain));
        }
    }

    public void setUpViewPager() {
        mViewPager.setSwipeEnabled(true);
        ArrayList<Integer> arrTitle = new ArrayList<>();
        ArrayList<Integer> arrSrc = new ArrayList<>();
        ArrayList<Integer> arrNew = new ArrayList<>();
        arrNew.add(R.string.mortgage);
        arrTitle.add(R.string.mortgage_detail);
        arrSrc.add(R.drawable.dashboard_1);
        arrNew.add(R.string.title_insurance);
        arrTitle.add(R.string.title_insurance_detail);
        arrSrc.add(R.drawable.dashboard_2);
        arrNew.add(R.string.escrow);
        arrTitle.add(R.string.escrow_detail);
        arrSrc.add(R.drawable.dashboard_3);
        arrNew.add(R.string.agent);
        arrTitle.add(R.string.agent_detail);
        arrSrc.add(R.drawable.dashboard_4);
        arrNew.add(R.string.hc_help);
        arrTitle.add(R.string.hc_help_detail);
        arrSrc.add(R.drawable.dashboard_5);
        arrNew.add(R.string.home_inspector);
        arrTitle.add(R.string.home_inspector_detail);
        arrSrc.add(R.drawable.dashboard_6);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), arrTitle.size(), arrNew, arrTitle, arrSrc);
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.setOffscreenPageLimit(arrSrc.size());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mOldPosition = mCurrentPosition;
                mCurrentPosition = position;
                //scrollAndUpdateButton();
                updateView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void scrolling(HorizontalScrollView sv, int position) {
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofInt(sv, "scrollX", position);
        objectAnimatorY.setStartDelay(0);
        objectAnimatorY.setDuration(300);
        objectAnimatorY.start();
    }

    @Override
    public void getShowingCaravanSuccess(ArrayList<CaravanShowing> caravans) {

    }

    @Override
    public void getShowingCaravanFail(String message) {

    }

    @Override
    public void getShowingCaravanFail(@StringRes int message) {

    }

    public void showLayout() {
        if (mLayoutMain.getVisibility() == View.GONE) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLayoutMain.setVisibility(View.INVISIBLE);
                    setUpPositionAnimation();
                }
            }, 500);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLayoutMain.setVisibility(View.VISIBLE);
                }
            }, 700);
        }
    }

    @Override
    public void getFavoriteSuccess(ArrayList<Listing> listings) {

    }

    @Override
    public void getFavoriteFail(String message) {

    }

    @Override
    public void getFavoriteFail(@StringRes int message) {

    }

    @Override
    public void getFeaturedSuccess(ArrayList<ResponseFeatured.Featured> arrFeatured) {
        ArrayList<BaseDataRecyclerView> arrayList = new ArrayList<>();
        arrayList.add(new HeaderRvData());
        arrayList.addAll(arrFeatured);
        mFeaturedAgentAdapter = new FeaturedAgentAdapter(getActivity(), arrayList, this);
        mAgent.setLayoutManager(createLayoutManager());
        mAgent.setAdapter(mFeaturedAgentAdapter);
    }

    @Override
    public void setAgentSuccess() {
        ConsumerUser.getInstance().getData().setHasAgent("yes");
        ConsumerUser.getInstance().getData().setAgentId(mFeaturedAgent.getId());
        ConsumerUser.getInstance().getData().setAgentFirstName(mFeaturedAgent.getFirstName());
        ConsumerUser.getInstance().getData().setAgentLastName(mFeaturedAgent.getLastName());
        ConsumerUser.getInstance().getData().setAgentPnUid(mFeaturedAgent.getPnUid());
        ConsumerUser.getInstance().getData().setAgentPhoto(mFeaturedAgent.getAvatar());
        mLayoutAgent.setVisibility(View.VISIBLE);
        Glide.with(getContext()).load(mFeaturedAgent.getAvatar())
                .asBitmap().fitCenter().into(mIvAgent);
        if (!mFeaturedAgent.getCompany().isEmpty()) {
            mAgentCompany.setText(mFeaturedAgent.getCompany().get(0).getName());
        }
        mAgentName.setText(mFeaturedAgent.getFullName());
    }

    @Override
    public void setAgentFail(String message) {
        showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "setAgent");
    }

    @Override
    public void setAgentFail(@StringRes int message) {
        showSnackBar(mLayoutMain, TypeDialog.ERROR, message, "setAgent");

    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final int mSize;
        private ArrayList<Integer> mArrTitle;
        private ArrayList<Integer> mArrSrc;
        private ArrayList<Integer> mArrNew;

        public ViewPagerAdapter(FragmentManager fm, int size, ArrayList<Integer> mArrNew, ArrayList<Integer> mArrTitle, ArrayList<Integer> mArrSrc) {
            super(fm);
            mSize = size;
            this.mArrSrc = mArrSrc;
            this.mArrTitle = mArrTitle;
            this.mArrNew = mArrNew;
        }

        @Override
        public int getCount() {
            return mSize;
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentDashboardNew.getInstance(mArrNew.get(position), mArrSrc.get(position), mArrTitle.get(position));
        }
    }

    @Override
    public void getThreadIdAtCaravanSuccess(ResponseMessageGetThreadId threadId, int position, String threadName) {

    }

    @Override
    public void getThreadIdSuccess(ResponseMessageGetThreadId threadId, String threadName) {
        Log.e(TAG, "getThreadIdSuccess: threadId: " + threadId.getThreadId());
        if(!HomeCaravanApplication.mLoginSocketSuccess){
            return;
        }
        Intent intent = new Intent(getActivity(), ConversationActivity.class);
        intent.putExtra("THREAD_ID", threadId.getThreadId());
        String responseMessage1 = "I’m driving right now";
        intent.putExtra("RESPONSE_MESSAGE_1", responseMessage1);
        intent.putExtra("MESSAGE_THREAD_NAME", threadName);
        startActivity(intent);
        hideLoading();
    }

    @Override
    public void getThreadIdFail() {
        hideLoading();
        showSnackBar(mLayoutMain, TypeDialog.ERROR, "Failed", "getThreadIdFail");
    }

    @Override
    public void getThreadIdFail(@StringRes int message) {
        hideLoading();
        showSnackBar(mLayoutMain, TypeDialog.ERROR, message, "getThreadIdFail");
    }

    public void setNewMessagesCount(int count){
        if(mTvNewMessagesCount != null){
            mTvNewMessagesCount.setText(String.valueOf(count));
        }
    }

}