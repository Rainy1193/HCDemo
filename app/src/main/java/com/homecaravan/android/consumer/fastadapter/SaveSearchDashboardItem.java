package com.homecaravan.android.consumer.fastadapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.listener.IDashboardListener;
import com.homecaravan.android.consumer.model.responseapi.Search;
import com.homecaravan.android.consumer.utils.Utils;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SaveSearchDashboardItem extends AbstractItem<SaveSearchDashboardItem, SaveSearchDashboardItem.SaveSearchItemHolder> {
    private Search mSearch;
    private Context mContext;
    private IDashboardListener mListener;

    public void setSearch(Search mSearch) {
        this.mSearch = mSearch;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public Search getSearch() {
        return mSearch;
    }

    @Override
    public void bindView(SaveSearchItemHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);

//        holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //CurrentSavedSearch.getInstance().setSavedSearch(mSavedSearches.get(position).getSavedSearch());
//                mListener.openSavedSearchItem(mSearch.getId());
//            }
//        });
        if (mSearch.getConditions().get(0).getLsf() != null) {
            holder.mSize.setText(mSearch.getConditions().get(0).getLsf());
        } else {
            holder.mSize.setText("0");
        }
        holder.mNameSearch.setText(Utils.handlerNameSearch(mSearch.getName()));
        holder.mBath.setText(mSearch.getConditions().get(0).getAr());
        holder.mBed.setText(mSearch.getConditions().get(0).getBr());
        holder.mFoundListing.setText("Found: " + mSearch.getListings().getTotal());
        holder.mNewListing.setText("New: " + mSearch.getListings().getTotal());
    }

    @Override
    public SaveSearchItemHolder getViewHolder(View v) {
        return new SaveSearchItemHolder(v);
    }

    @Override
    public int getType() {
        return R.id.save_search_item;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.save_search_item;
    }


    public static class SaveSearchItemHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.layoutItem)
        RelativeLayout mLayoutItem;
        @Bind(R.id.tvSize)
        TextView mSize;
        @Bind(R.id.tvNameSearch)
        TextView mNameSearch;
        @Bind(R.id.tvBed)
        TextView mBed;
        @Bind(R.id.tvBath)
        TextView mBath;
        @Bind(R.id.tvFound)
        TextView mFoundListing;
        @Bind(R.id.tvNew)
        TextView mNewListing;

        public SaveSearchItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
