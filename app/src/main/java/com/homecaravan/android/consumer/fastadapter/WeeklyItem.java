package com.homecaravan.android.consumer.fastadapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.Weekly;
import com.homecaravan.android.consumer.utils.Utils;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WeeklyItem extends AbstractItem<WeeklyItem, WeeklyItem.WeeklyItemHolder> {
    private Weekly mWeekly;
    private WeeklyItemHolder mHolder;
    private Context mContext;
    private PickTimeAst mPickTime;
    private int mPosition;

    public void setWeekly(Weekly mWeekly) {
        this.mWeekly = mWeekly;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public Weekly getWeekly() {
        return mWeekly;
    }

    public void setPickTime(PickTimeAst mPickTime) {
        this.mPickTime = mPickTime;
    }

    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    @Override
    public WeeklyItemHolder getViewHolder(View v) {
        return new WeeklyItemHolder(v);
    }

    @Override
    public void bindView(final WeeklyItemHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        mHolder = holder;
        holder.mTvTime.setText(Utils.handlerTimeAst(mWeekly.getHour()));
        if (mWeekly.getSun().isSelected()) {
            holder.mLayoutSun.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_ast_day_select));
            holder.mIvSelectedSun.setVisibility(View.VISIBLE);
        } else {
            holder.mIvSelectedSun.setVisibility(View.GONE);
            if (mWeekly.getSun().getStatus().equalsIgnoreCase("available")) {
                holder.mLayoutSun.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_available));
            }
            if (mWeekly.getSun().getStatus().equalsIgnoreCase("unavailable")) {
                holder.mLayoutSun.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_past));
            }
            if (mWeekly.getSun().getStatus().equalsIgnoreCase("past")) {
                holder.mLayoutSun.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_past));
            }
            if (mWeekly.getSun().getStatus().equalsIgnoreCase("open")) {
                holder.mLayoutSun.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_open));
            }
            if (mWeekly.getSun().getStatus().equalsIgnoreCase("pending")) {
                holder.mLayoutSun.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_pending));
            }
        }
        if (mWeekly.getMon().isSelected()) {
            holder.mLayoutMon.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_ast_day_select));
            holder.mIvSelectedMon.setVisibility(View.VISIBLE);
        } else {
            holder.mIvSelectedMon.setVisibility(View.GONE);
            if (mWeekly.getMon().getStatus().equalsIgnoreCase("available")) {
                holder.mLayoutMon.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_available));
            }
            if (mWeekly.getMon().getStatus().equalsIgnoreCase("unavailable")) {
                holder.mLayoutMon.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_past));
            }
            if (mWeekly.getMon().getStatus().equalsIgnoreCase("past")) {
                holder.mLayoutMon.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_past));
            }
            if (mWeekly.getMon().getStatus().equalsIgnoreCase("open")) {
                holder.mLayoutMon.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_open));
            }
            if (mWeekly.getMon().getStatus().equalsIgnoreCase("pending")) {
                holder.mLayoutMon.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_pending));
            }
        }
        if (mWeekly.getTue().isSelected()) {
            holder.mLayoutTue.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_ast_day_select));
            holder.mIvSelectedTue.setVisibility(View.VISIBLE);
        } else {
            holder.mIvSelectedTue.setVisibility(View.GONE);
            if (mWeekly.getTue().getStatus().equalsIgnoreCase("available")) {
                holder.mLayoutTue.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_available));
            }
            if (mWeekly.getTue().getStatus().equalsIgnoreCase("unavailable")) {
                holder.mLayoutTue.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_past));
            }
            if (mWeekly.getTue().getStatus().equalsIgnoreCase("past")) {
                holder.mLayoutTue.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_past));
            }
            if (mWeekly.getTue().getStatus().equalsIgnoreCase("open")) {
                holder.mLayoutTue.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_open));
            }
            if (mWeekly.getTue().getStatus().equalsIgnoreCase("pending")) {
                holder.mLayoutTue.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_pending));
            }
        }
        if (mWeekly.getWed().isSelected()) {
            holder.mLayoutWed.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_ast_day_select));
            holder.mIvSelectedWed.setVisibility(View.VISIBLE);
        } else {
            holder.mIvSelectedWed.setVisibility(View.GONE);
            if (mWeekly.getWed().getStatus().equalsIgnoreCase("available")) {
                holder.mLayoutWed.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_available));
            }
            if (mWeekly.getWed().getStatus().equalsIgnoreCase("unavailable")) {
                holder.mLayoutWed.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_past));
            }
            if (mWeekly.getWed().getStatus().equalsIgnoreCase("past")) {
                holder.mLayoutWed.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_past));
            }
            if (mWeekly.getWed().getStatus().equalsIgnoreCase("open")) {
                holder.mLayoutWed.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_open));
            }
            if (mWeekly.getWed().getStatus().equalsIgnoreCase("pending")) {
                holder.mLayoutWed.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_pending));
            }
        }
        if (mWeekly.getThu().isSelected()) {
            holder.mLayoutThu.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_ast_day_select));
            holder.mIvSelectedThu.setVisibility(View.VISIBLE);
        } else {
            holder.mIvSelectedThu.setVisibility(View.GONE);
            if (mWeekly.getThu().getStatus().equalsIgnoreCase("available")) {
                holder.mLayoutThu.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_available));
            }
            if (mWeekly.getThu().getStatus().equalsIgnoreCase("unavailable")) {
                holder.mLayoutThu.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_past));
            }
            if (mWeekly.getThu().getStatus().equalsIgnoreCase("past")) {
                holder.mLayoutThu.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_past));
            }
            if (mWeekly.getThu().getStatus().equalsIgnoreCase("open")) {
                holder.mLayoutThu.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_open));
            }
            if (mWeekly.getThu().getStatus().equalsIgnoreCase("pending")) {
                holder.mLayoutThu.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_pending));
            }
        }
        if (mWeekly.getFri().isSelected()) {
            holder.mLayoutFri.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_ast_day_select));
            holder.mIvSelectedFri.setVisibility(View.VISIBLE);
        } else {
            holder.mIvSelectedFri.setVisibility(View.GONE);
            if (mWeekly.getFri().getStatus().equalsIgnoreCase("available")) {
                holder.mLayoutFri.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_available));
            }
            if (mWeekly.getFri().getStatus().equalsIgnoreCase("unavailable")) {
                holder.mLayoutFri.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_past));
            }
            if (mWeekly.getFri().getStatus().equalsIgnoreCase("past")) {
                holder.mLayoutFri.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_past));
            }
            if (mWeekly.getFri().getStatus().equalsIgnoreCase("open")) {
                holder.mLayoutFri.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_open));
            }
            if (mWeekly.getFri().getStatus().equalsIgnoreCase("pending")) {
                holder.mLayoutFri.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_pending));
            }
        }
        if (mWeekly.getSat().isSelected()) {
            holder.mLayoutSat.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_ast_day_select));
            holder.mIvSelectedSat.setVisibility(View.VISIBLE);
        } else {
            holder.mIvSelectedSat.setVisibility(View.GONE);
            if (mWeekly.getSat().getStatus().equalsIgnoreCase("available")) {
                holder.mLayoutSat.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_available));
            }
            if (mWeekly.getSat().getStatus().equalsIgnoreCase("unavailable")) {
                holder.mLayoutSat.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_past));
            }
            if (mWeekly.getSat().getStatus().equalsIgnoreCase("past")) {
                holder.mLayoutSat.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_past));
            }
            if (mWeekly.getSat().getStatus().equalsIgnoreCase("open")) {
                holder.mLayoutSat.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_open));
            }
            if (mWeekly.getSat().getStatus().equalsIgnoreCase("pending")) {
                holder.mLayoutSat.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_book_single_day_pending));
            }
        }
        holder.mLayoutSun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWeekly.getSun().isSelected()) {
                    mPickTime.pickTime(mWeekly.getSun().getTimeBookStart(), false, mPosition, mWeekly.getSun().getStatus());
                    mWeekly.getSun().setSelected(false);
                } else {
                    mPickTime.pickTime(mWeekly.getSun().getTimeBookStart(), true, mPosition, mWeekly.getSun().getStatus());
                    mWeekly.getSun().setSelected(true);

                }
            }
        });
        holder.mLayoutMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWeekly.getMon().isSelected()) {
                    mPickTime.pickTime(mWeekly.getMon().getTimeBookStart(), false, mPosition, mWeekly.getMon().getStatus());
                    mWeekly.getMon().setSelected(false);
                } else {
                    mPickTime.pickTime(mWeekly.getMon().getTimeBookStart(), true, mPosition, mWeekly.getMon().getStatus());
                    mWeekly.getMon().setSelected(true);
                }
            }
        });

        holder.mLayoutTue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWeekly.getTue().isSelected()) {
                    mPickTime.pickTime(mWeekly.getTue().getTimeBookStart(), false, mPosition, mWeekly.getTue().getStatus());
                    mWeekly.getTue().setSelected(false);
                } else {
                    mPickTime.pickTime(mWeekly.getTue().getTimeBookStart(), true, mPosition, mWeekly.getTue().getStatus());
                    mWeekly.getTue().setSelected(true);
                }
            }
        });

        holder.mLayoutWed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWeekly.getWed().isSelected()) {
                    mPickTime.pickTime(mWeekly.getWed().getTimeBookStart(), false, mPosition, mWeekly.getWed().getStatus());
                    mWeekly.getWed().setSelected(false);
                } else {
                    mPickTime.pickTime(mWeekly.getWed().getTimeBookStart(), true, mPosition, mWeekly.getWed().getStatus());
                    mWeekly.getWed().setSelected(true);
                }
            }
        });

        holder.mLayoutThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWeekly.getThu().isSelected()) {
                    mPickTime.pickTime(mWeekly.getThu().getTimeBookStart(), false, mPosition, mWeekly.getThu().getStatus());
                    mWeekly.getThu().setSelected(false);
                } else {
                    mPickTime.pickTime(mWeekly.getThu().getTimeBookStart(), true, mPosition, mWeekly.getThu().getStatus());
                    mWeekly.getThu().setSelected(true);
                }
            }
        });

        holder.mLayoutFri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWeekly.getFri().isSelected()) {
                    mPickTime.pickTime(mWeekly.getFri().getTimeBookStart(), false, mPosition, mWeekly.getFri().getStatus());
                    mWeekly.getFri().setSelected(false);
                } else {
                    mPickTime.pickTime(mWeekly.getFri().getTimeBookStart(), true, mPosition, mWeekly.getFri().getStatus());
                    mWeekly.getFri().setSelected(true);
                }
            }
        });

        holder.mLayoutSat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWeekly.getSat().isSelected()) {
                    mPickTime.pickTime(mWeekly.getSat().getTimeBookStart(), false, mPosition, mWeekly.getSat().getStatus());
                    mWeekly.getSat().setSelected(false);
                } else {
                    mPickTime.pickTime(mWeekly.getSat().getTimeBookStart(), true, mPosition,mWeekly.getSat().getStatus());
                    mWeekly.getSat().setSelected(true);

                }
            }
        });

    }

    @Override
    public int getType() {
        return R.id.weekly_ast_item;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.weekly_item;
    }

    public void updateList() {

    }

    public class WeeklyItemHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvTime)
        TextView mTvTime;
        @Bind(R.id.layoutSun)
        RelativeLayout mLayoutSun;
        @Bind(R.id.ivSelectedSun)
        ImageView mIvSelectedSun;
        @Bind(R.id.layoutMon)
        RelativeLayout mLayoutMon;
        @Bind(R.id.ivSelectedMon)
        ImageView mIvSelectedMon;
        @Bind(R.id.layoutTue)
        RelativeLayout mLayoutTue;
        @Bind(R.id.ivSelectedTue)
        ImageView mIvSelectedTue;
        @Bind(R.id.layoutWed)
        RelativeLayout mLayoutWed;
        @Bind(R.id.ivSelectedWed)
        ImageView mIvSelectedWed;
        @Bind(R.id.layoutThu)
        RelativeLayout mLayoutThu;
        @Bind(R.id.ivSelectedThu)
        ImageView mIvSelectedThu;
        @Bind(R.id.layoutFri)
        RelativeLayout mLayoutFri;
        @Bind(R.id.ivSelectedFri)
        ImageView mIvSelectedFri;
        @Bind(R.id.layoutSat)
        RelativeLayout mLayoutSat;
        @Bind(R.id.ivSelectedSat)
        ImageView mIvSelectedSat;

        public WeeklyItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface PickTimeAst {
        void pickTime(String time, boolean b, int position, String status);
    }
}
