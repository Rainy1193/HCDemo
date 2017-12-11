package com.homecaravan.android.consumer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.listener.ISearchListener;
import com.homecaravan.android.consumer.model.Predictions;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultHolder> {
    private ArrayList<Predictions.Place> mPlaces;
    private Context mContext;
    private ISearchListener mListener;

    public SearchResultAdapter(ArrayList<Predictions.Place> mPlaces, Context mContext, ISearchListener mListener) {
        this.mPlaces = mPlaces;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @Override
    public SearchResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.search_result_item, null, false);
        return new SearchResultHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchResultHolder holder, final int position) {
        holder.mLayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.showSearchResult(mPlaces.get(position).getStructuredFormatting().getMainText());
            }
        });
        holder.mMainText.setText(mPlaces.get(position).getStructuredFormatting().getMainText());
        holder.mSecondaryText.setText(mPlaces.get(position).getStructuredFormatting().getSecondaryText());
    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }

    public class SearchResultHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvMainText)
        TextView mMainText;
        @Bind(R.id.layoutMain)
        LinearLayout mLayoutMain;
        @Bind(R.id.tvSecondaryText)
        TextView mSecondaryText;

        public SearchResultHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
