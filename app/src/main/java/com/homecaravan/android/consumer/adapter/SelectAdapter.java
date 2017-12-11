package com.homecaravan.android.consumer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.listener.ISelectListener;
import com.homecaravan.android.models.ItemSelect;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.SelectHolder> {
    private ArrayList<ItemSelect> mArrItem;
    private Context mContext;
    private ISelectListener mListener;

    public SelectAdapter(ArrayList<ItemSelect> mArrItem, Context mContext, ISelectListener mListener) {
        this.mArrItem = mArrItem;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @Override
    public SelectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.select_item, parent, false);
        return new SelectHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectHolder holder, int position) {
        holder.mValue.setText(mArrItem.get(position).getValue());
        if (mArrItem.get(position).isShowViewTop()) {
            holder.mView1.setVisibility(View.VISIBLE);
        } else {
            //holder.mView1.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mArrItem.size();
    }

    public class SelectHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvValue)
        TextView mValue;
        @Bind(R.id.view)
        View mView;
        @Bind(R.id.view1)
        View mView1;

        public SelectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
