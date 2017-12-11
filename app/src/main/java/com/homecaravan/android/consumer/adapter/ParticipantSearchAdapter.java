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
import com.homecaravan.android.consumer.model.listitem.ParticipantSearchItem;
import com.homecaravan.android.ui.CircleImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ParticipantSearchAdapter extends RecyclerView.Adapter<ParticipantSearchAdapter.AgentHolder> {
    private Context mContext;
    private ArrayList<ParticipantSearchItem> mArrParticipant;
    private IPickParticipant mListener;

    public ParticipantSearchAdapter(Context mContext, ArrayList<ParticipantSearchItem> mArrParticipant, IPickParticipant mListener) {
        this.mContext = mContext;
        this.mArrParticipant = mArrParticipant;
        this.mListener = mListener;
    }

    @Override
    public AgentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.participant_search_item, null, false);
        return new AgentHolder(view);
    }

    @Override
    public void onBindViewHolder(final AgentHolder holder, final int position) {
        Glide.with(mContext.getApplicationContext()).load(mArrParticipant.get(position).getAvatar()).asBitmap().fitCenter().placeholder(R.drawable.avatar_default).dontAnimate().into(holder.mIvAgent);
        holder.mTvNameAgent.setText(mArrParticipant.get(position).getFirstName() + mArrParticipant.get(position).getLastName());
        holder.mTvRoleAgent.setText(mArrParticipant.get(position).getRole());
        holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mArrParticipant.get(position).isPick()) {
                    mListener.pickUser(position, false);
                    mArrParticipant.get(position).setPick(false);
                    holder.mLayoutCheck.setVisibility(View.INVISIBLE);
                    //AnimUtils.scaleView(holder.mLayoutCheck, 1f, 0f, 1f, 0f);
                } else {
                    mListener.pickUser(position, true);
                    mArrParticipant.get(position).setPick(true);
                    holder.mLayoutCheck.setVisibility(View.VISIBLE);
                    //AnimUtils.scaleView(holder.mLayoutCheck, 0f, 1f, 0f, 1f);
                }
            }
        });
        if (mArrParticipant.get(position).isPick()) {
            holder.mLayoutCheck.setVisibility(View.VISIBLE);
        } else {
            holder.mLayoutCheck.setVisibility(View.INVISIBLE);
        }

    }


    @Override
    public int getItemCount() {
        return mArrParticipant.size();
    }

    public class AgentHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.layoutCheck)
        RelativeLayout mLayoutCheck;
        @Bind(R.id.ivAgent)
        CircleImageView mIvAgent;
        @Bind(R.id.agentName)
        TextView mTvNameAgent;
        @Bind(R.id.agentRole)
        TextView mTvRoleAgent;
        @Bind(R.id.layoutItem)
        RelativeLayout mLayoutItem;

        public AgentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface IPickParticipant {
        void pickUser(int position, boolean b);
    }
}
