package com.homecaravan.android.consumer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.ConsumerTeam;
import com.homecaravan.android.ui.CircleImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class AgentDashboardAdapter extends RecyclerView.Adapter<AgentDashboardAdapter.AgentAdapter> {
    public Context mContext;
    public ArrayList<ConsumerTeam> mArrTeam;

    public AgentDashboardAdapter(Context mContext, ArrayList<ConsumerTeam> mArrTeam) {
        this.mContext = mContext;
        this.mArrTeam = mArrTeam;
    }


    @Override
    public AgentAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.agent_item, null, false);
        return new AgentAdapter(view);
    }

    @Override
    public void onBindViewHolder(AgentAdapter holder, int position) {
        Glide.with(mContext.getApplicationContext()).load(mArrTeam.get(position).getPhoto()).asBitmap().fitCenter().placeholder(R.drawable.avatar_default).dontAnimate().into(holder.mIvAgent);
    }

    @Override
    public int getItemCount() {
        return mArrTeam.size();
    }

    public class AgentAdapter extends RecyclerView.ViewHolder {

        @Bind(R.id.ivAgent)
        CircleImageView mIvAgent;

        public AgentAdapter(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
