package com.homecaravan.android.consumer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.ExclusiveAgent;
import com.homecaravan.android.ui.CircleImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExclusiveAgentAdapter extends RecyclerView.Adapter<ExclusiveAgentAdapter.ExclusiveAgentHolder> {
    private ArrayList<ExclusiveAgent> mAgents = new ArrayList<>();
    private Context mContext;
    private ExclusiveAgentListener mListener;

    public ExclusiveAgentAdapter(ArrayList<ExclusiveAgent> mAgents, Context mContext, ExclusiveAgentListener mListener) {
        this.mAgents = mAgents;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @Override
    public ExclusiveAgentHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.exclusive_agent_item, null, false);
        return new ExclusiveAgentHolder(view);
    }

    @Override
    public void onBindViewHolder(final ExclusiveAgentHolder exclusiveAgentHolder, final int i) {
        exclusiveAgentHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAgents.get(i).isSelect()) {
                    mAgents.get(i).setSelect(false);
                    mListener.selectAgent(i, false);
                    exclusiveAgentHolder.mIvPick.setVisibility(View.GONE);
                } else {
                    mAgents.get(i).setSelect(true);
                    mListener.selectAgent(i, true);
                    exclusiveAgentHolder.mIvPick.setVisibility(View.VISIBLE);
                }
            }
        });
        if (mAgents.get(i).isSelect()) {
            exclusiveAgentHolder.mIvPick.setVisibility(View.VISIBLE);
        } else {
            exclusiveAgentHolder.mIvPick.setVisibility(View.GONE);
        }
        Glide.with(mContext).load(mAgents.get(i).getAvatar()).asBitmap().centerCrop().into(exclusiveAgentHolder.mIvAgent);
        exclusiveAgentHolder.mTvId.setText(mAgents.get(i).getId());
        exclusiveAgentHolder.mTvRole.setText(mAgents.get(i).getRole());
        exclusiveAgentHolder.mTvName.setText(mAgents.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return mAgents.size();
    }

    public class ExclusiveAgentHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivAgent)
        public CircleImageView mIvAgent;
        @Bind(R.id.tvName)
        public TextView mTvName;
        @Bind(R.id.tvId)
        public TextView mTvId;
        @Bind(R.id.tvRole)
        public TextView mTvRole;
        @Bind(R.id.ivPick)
        ImageView mIvPick;

        public ExclusiveAgentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface ExclusiveAgentListener {
        void selectAgent(int position, boolean b);
    }
}
