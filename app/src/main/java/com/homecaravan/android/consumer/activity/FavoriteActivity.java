package com.homecaravan.android.consumer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.base.BaseActivity;
import com.homecaravan.android.consumer.consumermvp.searchmvp.GetFavoriteListingPresenter;
import com.homecaravan.android.consumer.consumermvp.searchmvp.GetFavoriteListingView;
import com.homecaravan.android.consumer.fastadapter.FavoriteItem;
import com.homecaravan.android.consumer.model.EventListingDetail;
import com.homecaravan.android.consumer.model.responseapi.Listing;
import com.mikepenz.fastadapter.adapters.FooterAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter_extensions.items.ProgressItem;
import com.mikepenz.fastadapter_extensions.scroll.EndlessRecyclerOnScrollListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class FavoriteActivity extends BaseActivity implements GetFavoriteListingView, FavoriteItem.FavoriteListener {
    private GetFavoriteListingPresenter mGetFavoriteListingPresenter;
    private FastItemAdapter<FavoriteItem> mAdapter;
    private FooterAdapter<ProgressItem> mLoadMore;
    private int mPage = 1;
    private boolean mIsEnd;
    @Bind(R.id.rvFavorite)
    RecyclerView mRvFavorite;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.layoutEmpty)
    LinearLayout mLayoutEmpty;

    @OnClick(R.id.ivBack)
    public void back() {
        finish();
        overridePendingTransition(R.anim.anim_close_activity_left, R.anim.anim_close_activity_right);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGetFavoriteListingPresenter = new GetFavoriteListingPresenter(this);
        mGetFavoriteListingPresenter.getFavorite(String.valueOf(mPage));
        mAdapter = new FastItemAdapter<>();
        mLoadMore = new FooterAdapter<>();
        mRvFavorite.setLayoutManager(new LinearLayoutManager(this));
        mRvFavorite.setAdapter(mLoadMore.wrap(mAdapter));
        mRvFavorite.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLoadMore) {
            @Override
            public void onLoadMore(int currentPage) {
                if (!mIsEnd) {
                    mLoadMore.clear();
                    mLoadMore.add(new ProgressItem().withEnabled(false));
                    mPage++;
                    mGetFavoriteListingPresenter.getFavorite(String.valueOf(mPage));
                }
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_favorite_consumer;
    }

    @Override
    public void getFavoriteSuccess(ArrayList<Listing> listings) {
        mLayoutEmpty.setVisibility(View.GONE);
        mLoadMore.clear();
        mProgressBar.setVisibility(View.GONE);
        if (listings.size() == 0 && mPage == 1) {
            mLayoutEmpty.setVisibility(View.VISIBLE);
            mIsEnd = true;
            return;
        }
        mRvFavorite.setVisibility(View.VISIBLE);
        for (Listing listing : listings) {
            FavoriteItem favoriteItem = new FavoriteItem();
            favoriteItem.setContext(this);
            favoriteItem.setPosition(mAdapter.getItemCount());
            favoriteItem.setListener(this);
            favoriteItem.setListing(listing);
            mAdapter.add(favoriteItem);
        }

    }

    @Override
    public void getFavoriteFail(String message) {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void getFavoriteFail(@StringRes int message) {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public void openListingDetail(String id) {
        finish();
        EventBus.getDefault().post(new EventListingDetail(id));
    }

    @Override
    public void removeFavorite(int position) {
        mAdapter.remove(position);
    }
}
