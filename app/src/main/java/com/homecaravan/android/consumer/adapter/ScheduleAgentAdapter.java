package com.homecaravan.android.consumer.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.listitem.ParticipantItem;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ScheduleAgentAdapter extends RecyclerView.Adapter<ScheduleAgentAdapter.ScheduleAgentHolder> {
    private ArrayList<ParticipantItem> mArrAgent;
    private Context mContext;
    private IPickAgent mListener;

    public ScheduleAgentAdapter(ArrayList<ParticipantItem> mArrAgent, Context mContext, IPickAgent mListener) {
        this.mArrAgent = mArrAgent;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @Override
    public ScheduleAgentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.agent_schedule_item, parent, false);
        return new ScheduleAgentHolder(view);
    }

    @Override
    public void onBindViewHolder(final ScheduleAgentHolder holder, final int position) {
        Glide.with(mContext.getApplicationContext()).load(mArrAgent.get(position).getParticipant().getAvatar()).asBitmap().fitCenter().placeholder(R.drawable.avatar_default).dontAnimate().into(holder.mIvAgent);
        holder.mAgentName.setText(mArrAgent.get(position).getParticipant().getFirstName() + mArrAgent.get(position).getParticipant().getLastName());
        if (mArrAgent.get(position).isPick()) {
            holder.mLayoutBg.setVisibility(View.VISIBLE);
        } else {
            holder.mLayoutBg.setVisibility(View.INVISIBLE);
        }
        holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mArrAgent.get(position).isPick()) {
                    mListener.pickAgent(position, false);
                    mArrAgent.get(position).setPick(false);
                    AnimUtils.scaleView(holder.mLayoutBg, 1f, 0f, 1f, 0f);
                } else {
                    mListener.pickAgent(position, true);
                    mArrAgent.get(position).setPick(true);
                    AnimUtils.scaleView(holder.mLayoutBg, 0f, 1f, 0f, 1f);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrAgent.size();
    }

    public class ScheduleAgentHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.ivAgent)
        RoundedImageView mIvAgent;
        @Bind(R.id.layoutBg)
        RelativeLayout mLayoutBg;
        @Bind(R.id.agentName)
        TextView mAgentName;
        @Bind(R.id.layoutItem)
        RelativeLayout mLayoutItem;

        public ScheduleAgentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface IPickAgent {
        void pickAgent(int position, boolean b);
    }
}
