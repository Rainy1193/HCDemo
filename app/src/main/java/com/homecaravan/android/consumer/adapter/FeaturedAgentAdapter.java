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
import com.homecaravan.android.consumer.listener.IAgentListener;
import com.homecaravan.android.consumer.model.BaseDataRecyclerView;
import com.homecaravan.android.consumer.model.HeaderRvData;
import com.homecaravan.android.consumer.model.ViewAllRvData;
import com.homecaravan.android.consumer.model.responseapi.ResponseFeatured;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FeaturedAgentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_HEADER = 1;
    private final int VIEW_ITEM = 0;
    private final int VIEW_ALL = 2;
    private final ThreadLocal<ArrayList<BaseDataRecyclerView>> mArrTeam = new ThreadLocal<>();
    private Context mContext;
    private IAgentListener mListener;

    public FeaturedAgentAdapter(Context mContext, ArrayList<BaseDataRecyclerView> mArrTeam, IAgentListener mListener) {
        this.mContext = mContext;
        this.mArrTeam.set(mArrTeam);
        this.mListener = mListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_agent_item, parent, false);
            vh = new AgentHolder(v);
        } else if (viewType == VIEW_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_agent_header_item, parent, false);
            vh = new HeaderHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_item, parent, false);
            vh = new ViewAllHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HeaderHolder) {
            HeaderHolder headerHolder = (HeaderHolder) holder;
            headerHolder.mNumberListing.setText(String.valueOf(mArrTeam.get().size() - 2));
            headerHolder.mLayoutHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.openMyTeam();
                }
            });
        } else if (holder instanceof ViewAllHolder) {
            ViewAllHolder viewAllHolder = (ViewAllHolder) holder;
            viewAllHolder.mLayoutViewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.openMyTeam();
                }
            });
            viewAllHolder.mNumViewAll.setText(String.valueOf(mArrTeam.get().size() - 2));
        } else {
            final ResponseFeatured.Featured agent = (ResponseFeatured.Featured) mArrTeam.get().get(position);
            AgentHolder agentHolder = (AgentHolder) holder;
            Glide.with(mContext.getApplicationContext()).load(agent.getAvatar())
                    .asBitmap().fitCenter().placeholder(R.drawable.avatar_default)
                    .dontAnimate().into(agentHolder.mAvatarAgent);
            agentHolder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.openAgent(agent);
                }
            });
            if (!agent.getCompany().isEmpty()) {
                agentHolder.mAgentCompany.setText(agent.getCompany().get(0).getName());
                Glide.with(mContext.getApplicationContext()).load(agent.getCompany().get(0).getLogo())
                        .asBitmap().fitCenter().into(agentHolder.mLogoCompany);
            }
            agentHolder.mAgentName.setText(agent.getFirstName() + " " + agent.getLastName());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mArrTeam.get().get(position) instanceof ResponseFeatured.Featured) {
            return VIEW_ITEM;
        } else if (mArrTeam.get().get(position) instanceof HeaderRvData) {
            return VIEW_HEADER;
        } else if (mArrTeam.get().get(position) instanceof ViewAllRvData) {
            return VIEW_ALL;
        } else {
            throw new RuntimeException("ItemViewType unknown");
        }
    }

    @Override
    public int getItemCount() {
        return mArrTeam.get().size();
    }

    public class AgentHolder extends RecyclerView.ViewHolder {

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

        public AgentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvNumberListing)
        TextView mNumberListing;
        @Bind(R.id.layoutHeader)
        RelativeLayout mLayoutHeader;

        public HeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
