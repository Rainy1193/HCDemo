package com.homecaravan.android.consumer.consumerdiscover;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.adapter.ViewPagerAdapter;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.consumermvp.searchmvp.GetSearchDetailPresenter;
import com.homecaravan.android.consumer.consumermvp.searchmvp.GetSearchDetailView;
import com.homecaravan.android.consumer.listener.IFilterListener;
import com.homecaravan.android.consumer.listener.IUpdateSavedSearchListener;
import com.homecaravan.android.consumer.listener.IUpdateSavedSearchSuccess;
import com.homecaravan.android.consumer.model.SingletonFilter;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.model.responseapi.SearchDetail;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.homecaravan.android.consumer.utils.Utils;
import com.homecaravan.android.models.CurrentSaveSearch;

import butterknife.Bind;
import butterknife.OnClick;

public class FragmentSavedSearchDetail extends BaseFragment implements IUpdateSavedSearchSuccess, IUpdateSavedSearchListener,
        GetSearchDetailView, IFilterListener {
    private ViewPagerAdapter mAdapterViewPager;

    private String mType = "Listings";
    private FragmentListSavedSearch mListSavedSearch;
    private FragmentTeamSavedSearch mTeamSavedSearch;
    private FragmentSettingsSavedSearch mSettingsSavedSearch;
    private GetSearchDetailPresenter mGetSearchDetailPresenter;
    private String mSearchId;
    private boolean mGetDetailSuccess;
    private boolean mShowFilter;

    @Bind(R.id.layoutFilter)
    RelativeLayout mLayoutFilter;
    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    @Bind(R.id.layoutMenuContent)
    LinearLayout mLayoutMenuContent;
    @Bind(R.id.layoutMenu)
    LinearLayout mLayoutMenu;
    @Bind(R.id.layoutMain)
    RelativeLayout mLayoutMain;
    @Bind(R.id.ivChangeView)
    ImageView mIvChangeView;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;

    @OnClick(R.id.ivChangeView)
    public void changeView() {
        if (mListSavedSearch.isViewMap()) {
            mListSavedSearch.changeView(false);
            mIvChangeView.setImageResource(R.drawable.ic_view_map);
            mViewPager.setCurrentItem(0);
        } else {
            mListSavedSearch.changeView(true);
            mIvChangeView.setImageResource(R.drawable.ic_view_list);
            mViewPager.setCurrentItem(0);
        }
    }

    @OnClick(R.id.layoutTopRated)
    public void onLayoutTopRatedClicked() {
        mType = "Top Rated";
        closeMenu();
    }

    @OnClick(R.id.layoutJustListed)
    public void onLayoutJustListedClicked() {
        mType = "Just Listed";
        closeMenu();
    }

    @OnClick(R.id.layoutToReview)
    public void onLayoutToReviewClicked() {
        mType = "To Review";
        closeMenu();
    }

    @OnClick(R.id.layoutReviewed)
    public void onLayoutReviewedClicked() {
        mType = "Reviewed";
        closeMenu();
    }

    @OnClick(R.id.layoutListing)
    public void onLayoutListingClicked() {
        mType = "Listings";
        closeMenu();
    }

    @OnClick(R.id.layoutUpdate)
    public void onLayoutUpdateClicked() {
        mType = "Recently...";
        closeMenu();
    }


    @OnClick(R.id.layoutMenu)
    public void onLayoutMenuClicked() {
        closeMenu();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGetSearchDetailPresenter = new GetSearchDetailPresenter(this);
        initData();
    }

    public boolean isShowFilter() {
        return mShowFilter;
    }

    public boolean isGetDetailSuccess() {
        return mGetDetailSuccess;
    }

    public void initData() {
        mAdapterViewPager = new ViewPagerAdapter(getChildFragmentManager());
        mListSavedSearch = new FragmentListSavedSearch();
        mSettingsSavedSearch = new FragmentSettingsSavedSearch();
        mSettingsSavedSearch.setListener(this);
        mTeamSavedSearch = new FragmentTeamSavedSearch();
        mTeamSavedSearch.setListener(this);
        mAdapterViewPager.addFragment(mListSavedSearch, "List");
        mAdapterViewPager.addFragment(mTeamSavedSearch, "Team");
        mAdapterViewPager.addFragment(mSettingsSavedSearch, "Settings");
        mViewPager.setAdapter(mAdapterViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position != 2) {
                    mSettingsSavedSearch.checkNameSearch();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
        setupTabs();
        setupTabListener();
        mViewPager.setOffscreenPageLimit(3);
        createFragmentFilter();
    }

    public void updateViewPager(String id) {
        mViewPager.setCurrentItem(0);
        mViewPager.setVisibility(View.VISIBLE);
        mListSavedSearch.initData();
        mGetDetailSuccess = false;
        mSearchId = id;
        mGetSearchDetailPresenter.getSearchDetail(mSearchId);
    }

    public void clearViewPager() {
        SingletonFilter.getInstance().clearFilter();
        onLayoutMenuClicked();
        mViewPager.setCurrentItem(0);
        mTeamSavedSearch.clearPager();
//        mSettingsSavedSearch.clearPager();
        mListSavedSearch.clearPager();
        mListSavedSearch.changeView(false);

        if (mShowFilter) {
            cancelFilter();
        }
    }

    @Override
    public void updateSuccess(String tab) {

    }


    public void closeMenu() {
        if (mLayoutMenu.getVisibility() == View.VISIBLE) {
            AnimUtils.collapseView(mLayoutMenuContent, mLayoutMenu);
        }
        setupTabs();
    }

    public void setupTabs() {
        LinearLayout tabStrip = (LinearLayout) mTabLayout.getChildAt(0);
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            mTabLayout.getTabAt(i).setCustomView(null);
        }
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            setCustomTab(i, R.layout.custom_tab);
        }
    }

    public void setCustomTab(int tabPosition, int layout) {
        View customView;
        customView = getActivity().getLayoutInflater().inflate(layout, null);
        TextView textView = (TextView) customView.findViewById(R.id.tabName);
        ImageView imageView = (ImageView) customView.findViewById(R.id.ivDown);

        imageView.setImageResource(R.drawable.ic_down_tab_layout_select);
        if (tabPosition == 0) {
            textView.setText(mType);
        }
        if (tabPosition == 1) {
            imageView.setVisibility(View.GONE);
            textView.setText("Team");
        }
        if (tabPosition == 2) {
            imageView.setVisibility(View.GONE);
            textView.setText("Setting");
        }
        if (mTabLayout.getSelectedTabPosition() == tabPosition) {
            textView.setTextColor(Utils.getColorResources(getActivity(), R.color.colorBuy));
            imageView.setColorFilter(Utils.getColorResources(getActivity(), R.color.colorBuy));
        } else {

            textView.setTextColor(Utils.getColorResources(getActivity(), R.color.colorDashboardText));
            imageView.setColorFilter(Utils.getColorResources(getActivity(), R.color.colorDashboardText));
        }

        mTabLayout.getTabAt(tabPosition).setCustomView(customView);
    }

    private void setupTabListener() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                mViewPager.setCurrentItem(tab.getPosition());
                View customView = tab.getCustomView();
                if (customView != null) {
                    TextView textView = (TextView) customView.findViewById(R.id.tabName);
                    ImageView imageView = (ImageView) customView.findViewById(R.id.ivDown);
                    textView.setTextColor(Utils.getColorResources(getActivity(), R.color.colorBuy));
                    imageView.setColorFilter(Utils.getColorResources(getActivity(), R.color.colorBuy));
                }
                if (mLayoutMenu.getVisibility() == View.VISIBLE && tab.getPosition() != 0) {
                    AnimUtils.collapseView(mLayoutMenuContent, mLayoutMenu);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                if (customView != null) {
                    TextView textView = (TextView) customView.findViewById(R.id.tabName);
                    ImageView imageView = (ImageView) customView.findViewById(R.id.ivDown);
                    textView.setTextColor(Utils.getColorResources(getActivity(), R.color.colorDashboardText));
                    imageView.setColorFilter(Utils.getColorResources(getActivity(), R.color.colorDashboardText));
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    if (mLayoutMenu.getVisibility() == View.VISIBLE) {
                        AnimUtils.collapseView(mLayoutMenuContent, mLayoutMenu);

                    } else {
                        mLayoutMenu.setVisibility(View.VISIBLE);
                        AnimUtils.expandView(mLayoutMenuContent);
                    }
                }
            }
        });
    }

    @Override
    public void updateNameSearch(String nameSearch) {

    }

    @Override
    public void updateAgentFromSetting() {

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_saved_search_detail;
    }

    @Override
    public void getSearchDetailSuccess(SearchDetail searchDetail) {
        CurrentSaveSearch.getInstance().setArrParticipant(searchDetail.getParticipants().getData());
        CurrentSaveSearch.getInstance().setSearchDetail(searchDetail);
        CurrentSaveSearch.getInstance().setId(searchDetail.getId());
        CurrentSaveSearch.getInstance().setName(searchDetail.getName());
        mViewPager.setCurrentItem(0);
        mTeamSavedSearch.updatePager();
        mSettingsSavedSearch.updatePager();
        mListSavedSearch.updatePager();
        mGetDetailSuccess = true;
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void getSearchDetailFail(String message) {
        mProgressBar.setVisibility(View.GONE);
        showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "getSearchDetailFail");
        mGetDetailSuccess = false;
    }

    @Override
    public void getSearchDetailFail(@StringRes int message) {
        mProgressBar.setVisibility(View.GONE);
        showSnackBar(mLayoutMain, TypeDialog.ERROR, message, "getSearchDetailFail");
        mGetDetailSuccess = false;
    }

    public void createFragmentFilter() {
        FragmentFilter fragmentFilter = new FragmentFilter();
        fragmentFilter.setListener(this);
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(mLayoutFilter.getId(), fragmentFilter, "filter");
        fragmentTransaction.commitAllowingStateLoss();
    }


    public FragmentFilter getFragmentFilter() {
        return (FragmentFilter) getChildFragmentManager().findFragmentByTag("filter");
    }

    public void cancelFilter() {
        getFragmentFilter().closeLayout();
        AnimUtils.animationButtonMenuWithCloseView(null, mLayoutFilter, true);
        mShowFilter = false;
    }

    public void showFilter() {
        if (mShowFilter) {
            return;
        }
        mShowFilter = true;
        ObjectAnimator objectAnimator = AnimUtils.animationButtonMenuWithOpenViewWithListener(null, mLayoutFilter, true);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                getFragmentFilter().showLayout(true);
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
    public void applyFilter(String ft, String minPrice, String maxPrice, String bed, String bath, String minLs, String maxLs,
                            String minSf, String maxSf, String minYb, String maxYb, String pt, String dayHc, String k) {
        mListSavedSearch.applyFilter(ft, minPrice, maxPrice, bed, bath, minLs, maxLs, minSf, maxSf, minYb, maxYb, pt, dayHc, k);
        cancelFilter();
    }

    @Override
    public void resetFilter() {
        mListSavedSearch.resetFilter();
    }
}

