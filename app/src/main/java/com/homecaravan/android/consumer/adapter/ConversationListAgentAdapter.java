package com.homecaravan.android.consumer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.message.MessageThread;
import com.homecaravan.android.ui.CircleImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ConversationListAgentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<MessageThread> mArrMessageThread;

    public ConversationListAgentAdapter(Context mContext, ArrayList<MessageThread> mArrMessageThread) {
        this.mContext = mContext;
        this.mArrMessageThread = mArrMessageThread;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_agent_item, parent, false);
        vh = new AgentHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AgentHolder contactHolder = (AgentHolder) holder;
        final MessageThread messageThread = mArrMessageThread.get(position);

        if(messageThread.getUserInThread() == null
                || messageThread.getUserInThread().isEmpty()){
            return;
        }

        Glide.with(mContext.getApplicationContext()).load(messageThread.getUserInThread().get(0).getAvatar()).asBitmap().fitCenter()
                .placeholder(R.drawable.avatar_default)
                .dontAnimate()
                .into(contactHolder.mImgAvatar);
    }

    @Override
    public int getItemCount() {
        return mArrMessageThread.size();
    }

    public class AgentHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.layoutItem)
        RelativeLayout mLayoutItem;
        @Bind(R.id.imgAvatar)
        CircleImageView mImgAvatar;
        @Bind(R.id.vStatus)
        View mVStatus;
        @Bind(R.id.viewBg)
        View mViewBg;

        public AgentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
