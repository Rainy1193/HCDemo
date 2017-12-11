package com.homecaravan.android.consumer.fastadapter;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.adapter.SavedSearchAgentAdapter;
import com.homecaravan.android.consumer.listener.IAddAgentListener;
import com.homecaravan.android.consumer.listener.IOpenSavedSearchDetail;
import com.homecaravan.android.consumer.model.responseapi.Search;
import com.homecaravan.android.consumer.utils.Utils;
import com.homecaravan.android.models.CurrentSaveSearch;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SaveSearchItem extends AbstractItem<SaveSearchItem, SaveSearchItem.SaveSearchItemHolder> {

    private Search mSearch;
    private Context mContext;
    private SavedSearchAgentAdapter mAgentAdapter;
    private IAddAgentListener mListener;
    private IOpenSavedSearchDetail mOpenListener;
    private SaveSearchItemHolder mHolder;
    private int mPosition;
    private boolean mBindView = false;

    public Search getSearch() {
        return mSearch;
    }

    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public void setSearch(Search mSearch) {
        this.mSearch = mSearch;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public void setListener(IAddAgentListener mListener) {
        this.mListener = mListener;
    }

    public void setOpenListener(IOpenSavedSearchDetail mOpenListener) {
        this.mOpenListener = mOpenListener;
    }

    @Override
    public void bindView(SaveSearchItemHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        mHolder = holder;
        if (!mBindView) {
            holder.mRvAgent.setLayoutManager(getLayoutManager());
            holder.mRvListing.setLayoutManager(getLayoutManager());
            mAgentAdapter = new SavedSearchAgentAdapter(mContext, mSearch.getParticipants().getData());
            holder.mRvAgent.setAdapter(mAgentAdapter);
            mBindView = true;
        }
        holder.mTvTotalListing.setText("Listing: " + mSearch.getListings().getTotal());
        holder.mCollaborator.setText("Collaborator: " + String.valueOf(mSearch.getParticipants().getTotal()));
        if (mSearch.getConditions() != null) {
            if (mSearch.getConditions().size() != 0) {
                holder.mBadRoom.setText(mSearch.getConditions().get(0).getAr() == null ? "0" : mSearch.getConditions().get(0).getAr());
                holder.mBedRoom.setText(mSearch.getConditions().get(0).getBr() == null ? "0" : mSearch.getConditions().get(0).getBr());
                holder.mPrice.setText(mSearch.getConditions().get(0).getMinPrice() == null ? "$0" : Utils.getPrice(mSearch.getConditions().get(0).getMinPrice()));
            } else {
                holder.mBadRoom.setText("0");
                holder.mBedRoom.setText("0");
                holder.mPrice.setText("$0");
            }
        }
//        if (!mBindView) {
//            mPresenter = new GetListingPrePagePresenter(this);
//            mPresenter.getListingPrePage(mSearch.getId(), "1");
//            holder.mTabLayout.addTab(holder.mTabLayout.newTab().setText(R.string.discover_top_rated));
//            holder.mTabLayout.addTab(holder.mTabLayout.newTab().setText(R.string.discover_just_listed));
//            holder.mTabLayout.addTab(holder.mTabLayout.newTab().setText(R.string.discover_to_review));
//            holder.mTabLayout.addTab(holder.mTabLayout.newTab().setText(R.string.discover_reviewed));
//            holder.mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//                @Override
//                public void onTabSelected(TabLayout.Tab tab) {
//
//                }
//
//                @Override
//                public void onTabUnselected(TabLayout.Tab tab) {
//
//                }
//
//                @Override
//                public void onTabReselected(TabLayout.Tab tab) {
//
//                }
//            });
//            mBindView = true;
//        }

        holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentSaveSearch.getInstance().setArrParticipant(mSearch.getParticipants().getData());
                CurrentSaveSearch.getInstance().setId(mSearch.getId());
                CurrentSaveSearch.getInstance().setName(Utils.handlerNameSearch(mSearch.getName()));
                mOpenListener.openSavedSearchDetail(mSearch.getId());
            }
        });
        holder.mAddCollaborator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.mLayoutViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.mIvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentSaveSearch.getInstance().setArrParticipant(mSearch.getParticipants().getData());
                CurrentSaveSearch.getInstance().setId(mSearch.getId());
                CurrentSaveSearch.getInstance().setName(Utils.handlerNameSearch(mSearch.getName()));
                mOpenListener.openSavedSearchDetail(mSearch.getId());
            }
        });
        holder.mSearchName.setText(mSearch.getName());
//        holder.mLayoutAddAgent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CurrentSaveSearch.getInstance().setArrParticipant(mSearch.getParticipants().getData());
//                CurrentSaveSearch.getInstance().setId(mSearch.getId());
//                CurrentSaveSearch.getInstance().setName(Utils.handlerNameSearch(mSearch.getName()));
//                mListener.addAgent(mPosition);
//            }
//        });
    }

    public void updateAgent() {

        mAgentAdapter = new SavedSearchAgentAdapter(mContext, mSearch.getParticipants().getData());
        mHolder.mRvAgent.setAdapter(mAgentAdapter);
    }

    public void updateNameSearch() {
        mHolder.mSearchName.setText(mSearch.getName());
    }

    @Override
    public SaveSearchItemHolder getViewHolder(View v) {
        return new SaveSearchItemHolder(v);
    }


    public LinearLayoutManager getLayoutManager() {
        return new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
    }


    @Override
    public int getType() {
        return R.id.save_search_item1;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.saved_search_item;
    }


    public static class SaveSearchItemHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvTotalListing)
        TextView mTvTotalListing;
        @Bind(R.id.tvBed)
        TextView mBedRoom;
        @Bind(R.id.tvBad)
        TextView mBadRoom;
        @Bind(R.id.tvPrice)
        TextView mPrice;
        @Bind(R.id.tvCollaborator)
        TextView mCollaborator;
        @Bind(R.id.ivAddCollaborator)
        ImageView mAddCollaborator;

        @Bind(R.id.layoutItem)
        LinearLayout mLayoutItem;
        @Bind(R.id.ivSettingSavedSearch)
        ImageView mIvSetting;
        @Bind(R.id.tabLayout)
        TabLayout mTabLayout;
        @Bind(R.id.rvListingSavedSearch)
        RecyclerView mRvListing;
        @Bind(R.id.layoutAddAgent)
        RelativeLayout mLayoutAddAgent;
        @Bind(R.id.rvAgentSavedSearch)
        RecyclerView mRvAgent;
        @Bind(R.id.tvSearchName)
        TextView mSearchName;
        @Bind(R.id.layoutViewAll)
        RelativeLayout mLayoutViewAll;
        @Bind(R.id.tvNumViewAll)
        TextView mTotalListing;

        public SaveSearchItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
