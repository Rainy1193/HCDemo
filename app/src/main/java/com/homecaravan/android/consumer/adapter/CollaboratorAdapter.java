package com.homecaravan.android.consumer.adapter;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.listener.ICollaboratorListener;
import com.homecaravan.android.consumer.model.Collaborator;
import com.homecaravan.android.consumer.model.ConsumerAgent;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CollaboratorAdapter extends RecyclerView.Adapter<CollaboratorAdapter.CollaboratorHolder> {

    private ArrayList<Collaborator> mCollaborators;
    private Context mContext;
    private ICollaboratorListener mListener;

    public CollaboratorAdapter(ArrayList<Collaborator> mCollaborators, Context mContext, ICollaboratorListener mListener) {
        this.mCollaborators = mCollaborators;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @Override
    public CollaboratorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.collaborator_item, null, false);
        return new CollaboratorHolder(view);
    }

    @Override
    public void onBindViewHolder(CollaboratorHolder holder, final int position) {
        ConsumerAgent agent = mCollaborators.get(position).getAgent();
        if (mCollaborators.get(position).isCollaborator()) {
            Glide.with(mContext.getApplicationContext()).load(agent.getPhoto()).asBitmap().fitCenter().placeholder(R.drawable.avatar_default).dontAnimate().into(holder.mIvAgent);
            holder.mAgentName.setText(agent.getFirstName());
            holder.mAgentRole.setText(agent.getRoleId());
            holder.mLayoutSendMessage.setVisibility(View.VISIBLE);
            holder.mLayoutEdit.setVisibility(View.VISIBLE);
            holder.mAgentRole.setVisibility(View.VISIBLE);
            holder.mLayoutSendMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.pickAgent(mCollaborators.get(position));
                }
            });
        } else {
            holder.mLayoutSendMessage.setVisibility(View.INVISIBLE);
            holder.mLayoutEdit.setVisibility(View.INVISIBLE);
            holder.mAgentRole.setVisibility(View.INVISIBLE);
            holder.mAgentName.setTextColor(ContextCompat.getColor(mContext, R.color.colorMenuConsumer));
            holder.mAgentName.setText("Add collaborator");
            holder.mIvAgent.setImageResource(R.drawable.ic_add_collaborator);
            holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.addColl();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mCollaborators.size();
    }

    public class CollaboratorHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.layoutSendMessage)
        LinearLayout mLayoutSendMessage;
        @Bind(R.id.layoutEdit)
        LinearLayout mLayoutEdit;
        @Bind(R.id.agentName)
        TextView mAgentName;
        @Bind(R.id.agentRole)
        TextView mAgentRole;
        @Bind(R.id.ivAgent)
        RoundedImageView mIvAgent;
        @Bind(R.id.layoutItem)
        RelativeLayout mLayoutItem;

        public CollaboratorHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
