package com.homecaravan.android.consumer.consumerteamtabsearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.adapter.ContactAdapter;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.consumermvp.contactmvp.GetListContactPresenter;
import com.homecaravan.android.consumer.consumermvp.contactmvp.GetListContactView;
import com.homecaravan.android.consumer.listener.ITeamSearchListener;
import com.homecaravan.android.consumer.model.responseapi.ContactData;

import java.util.ArrayList;

import butterknife.Bind;


public class FragmentContact extends BaseFragment implements GetListContactView, ContactAdapter.SelectAgent {
    private int mOldPosition = -1;
    private int mCurrentPosition = -1;
    private ContactAdapter mContactAdapter;
    private ArrayList<ContactData> mArrTeam = new ArrayList<>();
    private GetListContactPresenter mGetListContactPresenter;
    private ITeamSearchListener mListener;
    @Bind(R.id.rvContact)
    RecyclerView mRvContact;

    public void setListener(ITeamSearchListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContactAdapter = new ContactAdapter(getActivity(), mArrTeam, this);
        mRvContact.setLayoutManager(createLayoutManager());
        mRvContact.setAdapter(mContactAdapter);
        setupMvp();
    }

    public void setupMvp() {
        mGetListContactPresenter = new GetListContactPresenter(this);
        mGetListContactPresenter.getListContact();
    }

    public LinearLayoutManager createLayoutManager() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }


    public void beginSearch(String query) {
        if (mContactAdapter != null)
            mContactAdapter.getFilter().filter(query);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_contact;
    }


    @Override
    public void getListContactSuccess(ArrayList<ContactData> data) {
        for (int i = 0; i < data.size(); i++) {
            mArrTeam.add(data.get(i));
        }
        mContactAdapter.notifyDataSetChanged();
    }

    @Override
    public void getListContactFail(@StringRes int message) {

    }

    @Override
    public void getListContactFail(String message) {

    }

    @Override
    public void selectAgent(ContactData contact, boolean b, int position) {
        mOldPosition = mCurrentPosition;
        mCurrentPosition = position;
        mListener.pickAgent(contact, b);
        if (mOldPosition != -1) {
            mArrTeam.get(mOldPosition).setSelect(false);
            mContactAdapter.notifyItemChanged(mOldPosition);
        }
        if (mCurrentPosition != -1) {
            mListener.pickAgentChangeTab();
            mArrTeam.get(mCurrentPosition).setSelect(true);
            mContactAdapter.notifyItemChanged(mCurrentPosition);
        }
    }

    public void clearSelect() {
        if (mCurrentPosition != -1) {
            mArrTeam.get(mCurrentPosition).setSelect(false);
            mContactAdapter.notifyItemChanged(mCurrentPosition);
        }
    }
}
