package com.itzyf.dutycalendarlib.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itzyf.dutycalendarlib.R;
import com.itzyf.dutycalendarlib.adapter.CalendarAdapter;
import com.itzyf.dutycalendarlib.adapter.MonthAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * @author 依风听雨
 * @version 2017/2/22
 */

public class DutyCalendar extends LinearLayout implements View.OnClickListener {
    private Context context;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月", Locale.CHINESE);
    private ViewPager mViewPager;
    private CalendarAdapter mAdapter;
    private TextView mTvTitle;
    private List<View> viewList;
    private ImageButton mBtnPre, mBtnNext;


    public DutyCalendar(Context context) {
        this(context, null);
    }

    public DutyCalendar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DutyCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.duty_calendar, this);
        initView();
        addViewList();
        mAdapter = new CalendarAdapter(viewList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(1);
        mViewPager.addOnPageChangeListener(listener);
        setTitle(1);
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mBtnPre = (ImageButton) findViewById(R.id.ibtn_pre);
        mBtnPre.setOnClickListener(this);
        mBtnNext = (ImageButton) findViewById(R.id.ibtn_next);
        mBtnNext.setOnClickListener(this);
    }

    private void addViewList() {

        viewList = new ArrayList<>();

        viewList.add(createMonthView(0));
        viewList.add(createMonthView(1));
        viewList.add(createMonthView(2));
    }

    private View createMonthView(int position) {
        RecyclerView rv = new RecyclerView(context);
        rv.setLayoutManager(new GridLayoutManager(context, 7) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rv.setAdapter(new MonthAdapter(context, position));
        return rv;
    }

    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setTitle(position);
            switch (position) {
                case 0:
                    mBtnPre.setVisibility(INVISIBLE);
                    mBtnNext.setVisibility(VISIBLE);
                    break;
                case 2:
                    mBtnPre.setVisibility(VISIBLE);
                    mBtnNext.setVisibility(INVISIBLE);
                    break;
                default:
                    mBtnPre.setVisibility(VISIBLE);
                    mBtnNext.setVisibility(VISIBLE);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onClick(View v) {
        int i = v.getId();
        int position = mViewPager.getCurrentItem();
        if (i == R.id.ibtn_pre) {
            position -= 1;
            if (position < 0) position = 0;
        } else if (i == R.id.ibtn_next) {
            position += 1;
            if (position >= 3) position = 2;
        }
        mViewPager.setCurrentItem(position);
    }

    public void setTitle(int position) {
        Calendar calendar = Calendar.getInstance();
        switch (position) {
            case 0:
                calendar.add(Calendar.MONTH, -1);
                break;
            case 2:
                calendar.add(Calendar.MONTH, 1);
                break;
            default:
                break;
        }
        mTvTitle.setText(sdf.format(calendar.getTime()));
    }
}
