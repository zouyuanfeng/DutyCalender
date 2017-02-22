package com.itzyf.dutycalendarlib.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itzyf.dutycalendarlib.R;
import com.itzyf.dutycalendarlib.utils.DateUtil;

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

    public MonthAdapter(Context context, int position) {
        this.context = context;
        this.position = position;
        preSumDays = DateUtil.getPreMonthDays();
        currentSumDays = DateUtil.getCurrentMonthDays();
        nextSumDays = DateUtil.getNextMonthDays();
    }

    @Override
    public DayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DayViewHolder(LayoutInflater.from(context).inflate(R.layout.item_day, null));
    }

    @Override
    public void onBindViewHolder(DayViewHolder holder, int position) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DAY_OF_MONTH, 1);
//        int week = calendar.get(Calendar.DAY_OF_WEEK);
//        switch (this.position) {
//            case 0:
//                if (week >= position && position - week <= preSumDays)
//                    holder.mView.setText(position - week + 1 + "\n班");
//                else holder.mView.setText("");
//                break;
//            case 1:
//                if (week >= position && position - week <= currentSumDays)
//                    holder.mView.setText(position - week + 1 + "\n班");
//                break;
//            case 2:
//                if (week >= position && position - week <= nextSumDays)
//                    holder.mView.setText(position - week + 1 + "\n班");
//                break;
//        }

    }


    @Override
    public int getItemCount() {
        return 6 * 7;
    }

    class DayViewHolder extends RecyclerView.ViewHolder {
        TextView mView;

        public DayViewHolder(View itemView) {
            super(itemView);
            mView = (TextView) itemView.findViewById(R.id.item_day);
        }
    }
}
