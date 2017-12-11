package com.homecaravan.android.consumer.consumershedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.homecaravan.android.R;
import com.homecaravan.android.caravan.ProgressItem;
import com.homecaravan.android.consumer.activity.BookSingleActivity;
import com.homecaravan.android.consumer.adapter.GridSpacingItemDecoration;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.consumermvp.listingmvp.GetListingPrePagePresenter;
import com.homecaravan.android.consumer.consumermvp.listingmvp.GetListingPrePageView;
import com.homecaravan.android.consumer.consumermvp.searchmvp.GetFavoriteListingPresenter;
import com.homecaravan.android.consumer.consumermvp.searchmvp.GetFavoriteListingView;
import com.homecaravan.android.consumer.fastadapter.ScheduleListingItem;
import com.homecaravan.android.consumer.listener.IPickSchedule;
import com.homecaravan.android.consumer.listener.ISchedulePropertyListener;
import com.homecaravan.android.consumer.model.ConsumerListingSchedule;
import com.homecaravan.android.consumer.model.CurrentListingSchedule;
import com.homecaravan.android.consumer.model.listitem.ListingFullItem;
import com.homecaravan.android.consumer.model.responseapi.Listing;
import com.homecaravan.android.consumer.model.responseapi.ListingFull;
import com.mikepenz.fastadapter.adapters.FooterAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter_extensions.scroll.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;

import butterknife.Bind;

public class FragmentPageSchedule extends BaseFragment implements IPickSchedule, GetListingPrePageView, GetFavoriteListingView {

    private ArrayList<ListingFullItem> mArrListing = new ArrayList<>();
    private boolean mSetAdapter;
    private FastItemAdapter<ScheduleListingItem> mFastItemAdapter = new FastItemAdapter<>();
    private FooterAdapter<ProgressItem> mProgressItem;
    private ISchedulePropertyListener mListener;
    private GetListingPrePagePresenter mGetListingPrePagePresenter;
    private GetFavoriteListingPresenter mGetFavoriteListingPresenter;
    private boolean mFavorite;
    private String mSearchId;
    private int mPage = 1;
    private boolean mIsEnd;

    @Bind(R.id.rvSchedule)
    RecyclerView mRvSchedule;
    @Bind(R.id.layoutEmpty)
    RelativeLayout mLayoutEmpty;

    public void setFavorite(boolean mFavorite) {
        this.mFavorite = mFavorite;
    }

    public void setListener(ISchedulePropertyListener mListener) {
        this.mListener = mListener;
    }

