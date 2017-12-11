package com.homecaravan.android.consumer.consumershedule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.adapter.ScheduleAgentAdapter;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.consumermvp.caravanmvp.AddParticipantPresenter;
import com.homecaravan.android.consumer.consumermvp.caravanmvp.AddParticipantView;
import com.homecaravan.android.consumer.consumermvp.caravanmvp.GetRecentPresenter;
import com.homecaravan.android.consumer.consumermvp.caravanmvp.GetRecentView;
import com.homecaravan.android.consumer.consumermvp.caravanmvp.RemoveParticipantPresenter;
import com.homecaravan.android.consumer.consumermvp.caravanmvp.RemoveParticipantView;
import com.homecaravan.android.consumer.model.CurrentCaravan;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.model.listitem.ParticipantItem;
import com.homecaravan.android.consumer.model.responseapi.CaravanParticipants;
import com.homecaravan.android.handling.ValidateData;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTouch;

public class FragmentStepInviteOther extends BaseFragment implements ScheduleAgentAdapter.IPickAgent, GetRecentView, AddParticipantView, RemoveParticipantView {

    private ArrayList<CaravanParticipants> mArrParticipant = new ArrayList<>();
    private ArrayList<ParticipantItem> mArrParticipantItem = new ArrayList<>();
    private GetRecentPresenter mGetRecentPresenter;
    private RemoveParticipantPresenter mRemoveParticipantPresenter;
    private AddParticipantPresenter mAddParticipantPresenter;
    private ScheduleAgentAdapter mAdapter;
    private String mIdParticipant = "";
    @Bind(R.id.rvAgent)
    RecyclerView mRvAgent;
    @Bind(R.id.etFirstName)
    MaterialEditText mFirstName;
    @Bind(R.id.etLastName)
    MaterialEditText mLastName;
    @Bind(R.id.etEmail)
    MaterialEditText mEmail;
    @Bind(R.id.etPhone)
    MaterialEditText mPhone;
    @Bind(R.id.layoutSendInvite)
    FrameLayout mLayoutSendInvite;

    @OnTouch(R.id.scrollView)
    public boolean onTouchScrollView(MotionEvent event) {
        hideKeyboard();
        return false;
    }

    @OnClick(R.id.layoutSendInvite)
    public void sendInvite() {
        hideKeyboard();

        if (mFirstName.getText().toString().trim().trim().isEmpty()) {
            showDialog(TypeDialog.WARNING, R.string.error_first_name, "validate");
            mFirstName.requestFocus();
            return;
        }

        if (mLastName.getText().toString().trim().trim().isEmpty()) {
            showDialog(TypeDialog.WARNING, R.string.error_last_name, "validate");
            mLastName.requestFocus();
            return;
        }

        if (!ValidateData.isEmailValid(mEmail.getText().toString().trim())) {
            showDialog(TypeDialog.WARNING, R.string.error_email, "validate");
            mEmail.requestFocus();
            return;
        }
        if (!ValidateData.isPhone(mPhone.getText().toString().trim())) {
            showDialog(TypeDialog.WARNING, R.string.error_phone, "validate");
            mPhone.requestFocus();
            return;
        }

        if (!isNetworkConnected()) {
            showDialog(TypeDialog.WARNING, R.string.no_network, "no-internet");
            return;
        }
        mLayoutSendInvite.setClickable(false);
        showLoading();
        mAddParticipantPresenter.addParticipant(CurrentCaravan.getCaravan().getData().getId(), "", mEmail.getText().toString(), mPhone.getText().toString(), "",
                mFirstName.getText().toString(), mLastName.getText().toString(), "");
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGetRecentPresenter = new GetRecentPresenter(this);
        mAddParticipantPresenter = new AddParticipantPresenter(this);
        mRemoveParticipantPresenter = new RemoveParticipantPresenter(this);
        mGetRecentPresenter.getRecent();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_step_invite_other;
    }


    @Override
    public void pickAgent(int position, boolean b) {
        mArrParticipantItem.get(position).setPick(b);
        mAdapter.notifyItemChanged(position);
        if (b) {
            mIdParticipant = mArrParticipantItem.get(position).getParticipant().getId();
            mAddParticipantPresenter.addParticipant(CurrentCaravan.getCaravan().getData().getId(), mIdParticipant, "", "", "", "", "", "");
        } else {
            mRemoveParticipantPresenter.removeParticipant(CurrentCaravan.getCaravan().getData().getId(), mIdParticipant);
            mIdParticipant = "";
        }
    }

    @Override
    public void getRecentSuccess(ArrayList<CaravanParticipants> participants) {

        mArrParticipant.addAll(participants);
        for (int i = 0; i < mArrParticipant.size(); i++) {
            mArrParticipantItem.add(new ParticipantItem(mArrParticipant.get(i), false));
        }
        mAdapter = new ScheduleAgentAdapter(mArrParticipantItem, getActivity(), this);
        mRvAgent.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRvAgent.setAdapter(mAdapter);
        mRvAgent.setItemAnimator(null);
    }

    @Override
    public void getRecentFail() {

    }

    @Override
    public void addParticipantSuccess(ArrayList<CaravanParticipants> participants) {

        hideLoading();
        //showDialog(TypeDialog.SUCCESS, R.string.add_participant_ok, "addParticipantSuccess");
        mLayoutSendInvite.setClickable(true);
        CaravanParticipants participant = participants.get(participants.size() - 1);
        mArrParticipant.add(participant);
        mArrParticipantItem.add(new ParticipantItem(participant, false));
        mAdapter.notifyDataSetChanged();
        mFirstName.setText("");
        mLastName.setText("");
        mEmail.setText("");
        mPhone.setText("");
    }

    @Override
    public void addParticipantFail() {
        hideLoading();
        showDialog(TypeDialog.WARNING, R.string.add_participant_fail, "addParticipantFail");
        mLayoutSendInvite.setClickable(true);
    }

    @Override
    public void addParticipantFail(@StringRes int message) {
        hideLoading();
        showDialog(TypeDialog.ERROR, message, "addParticipantFail");
    }

    @Override
    public void removeParticipantSuccess(ArrayList<CaravanParticipants> participants) {

    }

    @Override
    public void removeParticipantFail() {
//        hideLoading();
//        showDialog(TypeDialog.WARNING, R.string.remove_participant_fail, "removeParticipantFail");
    }

    @Override
    public void removeParticipantFail(@StringRes int message) {
//        hideLoading();
//        showDialog(TypeDialog.ERROR, message, "removeParticipantFail");
    }
}
