package com.homecaravan.android.consumer.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.ConsumerTeam;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.homecaravan.android.ui.CircleImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyResourceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable{
    private ArrayList<ConsumerTeam> mArrTeam = new ArrayList<>();
    private ArrayList<ConsumerTeam> mFilteredList = new ArrayList<>();
    private Context mContext;

    public MyResourceAdapter(Context mContext, ArrayList<ConsumerTeam> mArrTeam) {
        this.mContext = mContext;
        this.mArrTeam = mArrTeam;
        this.mFilteredList = mArrTeam;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_item, parent, false);
        vh = new MyResourceHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ConsumerTeam agent = mFilteredList.get(position);
        MyResourceHolder myResourceHolder = (MyResourceHolder) holder;

        Glide.with(mContext.getApplicationContext()).load(agent.getPhoto()).asBitmap().fitCenter()
                .placeholder(R.drawable.avatar_default)
                .dontAnimate()
                .into(myResourceHolder.mImgAvatar);
        Glide.with(mContext.getApplicationContext()).load(agent.getLogo()).asBitmap().fitCenter()
                .placeholder(R.drawable.no_image)
                .dontAnimate()
                .into(myResourceHolder.mImgLogo);

        myResourceHolder.mTvName.setText(agent.getFirstName() + " " + agent.getLastName());
        myResourceHolder.mTvCompanyName.setText(agent.getCompany());

        myResourceHolder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        myResourceHolder.mRlButtonSetAgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView tvSetAgent = (TextView) view.findViewById(R.id.tvSetAgent);
                changeButton(view, tvSetAgent);

                LayoutInflater layoutInflater1 = LayoutInflater.from(mContext);
                View view1 = layoutInflater1.inflate(R.layout.dialog_item_consumer_tab_search, null);
                TextView tvViewInfomation = (TextView) view1.findViewById(R.id.tvViewInfomation);
                TextView tvContact = (TextView) view1.findViewById(R.id.tvContact);
                TextView tvCancel = (TextView) view1.findViewById(R.id.tvCancel);

                final AlertDialog alertDialog = new AlertDialog.Builder(mContext).setView(view1).create();
                alertDialog.getWindow().getAttributes().windowAnimations = R.style.TeamTabSearchDialog;
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
                wmlp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                wmlp.y = 200;   //y position

                tvViewInfomation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                tvContact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
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

                    ArrayList<ConsumerTeam> filteredList = new ArrayList<>();
                    for(ConsumerTeam consumerTeam : mArrTeam){
                        String fullName = consumerTeam.getFirstName().toLowerCase() + " " + consumerTeam.getLastName().toLowerCase();
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
                mFilteredList = (ArrayList<ConsumerTeam>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyResourceHolder extends RecyclerView.ViewHolder {

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

        public MyResourceHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
