package com.homecaravan.android.consumer.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.AstDay;
import com.homecaravan.android.consumer.model.AstTime;
import com.homecaravan.android.consumer.utils.Utils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AstDaySelectAdapter extends RecyclerView.Adapter<AstDaySelectAdapter.AstDaySelectHolder> {
    private ArrayList<AstDay> mArrDay;
    private Context mContext;
    private boolean mBind;

    public AstDaySelectAdapter(ArrayList<AstDay> mArrDay, Context mContext) {
        this.mArrDay = mArrDay;
        this.mContext = mContext;
    }

    @Override
    public AstDaySelectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ast_day_select_item, null, false);
        return new AstDaySelectHolder(view);
    }

    @Override
    public void onBindViewHolder(AstDaySelectHolder holder, int position) {
        holder.mTvDay.setText(Utils.getDayAst(mArrDay.get(position).getDay()));
        holder.mRvTime.setLayoutManager(new GridLayoutManager(mContext, 3));
        AstTimeSelectAdapter adapter = new AstTimeSelectAdapter(getTimeAst(mArrDay.get(position).getTime()), mContext);
        holder.mRvTime.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return mArrDay.size();
    }

    public class AstDaySelectHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvDay)
        TextView mTvDay;
        @Bind(R.id.rvTime)
        RecyclerView mRvTime;

        public AstDaySelectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public ArrayList<AstTime> getTimeAst(ArrayList<String> rawTime) {
        ArrayList<AstTime> arrTime = new ArrayList<>();
        for (int i = 0; i < rawTime.size(); i++) {
            arrTime.add(new AstTime(Utils.getTimeAst(rawTime.get(i)), false));
        }
        return arrTime;
    }
}
