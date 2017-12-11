package com.homecaravan.android.consumer.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.responseapi.ContactData;
import com.homecaravan.android.ui.CircleImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private ArrayList<ContactData> mArrTeam = new ArrayList<>();
    private ArrayList<ContactData> mFilteredList = new ArrayList<>();
    private Context mContext;
    private SelectAgent mListener;

    public ContactAdapter(Context mContext, ArrayList<ContactData> mArrTeam, SelectAgent mListener) {
        this.mContext = mContext;
        this.mArrTeam = mArrTeam;
        this.mFilteredList = mArrTeam;
        this.mListener = mListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_item, parent, false);
        vh = new ContactHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ContactData agent = mFilteredList.get(position);
        ContactHolder contactHolder = (ContactHolder) holder;

        Glide.with(mContext.getApplicationContext()).load(agent.getAvatar()).asBitmap().fitCenter()
                .placeholder(R.drawable.avatar_default)
                .dontAnimate()
                .into(contactHolder.mImgAvatar);

        contactHolder.mTvName.setText(agent.getName());
        contactHolder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (agent.isSelect()) {
                    mListener.selectAgent(agent, false, position);
                } else {
                    agent.setSelect(true);
                    mListener.selectAgent(agent, true, position);
                }
            }
        });
        if (agent.isSelect()) {
            ((ContactHolder) holder).mTvSetAgent.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
            ((ContactHolder) holder).mRlButtonSetAgent.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_button_set_agent_team_search_click));
        } else {
            ((ContactHolder) holder).mRlButtonSetAgent.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_button_set_agent_team_search));
            ((ContactHolder) holder).mTvSetAgent.setTextColor(ContextCompat.getColor(mContext, R.color.teamtab_search_featured_text_button));
        }
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String query = charSequence.toString();

                if (query.isEmpty()) {
                    mFilteredList = mArrTeam;
                } else {

                    ArrayList<ContactData> filteredList = new ArrayList<>();
                    for (ContactData consumerTeam : mArrTeam) {
                        String fullName = consumerTeam.getName();
                        if (fullName.contains(query)) {
                            filteredList.add(consumerTeam);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<ContactData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ContactHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.layoutItem)
        LinearLayout mLayoutItem;
        @Bind(R.id.imgAvatar)
        CircleImageView mImgAvatar;
        @Bind(R.id.tvName)
        TextView mTvName;
        @Bind(R.id.tvCompanyName)
        TextView mTvCompanyName;
        @Bind(R.id.imgLogo)
        ImageView mImgLogo;
        @Bind(R.id.rlButtonSetAgent)
        RelativeLayout mRlButtonSetAgent;
        @Bind(R.id.tvSetAgent)
        TextView mTvSetAgent;

        public ContactHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface SelectAgent {
        void selectAgent(ContactData contact, boolean b, int position);
    }
}
