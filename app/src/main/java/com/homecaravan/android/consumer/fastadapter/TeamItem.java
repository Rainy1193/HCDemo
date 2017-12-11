package com.homecaravan.android.consumer.fastadapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.listener.ICollaboratorListener;
import com.homecaravan.android.consumer.model.listitem.TeamSaveSearchItem;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TeamItem extends AbstractItem<TeamItem, TeamItem.TeamItemHolder> {

    private Context mContext;
    private TeamSaveSearchItem mTeam;
    private ICollaboratorListener mListener;

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public TeamSaveSearchItem getTeam() {
        return mTeam;
    }

    public void setTeam(TeamSaveSearchItem mTeam) {
        this.mTeam = mTeam;
    }

    public void setListener(ICollaboratorListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void bindView(TeamItemHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        if (mTeam.isCollaborator()) {
            if (mTeam.getParticipant().getAvatar() != null) {
                Glide.with(mContext.getApplicationContext()).load(mTeam.getParticipant().getAvatar()).asBitmap().fitCenter().placeholder(R.drawable.avatar_default).dontAnimate().into(holder.mIvAgent);
            }
            holder.mAgentName.setText(mTeam.getParticipant().getFullName());
            holder.mAgentRole.setText(mTeam.getParticipant().getRole());
            holder.mLayoutSendMessage.setVisibility(View.VISIBLE);
            holder.mAgentRole.setVisibility(View.VISIBLE);
            holder.mLayoutSendMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        } else {
            holder.mLayoutSendMessage.setVisibility(View.INVISIBLE);
            holder.mLayoutEdit.setVisibility(View.INVISIBLE);
            holder.mAgentRole.setVisibility(View.INVISIBLE);
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
    public int getType() {
        return R.id.team_save_search_item;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.collaborator_item;
    }

    @Override
    public TeamItemHolder getViewHolder(View v) {
        return new TeamItemHolder(v);
    }


    public static class TeamItemHolder extends RecyclerView.ViewHolder {
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

        public TeamItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
