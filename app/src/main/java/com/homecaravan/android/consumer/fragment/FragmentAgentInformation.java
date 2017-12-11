package com.homecaravan.android.consumer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.consumerdata.ConsumerTeamData;
import com.homecaravan.android.consumer.model.ConsumerTeam;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.Bind;

public class FragmentAgentInformation extends BaseFragment {

    @Bind(R.id.imgAvatarRealtor)
    RoundedImageView mIvAgent;
    @Bind(R.id.tvAgentName)
    TextView mAgentName;
    @Bind(R.id.imgLogoCompany)
    ImageView mLogoCompany;
    @Bind(R.id.tvAgentPhone)
    TextView mAgentPhone;
    @Bind(R.id.tvAgentEmail)
    TextView mEmailAgent;
    @Bind(R.id.tvAgentAddress)
    TextView mAgentAddress;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void initAgentInformation(String agentId) {
        ArrayList<ConsumerTeam> consumerAgents = ConsumerTeamData.getInstance().mData.getTeams();
        for (int i = 0; i < consumerAgents.size(); i++) {
            if (consumerAgents.get(i).getId().equalsIgnoreCase(agentId)) {
                ConsumerTeam agent = consumerAgents.get(i);
                Glide.with(getActivity()).load(agent.getPhoto()).asBitmap().fitCenter().into(mIvAgent);
                mAgentName.setText(agent.getFirstName() + " " + agent.getLastName());
                Glide.with(getActivity()).load(agent.getLogo()).asBitmap().fitCenter().into(mLogoCompany);
                mAgentPhone.setText(agent.getPhone());
                mEmailAgent.setText(agent.getEmail());
                mAgentAddress.setText(agent.getCompanyAddress1());
            }
        }

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_agent_profile_infomation;
    }
}
