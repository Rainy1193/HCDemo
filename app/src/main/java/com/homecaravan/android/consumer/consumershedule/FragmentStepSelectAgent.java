package com.homecaravan.android.consumer.consumershedule;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.activity.ActivitySelectAgent;
import com.homecaravan.android.consumer.adapter.ExclusiveAgentAdapter;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.consumerbase.ConsumerUser;
import com.homecaravan.android.consumer.listener.IScheduleListener;
import com.homecaravan.android.consumer.model.ConsumerTeam;
import com.homecaravan.android.consumer.model.CurrentListingSchedule;
import com.homecaravan.android.consumer.model.ExclusiveAgent;
import com.homecaravan.android.consumer.widget.CustomNestedScrollView;
import com.homecaravan.android.ui.CustomLinearLayoutManager;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class FragmentStepSelectAgent extends BaseFragment implements ExclusiveAgentAdapter.ExclusiveAgentListener {
    private ExclusiveAgentAdapter mAdapter;
    private ArrayList<ExclusiveAgent> mAgents = new ArrayList<>();
    private int mCurrentPosition = -1;
    private int mOldPosition = -1;
    @Bind(R.id.rvAgent)
    RecyclerView mRvAgent;
    @Bind(R.id.tvNumberListingCaravan)
    TextView mNumberListingCaravan;
    @Bind(R.id.layoutIntroducedAgent)
    LinearLayout mLayoutIntroducedAgent;
    @Bind(R.id.layoutExclusiveAgent)
    LinearLayout mLayoutExclusiveAgent;
    @Bind(R.id.tvMeetListingAgent)
    TextView mTvMeetListingAgent;
    @Bind(R.id.tvLoyalAgent)
    TextView mTvLoyalAgent;
    @Bind(R.id.scrollView)
    CustomNestedScrollView mScrollView;
    @Bind(R.id.layoutSelectAgent)
    LinearLayout mLayoutSelectAgent;
    @Bind(R.id.ivAgent)
    RoundedImageView mIvAgent;
    @Bind(R.id.tvNameAgent)
    TextView mNameAgent;
    @Bind(R.id.tvCompanyAgent)
    TextView mCompanyAgent;
    @Bind(R.id.ivLogoCompany)
    ImageView mLogoCompany;
    @Bind(R.id.tvPhoneAgent)
    TextView mPhoneAgent;
    @Bind(R.id.tvEmailAgent)
    TextView mEmailAgent;
    @Bind(R.id.tvAddressAgent)
    TextView mAddressAgent;
    private IScheduleListener mListener;

    private int mCurrentSelect = -1;
    private int mOldSelect = -1;
    private ExclusiveAgent mAgent;

    public ExclusiveAgent getAgent() {
        return mAgent;
    }

    public void setListener(IScheduleListener mListener) {
        this.mListener = mListener;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_step_select_agent;
    }

    @OnClick(R.id.ivActionAgent)
    public void actionAgent() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ConsumerUser.getInstance().getData().setAgent(null);
                        mLayoutSelectAgent.setVisibility(View.VISIBLE);
                        mScrollView.setVisibility(View.INVISIBLE);
                    }
                })
                .setNeutralButton("Pick Other", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getActivity(), ActivitySelectAgent.class);
                        startActivityForResult(intent, 1);
                        getActivity().overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
                    }
                })
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
        alertDialog.show();
    }

    @OnClick(R.id.layoutIntroducedAgent)
    public void onIntroducedAgentClicked(View view) {
        mOldSelect = mCurrentSelect;
        mCurrentSelect = 1;
        for (int i = 0; i < mAgents.size(); i++) {
            mAgents.get(i).setSelect(false);
        }
        mAdapter.notifyDataSetChanged();
        updateSelect(view);
        mAgent = null;
    }

    @OnClick(R.id.layoutExclusiveAgent)
    public void onExclusiveAgentClicked(View view) {
        mOldSelect = mCurrentSelect;

        mLayoutSelectAgent.setVisibility(View.GONE);
        mRvAgent.setVisibility(View.VISIBLE);
        if (mCurrentPosition != -1) {
            mListener.showNextWhenSelectAgent(true);
        } else {
            mListener.showNextWhenSelectAgent(false);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNumberListingCaravan.setText("You have " + String.valueOf(CurrentListingSchedule.getInstance().getArrListing().size()) + " listings in this Caravan");
        mAgents.add(new ExclusiveAgent(R.drawable.photo_agent, "Christine Dicarlo", "License: 01290192", "Real Estate Broker", false));
        mAgents.add(new ExclusiveAgent(R.drawable.photo_agent_1, "Christi Magnusen", "License: 01947674", "Agent", false));
        mAgents.add(new ExclusiveAgent(R.drawable.photo_agent_2, "Regan Brooke", "License: 01987554", "Agent", false));
        mAgents.add(new ExclusiveAgent(R.drawable.photo_agent_3, "Neelam Molnar", "License: 01936384", "Agent", false));
        mAdapter = new ExclusiveAgentAdapter(mAgents, getContext(), this);
        mRvAgent.setAdapter(mAdapter);
        mRvAgent.setLayoutManager(new CustomLinearLayoutManager(getContext()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                setInformationAgent();
            }
        }
    }

    public void setInformationAgent() {
        ConsumerTeam consumerTeam = ConsumerUser.getInstance().getData().getAgent();
        if (consumerTeam != null) {
            mLayoutSelectAgent.setVisibility(View.INVISIBLE);
            mScrollView.setVisibility(View.VISIBLE);
            Glide.with(getActivity().getApplicationContext()).load(consumerTeam.getPhoto()).asBitmap().fitCenter().into(mIvAgent);
            Glide.with(getActivity().getApplicationContext()).load(consumerTeam.getLogo()).asBitmap().fitCenter().into(mLogoCompany);
            mNameAgent.setText(consumerTeam.getFirstName() + " " + consumerTeam.getLastName());
            mCompanyAgent.setText(consumerTeam.getCompany());
            mEmailAgent.setText(consumerTeam.getEmail());
            mPhoneAgent.setText(consumerTeam.getPhone());
            mAddressAgent.setText(consumerTeam.getCompanyAddress1());
        } else {
            mLayoutSelectAgent.setVisibility(View.VISIBLE);
            mScrollView.setVisibility(View.INVISIBLE);
        }
    }

    public void updateSelect(View view) {
        mListener.showNextWhenSelectAgent(true);
        view.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_option_selected_agent));
        if (mOldSelect == 1) {
            if (mCurrentSelect == 1) {
                mListener.showNextWhenSelectAgent(false);
                mCurrentSelect = -1;
            }
            mLayoutIntroducedAgent.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_option_select_agent));
        }
        if (mOldSelect == 2) {
            mLayoutExclusiveAgent.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_option_select_agent));
        }
    }

    public void resetSelect() {
        mAgent = null;
        mOldSelect = -1;
        mCurrentSelect = -1;
        mListener.showNextWhenSelectAgent(false);
        mLayoutIntroducedAgent.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_option_select_agent));
        mLayoutExclusiveAgent.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_option_select_agent));
    }

    public int getCurrentSelect() {
        return mCurrentSelect;
    }

    public void hideExclusiveAgent() {
        mLayoutSelectAgent.setVisibility(View.VISIBLE);
        mRvAgent.setVisibility(View.GONE);
        for (int i = 0; i < mAgents.size(); i++) {
            if (mAgents.get(i).isSelect()) {
                mListener.showNextWhenSelectAgent(true);
            }
        }
        if (mCurrentSelect != -1) {
            mListener.showNextWhenSelectAgent(true);
        } else {
            mListener.showNextWhenSelectAgent(false);
        }
    }

    public boolean isShowExclusive() {
        return mRvAgent.getVisibility() == View.VISIBLE;
    }


    @Override
    public void selectAgent(int position, boolean b) {
        if (b) {
            mOldPosition = mCurrentPosition;
            mCurrentPosition = position;
            if (mOldPosition != -1) {
                mAgents.get(mOldPosition).setSelect(false);
            }
            if (mCurrentPosition != -1) {
                mAgents.get(mCurrentPosition).setSelect(true);
                mCurrentSelect = 2;
            }
            mAgent = mAgents.get(mCurrentPosition);
            mAdapter.notifyDataSetChanged();
            updateSelect(mLayoutExclusiveAgent);
        } else {
            if (position == mCurrentPosition) {
                mListener.showNextWhenSelectAgent(false);
                resetSelect();
            }
        }
    }
}
