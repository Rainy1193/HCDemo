package com.homecaravan.android.consumer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.adapter.ParticipantSearchAdapter;
import com.homecaravan.android.consumer.base.BaseActivity;
import com.homecaravan.android.consumer.consumermvp.caravanmvp.GetRecentPresenter;
import com.homecaravan.android.consumer.consumermvp.caravanmvp.GetRecentView;
import com.homecaravan.android.consumer.consumermvp.searchmvp.AddParticipantSearchPresenter;
import com.homecaravan.android.consumer.consumermvp.searchmvp.AddParticipantSearchView;
import com.homecaravan.android.consumer.consumermvp.searchmvp.RemoveParticipantSearchPresenter;
import com.homecaravan.android.consumer.consumermvp.searchmvp.RemoveParticipantSearchView;
import com.homecaravan.android.consumer.model.EventUpdateSearch;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.model.listitem.ParticipantSearchItem;
import com.homecaravan.android.consumer.model.responseapi.CaravanParticipants;
import com.homecaravan.android.consumer.model.responseapi.Participant;
import com.homecaravan.android.consumer.model.responseapi.SearchDetail;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.homecaravan.android.handling.ValidateData;
import com.homecaravan.android.models.CurrentSaveSearch;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class InviteAgentActivity extends BaseActivity implements GetRecentView, ParticipantSearchAdapter.IPickParticipant,
        RemoveParticipantSearchView, AddParticipantSearchView {

    private ParticipantSearchAdapter mAdapter;
    private int mPosition = 1, mPosition1 = 1, mPosition2 = 1, mOldPosition = 1, mOldPosition1 = 1, mOldPosition2 = 1;
    private String mStatus = "";
    private String mPermission = "admin";
    private GetRecentPresenter mGetRecentPresenter;
    private RemoveParticipantSearchPresenter mRemoveParticipantPresenter;
    private AddParticipantSearchPresenter mAddParticipantPresenter;
    private ArrayList<ParticipantSearchItem> mArrParticipant;
    private int mPositionUpdate;
    @Bind(R.id.scrollView)
    ScrollView mScrollView;
    @Bind(R.id.rvAgent)
    RecyclerView mRvAgent;
    @Bind(R.id.tvAdmin)
    TextView mTvAdmin;
    @Bind(R.id.layoutAdmin)
    RelativeLayout mLayoutAdmin;
    @Bind(R.id.tvMember)
    TextView mTvMember;
    @Bind(R.id.layoutMember)
    RelativeLayout mLayoutMember;
    @Bind(R.id.tvReadonly)
    TextView mTvReadonly;
    @Bind(R.id.layoutReadonly)
    RelativeLayout mLayoutReadonly;
    @Bind(R.id.vEmail)
    View mVEmail;
    @Bind(R.id.tvEmail)
    TextView mTvEmail;
    @Bind(R.id.vPhone)
    View mVPhone;
    @Bind(R.id.tvPhone)
    TextView mTvPhone;
    @Bind(R.id.vBoth)
    View mVBoth;
    @Bind(R.id.tvBoth)
    TextView mTvBoth;
    @Bind(R.id.layoutEmail)
    LinearLayout mLayoutEmail;
    @Bind(R.id.layoutPhone)
    LinearLayout mLayoutPhone;
    @Bind(R.id.layoutBoth)
    LinearLayout mLayoutBoth;

    @Bind(R.id.etFirstName)
    MaterialEditText mFirstName;
    @Bind(R.id.etLastName)
    MaterialEditText mLastName;
    @Bind(R.id.etEmail)
    MaterialEditText mEmail;
    @Bind(R.id.etPhone)
    MaterialEditText mPhone;
    @Bind(R.id.rlInvite)
    RelativeLayout mLayoutInvite;

    @OnClick(R.id.ivBack)
    public void back() {
        onBackPressed();
    }

    @OnClick(R.id.ivCheck)
    public void check() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @OnClick(R.id.rlInvite)
    public void invite() {
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
        mLayoutInvite.setClickable(false);
        showLoading();
        mAddParticipantPresenter.addParticipant(mFirstName.getText().toString(), mLastName.getText().toString(), mEmail.getText().toString(),
                mPhone.getText().toString(), mPermission, "1", CurrentSaveSearch.getInstance().getId(), "");
    }


    @OnClick(R.id.layoutAdmin)
    public void onMLayoutAdminClicked(View view) {
        mOldPosition1 = mPosition1;
        mPosition1 = 1;
        mPermission = "admin";
        if (mOldPosition1 == 1) {
            return;
        }
        if (mOldPosition1 == 2) {
            changeButton(mLayoutMember, mTvMember, false);
        }
        if (mOldPosition1 == 3) {
            changeButton(mLayoutReadonly, mTvReadonly, false);
        }
        changeButton(mLayoutAdmin, mTvAdmin, true);
    }

    @OnClick(R.id.layoutMember)
    public void onMLayoutMemberClicked(View view) {
        mOldPosition1 = mPosition1;
        mPosition1 = 2;
        mPermission = "member";
        if (mOldPosition1 == 2) {
            return;
        }
        if (mOldPosition1 == 1) {
            changeButton(mLayoutAdmin, mTvAdmin, false);
        }
        if (mOldPosition1 == 3) {
            changeButton(mLayoutReadonly, mTvReadonly, false);
        }
        changeButton(mLayoutMember, mTvMember, true);
    }

    @OnClick(R.id.layoutReadonly)
    public void onMLayoutReadonlyClicked(View view) {
        mOldPosition1 = mPosition1;
        mPosition1 = 3;
        mPermission = "readonly";
        if (mOldPosition1 == 3) {
            return;
        }
        if (mOldPosition1 == 1) {
            changeButton(mLayoutAdmin, mTvAdmin, false);
        }
        if (mOldPosition1 == 2) {
            changeButton(mLayoutMember, mTvMember, false);
        }
        changeButton(mLayoutReadonly, mTvReadonly, true);
    }

    @OnClick(R.id.layoutEmail)
    public void onMLayoutEmailClicked(View view) {
        mOldPosition2 = mPosition2;
        mPosition2 = 1;
        if (mOldPosition2 == 1) {
            return;
        }
        if (mOldPosition2 == 2) {
            changeButtonWithAnimationType2(mVPhone, mTvPhone, false);
            changeStatusAndDisableClick(mLayoutPhone, true);
        }
        if (mOldPosition2 == 3) {
            changeButtonWithAnimationType2(mVBoth, mTvBoth, false);
            changeStatusAndDisableClick(mLayoutBoth, true);
        }
        changeButtonWithAnimationType2(mVEmail, mTvEmail, true);
        changeStatusAndDisableClick(mLayoutEmail, true);
    }

    @OnClick(R.id.layoutPhone)
    public void onMLayoutPhoneClicked(View view) {
        mOldPosition2 = mPosition2;
        mPosition2 = 2;
        if (mOldPosition2 == 2) {
            return;
        }
        if (mOldPosition2 == 1) {
            changeButtonWithAnimationType2(mVEmail, mTvEmail, false);
            changeStatusAndDisableClick(mLayoutEmail, true);
        }
        if (mOldPosition2 == 3) {
            changeButtonWithAnimationType2(mVBoth, mTvBoth, false);
            changeStatusAndDisableClick(mLayoutBoth, true);
        }
        changeButtonWithAnimationType2(mVPhone, mTvPhone, true);
        changeStatusAndDisableClick(mLayoutPhone, true);
    }

    @OnClick(R.id.layoutBoth)
    public void onMLayoutBothClicked(View view) {
        mOldPosition2 = mPosition2;
        mPosition2 = 3;
        if (mOldPosition2 == 3) {
            return;
        }
        if (mOldPosition2 == 1) {
            changeButtonWithAnimationType2(mVEmail, mTvEmail, false);
            changeStatusAndDisableClick(mLayoutEmail, true);
        }
        if (mOldPosition2 == 2) {
            changeButtonWithAnimationType2(mVPhone, mTvPhone, false);
            changeStatusAndDisableClick(mLayoutPhone, true);
        }
        changeButtonWithAnimationType2(mVBoth, mTvBoth, true);
        changeStatusAndDisableClick(mLayoutBoth, true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getExtras() != null) {
            mStatus = getIntent().getExtras().getString("status");
        }
        mArrParticipant = new ArrayList<>();
        mAdapter = new ParticipantSearchAdapter(this, mArrParticipant, this);
        mGetRecentPresenter = new GetRecentPresenter(this);
        mRemoveParticipantPresenter = new RemoveParticipantSearchPresenter(this);
        mAddParticipantPresenter = new AddParticipantSearchPresenter(this);
        mRvAgent.setAdapter(mAdapter);
        mRvAgent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRvAgent.setItemAnimator(null);
        mGetRecentPresenter.getRecent();

    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_invite_agent_consumer;
    }


    public void changeButton(View view, TextView textView, boolean b) {
        changeButtonWithAnimation(view, textView, b);
        changeStatusAndDisableClick(view, b);
    }

    public void changeButtonWithAnimation(View layout, TextView tv, boolean isClick) {
        int fromColor;
        int toColor;
        int fromBg;
        int toBg;

        if (isClick) {
            fromColor = R.color.colorTextMain;
            toColor = R.color.colorWhite;
            fromBg = R.drawable.bg_button_filter1;
            toBg = R.drawable.bg_button_filter;
        } else {
            fromColor = R.color.colorWhite;
            toColor = R.color.colorTextMain;
            fromBg = R.drawable.bg_button_filter;
            toBg = R.drawable.bg_button_filter1;
        }
        AnimUtils.changeTextColor(this, fromColor, toColor, tv);
        AnimUtils.changeBackgroundDrawable(this, fromBg, toBg, layout);
    }

    public void changeButtonWithAnimationType2(View layout, TextView tv, boolean isClick) {
        int fromColor;
        int toColor;
        int fromBg;
        int toBg;

        if (isClick) {
            fromColor = R.color.colorTextMain;
            toColor = R.color.color_text_light_blue_filter;
            fromBg = R.drawable.bg_button_filter2;
            toBg = R.drawable.bg_button_filter;
        } else {
            fromColor = R.color.color_text_light_blue_filter;
            toColor = R.color.colorTextMain;
            fromBg = R.drawable.bg_button_filter;
            toBg = R.drawable.bg_button_filter2;
        }
        AnimUtils.changeTextColor(this, fromColor, toColor, tv);
        AnimUtils.changeBackgroundDrawable(this, fromBg, toBg, layout);
    }

    public void changeStatusAndDisableClick(final View view, boolean b) {
        view.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(true);
            }
        }, 200);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_close_activity_left, R.anim.anim_close_activity_right);
    }


    @Override
    public void getRecentSuccess(ArrayList<CaravanParticipants> participants) {
        ArrayList<Participant> arrParticipants = CurrentSaveSearch.getInstance().getArrParticipant();
        for (int i = 0; i < participants.size(); i++) {
            ParticipantSearchItem participantSearchItem = new ParticipantSearchItem();
            participantSearchItem.setId(participants.get(i).getId());
            participantSearchItem.setFirstName(participants.get(i).getFirstName());
            participantSearchItem.setLastName(participants.get(i).getLastName());
            participantSearchItem.setAvatar(participants.get(i).getAvatar());
            participantSearchItem.setPnUid(participants.get(i).getPnUid());
            participantSearchItem.setPnTid(participants.get(i).getPnTid());
            for (int j = 0; j < arrParticipants.size(); j++) {
                if (participants.get(i).getId().equalsIgnoreCase(arrParticipants.get(j).getId())) {
                    participantSearchItem.setPick(true);
                }
            }
            mArrParticipant.add(participantSearchItem);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getRecentFail() {

    }

    @Override
    public void pickUser(int position, boolean b) {
        mPositionUpdate = position;
        if (b) {
            showLoading();
            mAddParticipantPresenter.addParticipant(mArrParticipant.get(position).getFirstName(),
                    mArrParticipant.get(position).getLastName(),
                    mArrParticipant.get(position).getEmail(),
                    mArrParticipant.get(position).getPhone(),
                    mPermission,
                    "1",
                    CurrentSaveSearch.getInstance().getId(),
                    mArrParticipant.get(position).getId());
        } else {
            mRemoveParticipantPresenter.removeParticipant(CurrentSaveSearch.getInstance().getId(), mArrParticipant.get(position).getId());
        }
    }

    @Override
    public void removeParticipantSuccess(SearchDetail searchDetail) {
        EventUpdateSearch updateSearch = new EventUpdateSearch();
        updateSearch.setDelete(true);
        updateSearch.setIdParticipant(mArrParticipant.get(mPositionUpdate).getId());
        updateSearch.setIdSearch(CurrentSaveSearch.getInstance().getId());
        EventBus.getDefault().post(updateSearch);
        mArrParticipant.remove(mPositionUpdate);
    }

    @Override
    public void removeParticipantFail(String message) {
        showDialog(TypeDialog.WARNING, message, "removeParticipantFail");
    }

    @Override
    public void removeParticipantFail(@StringRes int message) {
        showDialog(TypeDialog.ERROR, message, "removeParticipantFail");
    }

    @Override
    public void addParticipantSuccess(SearchDetail searchDetail) {
        mLayoutInvite.setClickable(true);
        hideLoading();
        showDialog(TypeDialog.SUCCESS, R.string.invite_success, "addParticipantSuccess");
        Participant participant = searchDetail.getParticipants().getData().get(searchDetail.getParticipants().getData().size() - 1);
        ParticipantSearchItem participantSearchItem = new ParticipantSearchItem();
        participantSearchItem.setId(participant.getId());
        participantSearchItem.setFirstName(participant.getFirstName());
        participantSearchItem.setLastName(participant.getLastName());
        participantSearchItem.setAvatar(participant.getAvatar());
        participantSearchItem.setPnUid(participant.getPnUid());
        participantSearchItem.setPnTid(participant.getPnTid());
        participantSearchItem.setRole(participant.getRole());
        participantSearchItem.setWeight(participant.getWeight());
        participantSearchItem.setPick(true);
        mArrParticipant.add(participantSearchItem);
        mAdapter.notifyDataSetChanged();
        EventUpdateSearch updateSearch = new EventUpdateSearch();
        updateSearch.setIdParticipant(participant.getId());
        updateSearch.setDelete(false);
        updateSearch.setParticipant(participant);
        updateSearch.setIdSearch(CurrentSaveSearch.getInstance().getId());
        EventBus.getDefault().post(updateSearch);

    }

    @Override
    public void addParticipantFail(String message) {
        mLayoutInvite.setClickable(true);
        hideLoading();
        showDialog(TypeDialog.WARNING, message, "addParticipantFail");
    }

    @Override
    public void addParticipantFail(@StringRes int message) {
        mLayoutInvite.setClickable(true);
        hideLoading();
        showDialog(TypeDialog.ERROR, message, "addParticipantFail");
    }
}
