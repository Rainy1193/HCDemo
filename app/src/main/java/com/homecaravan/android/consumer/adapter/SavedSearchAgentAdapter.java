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
import com.homecaravan.android.consumer.model.responseapi.Participant;
import com.homecaravan.android.ui.CircleImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedSearchAgentAdapter extends RecyclerView.Adapter<SavedSearchAgentAdapter.SavedSearchAgentHolder> {
    private ArrayList<Participant> mParticipants;
    private Context mContext;

    public SavedSearchAgentAdapter(Context mContext, ArrayList<Participant> mArrAgent) {
        this.mParticipants = mArrAgent;
        this.mContext = mContext;
    }

    @Override
    public SavedSearchAgentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.saved_search_agent_item, null, false);
        return new SavedSearchAgentHolder(view);
    }

    @Override
    public void onBindViewHolder(SavedSearchAgentHolder holder, int position) {
        Glide.with(mContext.getApplicationContext()).load(mParticipants.get(position).getAvatar()).asBitmap().fitCenter().placeholder(R.drawable.avatar_default).dontAnimate().into(holder.mIvAgent);
        holder.mTvNameAgent.setText(mParticipants.get(position).getFirstName());
        holder.mTvRoleAgent.setText(mParticipants.get(position).getRole());
        holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mParticipants.size();
    }

    public class SavedSearchAgentHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivAgent)
        CircleImageView mIvAgent;
        @Bind(R.id.agentName)
        TextView mTvNameAgent;
        @Bind(R.id.agentRole)
        TextView mTvRoleAgent;
        @Bind(R.id.layoutItem)
        RelativeLayout mLayoutItem;

        public SavedSearchAgentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
