package com.homecaravan.android.consumer.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.ConsumerSelectAgent;
import com.homecaravan.android.consumer.model.ConsumerTeam;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectAgentAdapter extends RecyclerView.Adapter<SelectAgentAdapter.SelectAgentHolder> {
    private Context mContext;
    private ArrayList<ConsumerSelectAgent> mArrAgent;
    private SelectAgentListener mListener;

    public SelectAgentAdapter(Context mContext, ArrayList<ConsumerSelectAgent> mArrAgent, SelectAgentListener mListener) {
        this.mContext = mContext;
        this.mArrAgent = mArrAgent;
        this.mListener = mListener;
    }

    @Override
    public SelectAgentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.select_agent_item, parent, false);
        return new SelectAgentHolder(view);
    }

    @Override
    public void onBindViewHolder(final SelectAgentHolder holder, final int position) {
        ConsumerTeam agent = mArrAgent.get(position).getAgent();
        Glide.with(mContext.getApplicationContext()).load(agent.getLogo())
                .asBitmap().fitCenter().into(holder.mLogoCompany);
        Glide.with(mContext.getApplicationContext()).load(agent.getPhoto())
                .asBitmap().fitCenter().placeholder(R.drawable.avatar_default)
                .dontAnimate().into(holder.mAvatarAgent);
        holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mArrAgent.get(position).isPick()) {
                    mListener.selectAgent(position);
                    AnimUtils.scaleView(holder.mLayoutBg, 1f, 0f, 1f, 0f);
                } else {
                    mListener.selectAgent(position);
                    AnimUtils.scaleView(holder.mLayoutBg, 0f, 1f, 0f, 1f);
                }
            }
        });
        if (mArrAgent.get(position).isPick()) {
            holder.mLayoutBg.setVisibility(View.VISIBLE);
        } else {
            holder.mLayoutBg.setVisibility(View.GONE);
        }
        holder.mAgentName.setText(agent.getFirstName() + " " + agent.getLastName());
        holder.mAgentCompany.setText(agent.getCompany());
    }

    @Override
    public int getItemCount() {
        return mArrAgent.size();
    }

    public void updateList(ArrayList<ConsumerSelectAgent> agents) {
        this.mArrAgent = agents;
        notifyDataSetChanged();
    }

    public class SelectAgentHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.layoutItem)
        RelativeLayout mLayoutItem;
        @Bind(R.id.ivLogoCompany)
        ImageView mLogoCompany;
        @Bind(R.id.ivAgent)
        RoundedImageView mAvatarAgent;
        @Bind(R.id.agentName)
        TextView mAgentName;
        @Bind(R.id.agentCompany)
        TextView mAgentCompany;
        @Bind(R.id.layoutBg)
        RelativeLayout mLayoutBg;

        public SelectAgentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface SelectAgentListener {
        void selectAgent(int position);
    }
}
