package com.homecaravan.android.consumer.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.responseapi.ContactData;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.homecaravan.android.ui.CircleImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Anh Dao on 11/30/2017.
 */

public class SearchAgentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<ContactData> mArrContactData;
    private SetAgentView mListener;

    public SearchAgentAdapter(Context mContext, ArrayList<ContactData> mArrContactData, SetAgentView mListener) {
        this.mContext = mContext;
        this.mArrContactData = mArrContactData;
        this.mListener = mListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_agent_item, parent, false);
        vh = new SearchAgentHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ContactData agent = mArrContactData.get(position);
        final SearchAgentHolder myHolder = (SearchAgentHolder) holder;

        Glide.with(mContext.getApplicationContext()).load(agent.getAvatar()).asBitmap().fitCenter()
                .placeholder(R.drawable.avatar_default)
                .dontAnimate()
                .into(myHolder.mImgAvatar);

        myHolder.mTvName.setText(agent.getName());
        String str = agent.getEmail();
        if(str == null){
            str = agent.getPhone();
        }
        myHolder.mTvCompanyName.setText(str);

        myHolder.mRlButtonSetAgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tvSetAgent = (TextView) view.findViewById(R.id.tvSetAgent);
                changeButton(view, tvSetAgent);
                mListener.onSetAgent(agent);
            }
        });
    }

    private void changeButton(final View view, final TextView textView) {
        final int fromColor = R.color.teamtab_search_featured_text_button;
        final int toColor = R.color.colorWhite;
        final int fromBg = R.drawable.bg_button_set_agent_team_search;
        final int toBg = R.drawable.bg_button_set_agent_team_search_click;

        view.setEnabled(false);

        AnimUtils.changeTextColor(mContext, fromColor, toColor, textView);
        AnimUtils.changeBackgroundDrawable(mContext, fromBg, toBg, view);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(true);
                AnimUtils.changeTextColor(mContext, toColor, fromColor, textView);
                AnimUtils.changeBackgroundDrawable(mContext, toBg, fromBg, view);
            }
        }, 100);
    }

    @Override
    public int getItemCount() {
        return mArrContactData.size();
    }


    public class SearchAgentHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.layoutItem)
        LinearLayout mLayoutItem;
        @Bind(R.id.imgAvatar)
        CircleImageView mImgAvatar;
        @Bind(R.id.tvName)
        TextView mTvName;
        @Bind(R.id.tvCompanyName)
        TextView mTvCompanyName;
        @Bind(R.id.rlButtonSetAgent)
        RelativeLayout mRlButtonSetAgent;
        @Bind(R.id.tvSetAgent)
        TextView mTvSetAgent;

        public SearchAgentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface SetAgentView {
        void onSetAgent(ContactData data);
    }
}
