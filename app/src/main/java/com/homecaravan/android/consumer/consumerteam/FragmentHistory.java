package com.homecaravan.android.consumer.consumerteam;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.adapter.HistoryAdapter;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.consumerteam.historymvp.HistoryHelper;
import com.homecaravan.android.consumer.consumerteam.historymvp.HistoryPresenter;
import com.homecaravan.android.consumer.consumerteam.historymvp.IHistoryView;
import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Anh Dao on 7/20/2017.
 */

public class FragmentHistory extends BaseFragment implements IHistoryView {

    private HistoryAdapter mHistoryAdapter;
    private ArrayList<ConsumerTeam> mArrTeam = new ArrayList<>();
    private ArrayList<ConsumerTeam> mArrTeamFilter = new ArrayList<>();

    //mvp
    private HistoryHelper mHistoryHelper;
    private HistoryPresenter mHistoryPresenter;


    @Bind(R.id.rvHistory)
    RecyclerView mRvHistory;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHistoryAdapter = new HistoryAdapter(getActivity(), mArrTeamFilter);
        mRvHistory.setLayoutManager(createLayoutManager());
        mRvHistory.setAdapter(mHistoryAdapter);
        setupMvp();
    }

    private void setupMvp(){
        mHistoryHelper = new HistoryHelper();
        mHistoryPresenter = new HistoryPresenter(mHistoryHelper, this);
        mHistoryPresenter.getHistory();
    }

    public LinearLayoutManager createLayoutManager() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }

    public void filter(String id) {
        // filter
        mArrTeamFilter.clear();
        for (ConsumerTeam consumerTeam : mArrTeam){
            if(consumerTeam.getId().equals(id)){
                mArrTeamFilter.add(consumerTeam);
            }
        }
        mHistoryAdapter.notifyDataSetChanged();
    }

    public void setFullHistory(){
        mArrTeamFilter.clear();
        mArrTeamFilter.addAll(mArrTeam);
        mHistoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showHistory(ArrayList<ConsumerTeam> teams) {
        mArrTeam.clear();
        mArrTeam.addAll(teams);
        mArrTeamFilter.addAll(teams);
        mHistoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_history;
    }
}
