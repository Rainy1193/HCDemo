package com.homecaravan.android.caravan;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ProgressBar;

import com.homecaravan.android.R;
import com.mikepenz.fastadapter.commons.utils.FastAdapterUIUtils;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;


public class ProgressItem extends AbstractItem<ProgressItem, ProgressItem.ViewHolder> {

    @Override
    public int getType() {
        return R.id.progress_item_id;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.progress_item;
    }

    @Override
    public void bindView(ViewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        if (isEnabled()) {
            holder.itemView.setBackgroundResource(FastAdapterUIUtils.getSelectableBackground(holder.itemView.getContext()));
        }
        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
        layoutParams.setFullSpan(true);
        holder.itemView.setLayoutParams(layoutParams);
    }

    @Override
    public void unbindView(ViewHolder holder) {

    }

    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        protected ProgressBar progressBar;

        public ViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        }
    }
}

