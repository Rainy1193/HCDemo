package com.homecaravan.android.consumer.consumerdiscover;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.activity.InviteAgentActivity;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.consumermvp.searchmvp.GetListSearchPresenter;
import com.homecaravan.android.consumer.consumermvp.searchmvp.GetListSearchView;
import com.homecaravan.android.consumer.fastadapter.SaveSearchItem;
import com.homecaravan.android.consumer.listener.IAddAgentListener;
import com.homecaravan.android.consumer.listener.IOpenSavedSearchDetail;
import com.homecaravan.android.consumer.model.EventUpdateSearch;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.model.responseapi.ResponseAllSearch;
import com.homecaravan.android.consumer.model.responseapi.Search;
import com.mikepenz.fastadapter.adapters.FooterAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter_extensions.items.ProgressItem;
import com.mikepenz.fastadapter_extensions.scroll.EndlessRecyclerOnScrollListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;

public class FragmentOpenSavedSearch extends BaseFragment implements IAddAgentListener,
        IOpenSavedSearchDetail, GetListSearchView {

    private int REQUEST_ADD_AGENT = 1;
    private IOpenSavedSearchDetail mListener;
    private GetListSearchPresenter mGetListSearchPresenter;
    private FastItemAdapter<SaveSearchItem> mAdapter;
    private FooterAdapter<ProgressItem> mLoadMore;
    private int mPage = 0;
    private boolean mIsEnd;
    private int mPositionUpdate;
    @Bind(R.id.rvSavedSearch)
    RecyclerView mRecyclerView;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.layoutMain)
    RelativeLayout mLayoutMain;

    public void setListener(IOpenSavedSearchDetail mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGetListSearchPresenter = new GetListSearchPresenter(this);
        mAdapter = new FastItemAdapter<>();
        mLoadMore = new FooterAdapter<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mLoadMore.wrap(mAdapter));
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLoadMore) {
            @Override
            public void onLoadMore(int currentPage) {
                if (!mIsEnd) {
                    mLoadMore.clear();
                    mLoadMore.add(new ProgressItem().withEnabled(false));
                    mPage++;
                    mGetListSearchPresenter.getListSearch(String.valueOf(mPage), "", "", "");
                }
            }
        });
        EventBus.getDefault().register(this);
    }

    public void initData() {
        mProgressBar.setVisibility(View.VISIBLE);
        mGetListSearchPresenter.getListSearch("1", "", "", "");
    }

    @Override
    public void addAgent(int position) {
        mPositionUpdate = position;
        Intent intent = new Intent(getActivity(), InviteAgentActivity.class);
        startActivityForResult(intent, REQUEST_ADD_AGENT);
        getActivity().overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_AGENT) {
            if (resultCode == Activity.RESULT_OK) {

            }
        }
    }


    @Override
    public void openSavedSearchDetail(String searchId) {
        mListener.openSavedSearchDetail(searchId);
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_open_saved_search;
    }

    @Override
    public void getListSearchSuccess(ResponseAllSearch.ResponseAllSearchData searchData) {
        mProgressBar.setVisibility(View.GONE);
        ArrayList<Search> arrSearch = searchData.getArrSearch();
        if (arrSearch.size() < 10) {
            mIsEnd = true;
        }
        for (int i = 0; i < arrSearch.size(); i++) {
            SaveSearchItem saveSearchItem = new SaveSearchItem();
            saveSearchItem.setContext(getActivity());
            saveSearchItem.setListener(this);
            saveSearchItem.setOpenListener(this);
            saveSearchItem.setSearch(arrSearch.get(i));
            saveSearchItem.setPosition(i);
            mAdapter.add(saveSearchItem);
        }

    }

    @Override
    public void getListSearchFail(String message) {
        mIsEnd = false;
        mLoadMore.clear();
        mProgressBar.setVisibility(View.GONE);
        showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "getListSaveSearchFail");
    }

    @Override
    public void getListSearchFail(@StringRes int message) {
        mIsEnd = false;
        mLoadMore.clear();
        mProgressBar.setVisibility(View.GONE);
        showSnackBar(mLayoutMain, TypeDialog.ERROR, message, "getListSaveSearchFail");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onReloadSaveSearch(EventUpdateSearch updateSearch) {
        if (!updateSearch.isChangeName()) {
            if (updateSearch.isDelete()) {
                Search search = mAdapter.getAdapterItem(mPositionUpdate).getSearch();
                search.getParticipants().getData().remove(mPositionUpdate);
                SaveSearchItem saveSearchItem = mAdapter.getAdapterItem(mPositionUpdate);
                saveSearchItem.setSearch(search);
                saveSearchItem.updateAgent();
            } else {
                Search search = mAdapter.getAdapterItem(mPositionUpdate).getSearch();
                search.getParticipants().getData().add(updateSearch.getParticipant());
                SaveSearchItem saveSearchItem = mAdapter.getAdapterItem(mPositionUpdate);
                saveSearchItem.setSearch(search);
                saveSearchItem.updateAgent();
            }
        } else {
            Search search = mAdapter.getAdapterItem(mPositionUpdate).getSearch();
            SaveSearchItem saveSearchItem = mAdapter.getAdapterItem(mPositionUpdate);
            saveSearchItem.setSearch(search);
            saveSearchItem.updateNameSearch();
        }
    }


    public void removeData() {
        mRecyclerView.removeAllViews();
        mLoadMore.clear();
        mIsEnd = false;
        mPage = 0;
        mAdapter.clear();
        mAdapter.notifyDataSetChanged();
    }
}
