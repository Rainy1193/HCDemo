package com.homecaravan.android.consumer.consumershedule;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.activity.ActivitySelectAgent;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.consumerbase.ConsumerUser;
import com.homecaravan.android.consumer.listener.IScheduleListener;
import com.homecaravan.android.consumer.model.ConsumerTeam;
import com.homecaravan.android.consumer.model.CurrentListingSchedule;
import com.homecaravan.android.consumer.utils.Utils;
import com.homecaravan.android.consumer.widget.CustomNestedScrollView;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.Bind;
import butterknife.OnClick;

public class FragmentStepSelectAgent extends BaseFragment {
    @Bind(R.id.tvNumberListingCaravan)
    TextView mNumberListingCaravan;
    @Bind(R.id.tvMeetSeveralAgent)
    TextView mTvMeetSeveralAgent;
    @Bind(R.id.layoutMeetSeveralAgents)
    LinearLayout mLayoutMeetSeveralAgents;
    @Bind(R.id.tvOneLocalExpert)
    TextView mTvOneLocalExpert;
    @Bind(R.id.layoutOneLocalExpert)
    LinearLayout mLayoutOneLocalExpert;
    @Bind(R.id.tvMeetListingAgent)
    TextView mTvMeetListingAgent;
    @Bind(R.id.layoutMeetListingAgents)
    LinearLayout mLayoutMeetListingAgents;
    @Bind(R.id.tvLoyalAgent)
    TextView mTvLoyalAgent;
    @Bind(R.id.layoutLoyalAgent)
    LinearLayout mLayoutLoyalAgent;
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

    @OnClick(R.id.layoutMeetSeveralAgents)
    public void onMLayoutMeetSeveralAgentsClicked(View view) {
        mOldSelect = mCurrentSelect;
        mCurrentSelect = 1;
        updateSelect(view);
    }

    @OnClick(R.id.layoutOneLocalExpert)
    public void onMLayoutOneLocalExpertClicked(View view) {
        mOldSelect = mCurrentSelect;
        mCurrentSelect = 2;
        updateSelect(view);
    }

    @OnClick(R.id.layoutMeetListingAgents)
    public void onMLayoutMeetListingAgentsClicked(View view) {
        mOldSelect = mCurrentSelect;
        mCurrentSelect = 3;
        updateSelect(view);
    }

    @OnClick(R.id.layoutLoyalAgent)
    public void onMLayoutLoyalAgentClicked(View view) {
        mOldSelect = mCurrentSelect;
        mCurrentSelect = 4;
        updateSelect(view);
        Intent intent = new Intent(getActivity(), ActivitySelectAgent.class);
        startActivityForResult(intent, 1);
        getActivity().overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNumberListingCaravan.setText("You have " + String.valueOf(CurrentListingSchedule.getInstance().getArrListing().size()) + " listings in this Caravan");
        String s1 = getActivity().getResources().getString(R.string.select_agent_tab2);
        String s2 = getActivity().getResources().getString(R.string.select_agent_tab3);
        String s3 = getActivity().getResources().getString(R.string.select_agent_tab4);
        String s4 = getActivity().getResources().getString(R.string.select_agent_tab5);
        mTvMeetSeveralAgent.setText(Utils.getTextSelectAgent
                (getActivity(), s1, s1.indexOf("meet several agents"), s1.indexOf("meet several agents") + 19));
        mTvOneLocalExpert.setText(Utils.getTextSelectAgent
                (getActivity(), s2, s2.indexOf("one local expert"), s2.length()));
        mTvMeetListingAgent.setText(Utils.getTextSelectAgent
                (getActivity(), s3, s3.indexOf("meet listing agents"), s3.length()));
        mTvLoyalAgent.setText(Utils.getTextSelectAgent
                (getActivity(), s4, s4.indexOf("loyal to an agent"), s4.indexOf("loyal to an agent") + 17));
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
        mListener.showNextWhenSelectAgent();
        view.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_option_selected_agent));
        if (mOldSelect == 1) {
            mLayoutMeetSeveralAgents.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_option_select_agent));
        }
        if (mOldSelect == 2) {
            mLayoutOneLocalExpert.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_option_select_agent));
        }
        if (mOldSelect == 3) {
            mLayoutMeetListingAgents.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_option_select_agent));
        }
        if (mOldSelect == 4) {
            mLayoutLoyalAgent.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_option_select_agent));
        }
    }

    public void resetSelect() {
        mLayoutMeetSeveralAgents.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_option_select_agent));
        mLayoutOneLocalExpert.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_option_select_agent));
        mLayoutMeetListingAgents.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_option_select_agent));
        mLayoutLoyalAgent.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_option_select_agent));
        mOldSelect = -1;
        mCurrentSelect = -1;
    }

    public int getCurrentSelect() {
        return mCurrentSelect;
    }
}
