package com.homecaravan.android.consumer.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.listener.IDashboardListener;
import com.homecaravan.android.consumer.model.CurrentSavedSearch;
import com.homecaravan.android.consumer.model.SavedSearchDashboard;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedSearchDashboardAdapter extends RecyclerView.Adapter<SavedSearchDashboardAdapter.SaveSearchHolder> {

    private Context mContext;
    private ArrayList<SavedSearchDashboard> mSavedSearches;
    private IDashboardListener mListener;

    public SavedSearchDashboardAdapter(Context mContext, ArrayList<SavedSearchDashboard> mSavedSearches, IDashboardListener mListener) {
        this.mContext = mContext;
        this.mSavedSearches = mSavedSearches;
        this.mListener = mListener;
    }


    @Override
    public SavedSearchDashboardAdapter.SaveSearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.save_search_item, parent, false);
        return new SaveSearchHolder(v);
    }

    @Override
    public void onBindViewHolder(SavedSearchDashboardAdapter.SaveSearchHolder searchHolder, final int position) {

        searchHolder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentSavedSearch.getInstance().setSavedSearch(mSavedSearches.get(position).getSavedSearch());
               // mListener.openSavedSearchItem(position);
            }
        });
        searchHolder.mSize.setText(mSavedSearches.get(position).getSavedSearch().getLiving());
        searchHolder.mNameSearch.setText(mSavedSearches.get(position).getSavedSearch().getNameSearch());
        searchHolder.mBath.setText(mSavedSearches.get(position).getSavedSearch().getBath());
        searchHolder.mBed.setText(mSavedSearches.get(position).getSavedSearch().getBed());
        searchHolder.mFoundListing.setText("Found: " + mSavedSearches.get(position).getSavedSearch().getFoundListing());
        searchHolder.mNewListing.setText("New: " + mSavedSearches.get(position).getSavedSearch().getNewListing());
    }


    @Override
    public int getItemCount() {
        return mSavedSearches.size();
    }

    public class SaveSearchHolder extends RecyclerView.ViewHolder {

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

        public SaveSearchHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
