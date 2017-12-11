package com.homecaravan.android.consumer.consumerteamtabsearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.adapter.FeaturedAdapter;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.consumermvp.loginmvp.GetFeaturedPresenter;
import com.homecaravan.android.consumer.consumermvp.loginmvp.GetFeaturedView;
import com.homecaravan.android.consumer.consumerteamtabsearch.featuredmvp.IFeaturedView;
import com.homecaravan.android.consumer.listener.IContactManager;
import com.homecaravan.android.consumer.listener.ITeamSearchListener;
import com.homecaravan.android.consumer.model.ConsumerTeam;
import com.homecaravan.android.consumer.model.responseapi.ResponseFeatured;

import java.util.ArrayList;

import butterknife.Bind;


public class FragmentFeatured extends BaseFragment implements IFeaturedView, GetFeaturedView, FeaturedAdapter.SelectAgent {
    private int mOldPosition = -1;
    private int mCurrentPosition = -1;
    private GetFeaturedPresenter mGetFeaturedPresenter;
    private FeaturedAdapter mFeaturedAdapter;
    private ArrayList<ResponseFeatured.Featured> mArrTeam = new ArrayList<>();
    private ITeamSearchListener mListener;
    private IContactManager mContactListener;

    public void setListener(ITeamSearchListener mListener) {
        this.mListener = mListener;
    }

    public void setContactListener(IContactManager mContactListener) {
        this.mContactListener = mContactListener;
    }

    @Bind(R.id.rvFeatured)
    RecyclerView mRvFeatured;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFeaturedAdapter = new FeaturedAdapter(getActivity(), mArrTeam, this);
        mRvFeatured.setLayoutManager(createLayoutManager());
        mRvFeatured.setAdapter(mFeaturedAdapter);
        setupMvp();
    }

    public void setupMvp() {
        mGetFeaturedPresenter = new GetFeaturedPresenter(this);
        mGetFeaturedPresenter.getFeature();

    }

    public LinearLayoutManager createLayoutManager() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }

    @Override
    public void showFeatured(ArrayList<ConsumerTeam> teams) {

    }

    @Override
    public void showError(String message) {

    }

    public void beginSearch(String query) {
        if (mFeaturedAdapter != null)
            mFeaturedAdapter.getFilter().filter(query);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_featured;
    }

    @Override
    public void getFeaturedSuccess(ArrayList<ResponseFeatured.Featured> arrFeatured) {
        mArrTeam.clear();
        for (int i = 0; i < arrFeatured.size(); i++) {
            mArrTeam.add(arrFeatured.get(i));
        }
        mFeaturedAdapter.notifyDataSetChanged();
    }

    @Override
    public void selectAgent(ResponseFeatured.Featured agent, boolean b, int position) {
        mOldPosition = mCurrentPosition;
        mCurrentPosition = position;
        mListener.pickAgent(agent, b);
        if (mOldPosition != -1) {
            mArrTeam.get(mOldPosition).setSelect(false);
            mFeaturedAdapter.notifyItemChanged(mOldPosition);
        }
        if (mCurrentPosition != -1) {
            mListener.pickAgentChangeTab();
            mArrTeam.get(mCurrentPosition).setSelect(true);
            mFeaturedAdapter.notifyItemChanged(mCurrentPosition);
        }
    }

    public void clearSelect() {
        if (mCurrentPosition != -1) {
            mArrTeam.get(mCurrentPosition).setSelect(false);
            mFeaturedAdapter.notifyItemChanged(mCurrentPosition);
        }
    }
}