    public void setSearchId(String mSearchId) {
        this.mSearchId = mSearchId;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressItem = new FooterAdapter<>();
        mGetListingPrePagePresenter = new GetListingPrePagePresenter(this);
        mGetFavoriteListingPresenter = new GetFavoriteListingPresenter(this);
        mRvSchedule.setAdapter(mProgressItem.wrap(mFastItemAdapter));
        mRvSchedule.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRvSchedule.addItemDecoration(new GridSpacingItemDecoration(getActivity().getResources().getDimensionPixelSize(R.dimen.margin_grid_item)));
        mRvSchedule.setHasFixedSize(true);
        mRvSchedule.setItemAnimator(null);
        mRvSchedule.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore(int currentPage) {
                if (!mIsEnd) {
                    mProgressItem.clear();
                    mProgressItem.add(new ProgressItem().withEnabled(false));
                    mPage++;
                    if (!mFavorite) {
                        mGetListingPrePagePresenter.getListingPrePage(mSearchId, String.valueOf(mPage));
                    } else {
                        mGetFavoriteListingPresenter.getFavorite(String.valueOf(mPage));
                    }
                }
            }
        });
    }

    public void setAdapter() {
        if (!mSetAdapter) {
            if (!mFavorite) {
                mGetListingPrePagePresenter.getListingPrePage(mSearchId, String.valueOf(mPage));
            } else {
                mGetFavoriteListingPresenter.getFavorite(String.valueOf(mPage));
            }
            mSetAdapter = true;
        }
    }

    public void resetAdapter() {
        mPage = 1;
        mFastItemAdapter.clear();
        mGetFavoriteListingPresenter.getFavorite(String.valueOf(mPage));
    }

    public void updateAdapter(String listingId, boolean b) {
        for (ListingFullItem listingFull : mArrListing) {
            if (listingFull.getListing().getId().equalsIgnoreCase(listingId)) {
                if (b) {
                    listingFull.setInQueue(true);
                    mFastItemAdapter.notifyItemChanged(mArrListing.indexOf(listingFull));
                } else {
                    listingFull.setInQueue(false);
                    mFastItemAdapter.notifyItemChanged(mArrListing.indexOf(listingFull));
                }
            }
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_tab_schedule;
    }

    @Override
    public void pickSchedule(ListingFullItem listing) {
        mListener.pickSchedule(convertListing(listing.getListing()));
    }

    @Override
    public void bookSingle(String id) {
        Intent intent = new Intent(getContext(), BookSingleActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
    }

    @Override
    public void getListingPrePageSuccess(ArrayList<ListingFull> listingFulls) {
        mProgressItem.clear();
        ArrayList<ConsumerListingSchedule> listingFullItems = CurrentListingSchedule.getInstance().getArrListing();
        if (listingFulls.size() == 0 && mPage == 1) {
            mIsEnd = true;
            mLayoutEmpty.setVisibility(View.VISIBLE);
            mRvSchedule.setVisibility(View.GONE);
        } else {
            mLayoutEmpty.setVisibility(View.GONE);
            mRvSchedule.setVisibility(View.VISIBLE);
            if (listingFulls.size() < 10) {
                mIsEnd = true;
            }
            for (int i = 0; i < listingFulls.size(); i++) {
                ListingFullItem listingFullItem = new ListingFullItem();
                for (int j = 0; j < listingFullItems.size(); j++) {
                    if (listingFulls.get(i).getId().equalsIgnoreCase(listingFullItems.get(j).getListing().getId())) {
                        listingFullItem.setInQueue(true);
                    }
                }
                listingFullItem.setListing(listingFulls.get(i));
                ScheduleListingItem scheduleListingItem = new ScheduleListingItem();
                scheduleListingItem.setContext(getActivity());
                scheduleListingItem.setListing(listingFullItem);
                scheduleListingItem.setListener(this);
                mFastItemAdapter.add(scheduleListingItem);
                mArrListing.add(listingFullItem);
            }
        }
    }

    @Override
    public void getListingPrePageFail() {
        mProgressItem.clear();
    }

    public Listing convertListing(ListingFull listingFull) {
        Listing listing = new Listing();
        listing.setId(listingFull.getId());
        listing.setLkey(listingFull.getLkey());
        listing.setStatus(listingFull.getStatus());
        listing.setListingType(listingFull.getListingType());
        listing.setSaleType(listingFull.getSaleType());
        listing.setPropertyType(listingFull.getPropertyType());
        listing.setBedrooms(listingFull.getBedrooms());
        listing.setBathrooms(listingFull.getBathrooms());
        listing.setUrl(listingFull.getUrl());
        listing.setDirectionUrl(listingFull.getDirectionUrl());
        listing.setDescription(listingFull.getDescription());
        listing.setYearBuilt(listingFull.getYearBuilt());
        listing.setPool(listingFull.getPool());
        listing.setGarage(listingFull.getGarage());
        listing.setAddress(listingFull.getAddress());
        listing.setPrice(listingFull.getPrice());
        listing.setLivingSquare(listingFull.getLivingSquare());
        listing.setLotSize(listingFull.getLotSize());
        listing.setListingImages(listingFull.getListingImages());
        return listing;
    }

    public ListingFull convertListing(Listing listingFull) {
        ListingFull listing = new ListingFull();
        listing.setId(listingFull.getId());
        listing.setLkey(listingFull.getLkey());
        listing.setStatus(listingFull.getStatus());
        listing.setListingType(listingFull.getListingType());
        listing.setSaleType(listingFull.getSaleType());
        listing.setPropertyType(listingFull.getPropertyType());
        listing.setBedrooms(listingFull.getBedrooms());
        listing.setBathrooms(listingFull.getBathrooms());
        listing.setUrl(listingFull.getUrl());
        listing.setDirectionUrl(listingFull.getDirectionUrl());
        listing.setDescription(listingFull.getDescription());
        listing.setYearBuilt(listingFull.getYearBuilt());
        listing.setPool(listingFull.getPool());
        listing.setGarage(listingFull.getGarage());
        listing.setAddress(listingFull.getAddress());
        listing.setPrice(listingFull.getPrice());
        listing.setLivingSquare(listingFull.getLivingSquare());
        listing.setLotSize(listingFull.getLotSize());
        listing.setListingImages(listingFull.getListingImages());
        return listing;
    }

    @Override
    public void getFavoriteSuccess(ArrayList<Listing> listings) {
        mProgressItem.clear();
        ArrayList<ConsumerListingSchedule> listingFullItems = CurrentListingSchedule.getInstance().getArrListing();
        if (listings.size() == 0 && mPage == 1) {
            mIsEnd = true;
            mLayoutEmpty.setVisibility(View.VISIBLE);
            mRvSchedule.setVisibility(View.GONE);
        } else {
            mLayoutEmpty.setVisibility(View.GONE);
            mRvSchedule.setVisibility(View.VISIBLE);
            if (listings.size() < 10) {
                mIsEnd = true;
            }
            for (int i = 0; i < listings.size(); i++) {
                ListingFullItem listingFullItem = new ListingFullItem();
                for (int j = 0; j < listingFullItems.size(); j++) {
                    if (listings.get(i).getId().equalsIgnoreCase(listingFullItems.get(j).getListing().getId())) {
                        listingFullItem.setInQueue(true);
                    }
                }
                listingFullItem.setListing(convertListing(listings.get(i)));
                ScheduleListingItem scheduleListingItem = new ScheduleListingItem();
                scheduleListingItem.setContext(getActivity());
                scheduleListingItem.setListing(listingFullItem);
                scheduleListingItem.setListener(this);
                mFastItemAdapter.add(scheduleListingItem);
                mArrListing.add(listingFullItem);
            }
        }
    }

    @Override
    public void getFavoriteFail(String message) {

    }

    @Override
    public void getFavoriteFail(@StringRes int message) {

    }
}
