package com.itzyf.dutycalendarlib.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.itzyf.dutycalendarlib.utils.DateUtil;
import com.itzyf.dutycalendarlib.utils.DensityUtils;
import com.itzyf.dutycalendarlib.view.DayView;

import java.util.List;

/**
 * @author 依风听雨
 * @version 2017/2/22.
 */

public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.DayViewHolder> {
    private Context context;
    private int position;

    private int preSumDays; //上一个月的总天数
    private int currentSumDays; //当前月的总天数
    private int nextSumDays; //下一个月的总天数
    private int preFirstDayOfWeek;
    private int currentFirstDayOfWeek;
    private int nextFirstDayOfWeek;
    private List<Integer> mDutys;
    private float dayWidth;

    public MonthAdapter(Context context, int position) {
        this.context = context;
        this.position = position;
        dayWidth = DensityUtils.dp2px(context, 48);
        preSumDays = DateUtil.getPreMonthDays();
        currentSumDays = DateUtil.getCurrentMonthDays();
        nextSumDays = DateUtil.getNextMonthDays();
        preFirstDayOfWeek = DateUtil.getPreFirstDayOfWeek();
        currentFirstDayOfWeek = DateUtil.getFirstDayOfWeek();
        nextFirstDayOfWeek = DateUtil.getNextFirstDayOfWeek();
    }

    public MonthAdapter(Context context, int position, float dayWidth) {
        this(context, position);
        this.dayWidth = dayWidth;
    }

    public void setDutys(List<Integer> mDutys) {
        this.mDutys = mDutys;
        notifyDataSetChanged();
    }

    @Override
    public DayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DayView view = new DayView(context);
        view.setLayoutParams(new GridLayoutManager.LayoutParams((int) dayWidth, (int) dayWidth));
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DayViewHolder holder, int position) {
        switch (this.position) {
            case 0:
                setText(holder, position, preFirstDayOfWeek, preSumDays);
                break;
            case 1:
                setText(holder, position, currentFirstDayOfWeek, currentSumDays);
                if (DateUtil.getCurrentMonthDay() == position - currentFirstDayOfWeek + 1) {
                    holder.dayView.setCurrent(true);
                }
                break;
            case 2:
                setText(holder, position, nextFirstDayOfWeek, nextSumDays);
                break;
        }

    }

    private void setText(DayViewHolder holder, int position, int firstDayOfWeek, int sumDays) {
        if (firstDayOfWeek <= position && position - firstDayOfWeek < sumDays) {
            int day = position - firstDayOfWeek + 1;
            holder.dayView.setDay(day);
            if (mDutys != null && mDutys.contains(day))
                holder.dayView.setDuty(true);
        }
    }


    @Override
    public int getItemCount() {
        return 6 * 7;
    }

    class DayViewHolder extends RecyclerView.ViewHolder {
        DayView dayView;

        public DayViewHolder(View itemView) {
            super(itemView);
            dayView = (DayView) itemView;
        }


    }
}
