package com.homecaravan.android.consumer.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.activity.ConversationActivity;
import com.homecaravan.android.consumer.activity.FindContactActivity;
import com.homecaravan.android.consumer.adapter.MessageContactAdapterv2;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.message.messagecontactmvp.IMessageContactView;
import com.homecaravan.android.consumer.message.messagecontactmvp.MessageContactPresenter;
import com.homecaravan.android.consumer.message.messagegetthreadidmvp.GetThreadIdPresenter;
import com.homecaravan.android.consumer.message.messagegetthreadidmvp.GetThreadIdView;
import com.homecaravan.android.consumer.model.responseapi.ContactData;
import com.homecaravan.android.consumer.model.responseapi.ResponseMessageGetThreadId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Anh Dao on 11/24/2017.
 */

public class FragmentMessageContactv2 extends BaseFragment implements IMessageContactView, GetThreadIdView {

    private final String TAG = "DaoDiDem";
    private MessageContactAdapterv2 mMessageContactAdapter;
    private ArrayList<ContactData> mArrMessageContact = new ArrayList<>();
    //mvp
    private GetThreadIdPresenter mGetThreadIdPresenter;

    @Bind(R.id.layoutMessageContact)
    RelativeLayout mLayoutMessageContact;
    @Bind(R.id.pbLoadContact)
    ProgressBar mPbLoadContact;
    @Bind(R.id.rvMessageContact)
    RecyclerView mRvMessageContact;
    @Bind(R.id.layoutEmpty)
    LinearLayout mLayoutEmpty;


    @OnClick(R.id.fabNewContact)
    void onNewContactClicked(){
        startActivity(new Intent(getActivity(), FindContactActivity.class));
        getActivity().overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupAdapter();
        MessageContactPresenter mMessageContactPresenter = new MessageContactPresenter(this);
        mMessageContactPresenter.getMessageContact();

        mGetThreadIdPresenter = new GetThreadIdPresenter(this);
    }

    private void setupAdapter() {
        mMessageContactAdapter = new MessageContactAdapterv2(getActivity(), mArrMessageContact, HomeCaravanApplication.getInstance().buildPicasso());
        mRvMessageContact.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRvMessageContact.setAdapter(mMessageContactAdapter);

        mMessageContactAdapter.setOnClickListener(new MessageContactAdapterv2.OnItemClickListener() {
            @Override
            public void onItemClick(ContactData item) {
                mGetThreadIdPresenter.getThreadId("", "", "", item.getName(), item.getUser());
                showLoading();
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_message_contact_v2;
    }

    @Override
    public void getMessageContactSuccess(ArrayList<ContactData> mArrContact) {
        Collections.sort(mArrContact, new Comparator<ContactData>() {
            @Override
            public int compare(ContactData o1, ContactData o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        mArrMessageContact.clear();
        mArrMessageContact.addAll(mArrContact);

        if(mArrMessageContact.size() != 0){
            if(getActivity() == null)
                return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMessageContactAdapter.notifyDataSetChanged();
                    mLayoutEmpty.setVisibility(View.GONE);
                    mPbLoadContact.setVisibility(View.GONE);
                }
            });
        }else{
            if(getActivity() == null)
                return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLayoutEmpty.setVisibility(View.VISIBLE);
                    mPbLoadContact.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void getMessageContactFail(String message) {
//        showSnackBar(mLayoutMessageContact, TypeDialog.WARNING, message, "getMessageContactFail");
        mLayoutEmpty.setVisibility(View.VISIBLE);
        mPbLoadContact.setVisibility(View.GONE);
    }

    @Override
    public void serverError(@StringRes int message) {
//        showSnackBar(mLayoutMessageContact, TypeDialog.ERROR, message, "serverError");
        mLayoutEmpty.setVisibility(View.VISIBLE);
        mPbLoadContact.setVisibility(View.GONE);
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

    }

    @Override
    public void getThreadIdFail(@StringRes int message) {

    }
}
