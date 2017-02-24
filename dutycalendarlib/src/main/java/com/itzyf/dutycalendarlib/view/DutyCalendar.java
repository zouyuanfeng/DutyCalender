package com.itzyf.dutycalendarlib.view;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itzyf.dutycalendarlib.R;
import com.itzyf.dutycalendarlib.adapter.CalendarAdapter;
import com.itzyf.dutycalendarlib.adapter.MonthAdapter;
import com.itzyf.dutycalendarlib.utils.AnimatorListener;
import com.itzyf.dutycalendarlib.utils.DensityUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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

    private LinearLayout mLlWeek;

    private int weeksColor = 0xDD000000;

    private boolean isTitleAnim = true;
    private int translate;
    public static final int DEFAULT_Y_TRANSLATION_DP = 20;
    /**
     * 动画时间
     */
    private int titleAnimTime = 150;

    private float weeksTextSize;

    private String[] weeks = getResources().getStringArray(R.array.weeks);

    public DutyCalendar(Context context) {
        this(context, null);
    }

    public DutyCalendar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DutyCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        //初始化view
        LayoutInflater.from(context).inflate(R.layout.duty_calendar, this);
        initView();
        addViewList();
        mAdapter = new CalendarAdapter(viewList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(1);
        mViewPager.addOnPageChangeListener(listener);
        mTvTitle.setText(sdf.format(Calendar.getInstance().getTime()));

        translate = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, DEFAULT_Y_TRANSLATION_DP, getResources().getDisplayMetrics());

        //初始化属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DutyCalendar);
        int color = a.getColor(R.styleable.DutyCalendar_arrowColor, 0xDD000000);
        setArrow(color);
        weeksColor = a.getColor(R.styleable.DutyCalendar_weeksColor, 0xDD000000);
        isTitleAnim = a.getBoolean(R.styleable.DutyCalendar_titleAnim, true);
        titleAnimTime = a.getInteger(R.styleable.DutyCalendar_titleAnimDuration, 150);
        weeksTextSize = a.getDimension(R.styleable.DutyCalendar_weeksTextSize, DensityUtils.sp2px(context, 16));
        a.recycle();

        createWeekView();
    }

    public void setWeeksColor(@ColorInt int weeksColor) {
        for (int i = 0; i < mLlWeek.getChildCount(); i++) {
            if (mLlWeek.getChildAt(i) instanceof TextView) {
                ((TextView) mLlWeek.getChildAt(i)).setTextColor(weeksColor);
            }
        }
    }

    /**
     * 单位为sp或dp
     *
     * @param textSize
     */
    public void setWeeksTextSize(float textSize) {
        for (int i = 0; i < mLlWeek.getChildCount(); i++) {
            if (mLlWeek.getChildAt(i) instanceof TextView) {
                ((TextView) mLlWeek.getChildAt(i)).setTextSize(textSize);
            }
        }
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mBtnPre = (ImageButton) findViewById(R.id.ibtn_pre);
        mBtnPre.setOnClickListener(this);
        mBtnNext = (ImageButton) findViewById(R.id.ibtn_next);
        mBtnNext.setOnClickListener(this);
        mLlWeek = (LinearLayout) findViewById(R.id.ll_week);

    }

    private void createWeekView() {
        for (String week : weeks) {
            TextView tv = new TextView(context);
            LayoutParams lp = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.weight = 1;
            tv.setLayoutParams(lp);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(weeksColor);
            tv.setTextSize(getWeeksTextSize());
            tv.setText(week);
            mLlWeek.addView(tv);
        }
    }

    private float getWeeksTextSize() {
        return DensityUtils.px2sp(context, weeksTextSize);
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

    private void setTitle(int position) {
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
        doChange(calendar.getTime());
//        mTvTitle.setText();
    }

    /**
     * 设置箭头的颜色
     *
     * @param color
     */
    public void setArrow(@ColorInt int color) {
        mBtnNext.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        mBtnPre.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    public void setArrowRes(@ColorRes int color) {
        setArrow(getResources().getColor(color));
    }

    /**
     * 上个月有班的数组
     *
     * @param duty
     */
    public void setPreListDuty(List<Integer> duty) {
        setDutts(duty, 0);
    }

    public void setPreListDuty(Integer... duty) {
        setDutts(Arrays.asList(duty), 0);
    }

    /**
     * 当月有班的数组
     *
     * @param duty
     */
    public void setCurrentListDuty(List<Integer> duty) {
        setDutts(duty, 1);
    }

    public void setCurrentListDuty(Integer... duty) {
        setDutts(Arrays.asList(duty), 1);
    }

    /**
     * 下个月有班的数组
     *
     * @param duty
     */
    public void setNextListDuty(List<Integer> duty) {
        setDutts(duty, 2);
    }

    public void setNextListDuty(Integer... duty) {
        setDutts(Arrays.asList(duty), 2);
    }

    private void setDutts(List<Integer> duty, int position) {
        ((MonthAdapter) ((RecyclerView) viewList.get(position)).getAdapter()).setDutys(duty);
    }

    private final Interpolator interpolator = new DecelerateInterpolator(2f);

    private void doChange(Date currentDate) {
        mTvTitle.animate().cancel();
        mTvTitle.setTranslationY(0);

        mTvTitle.setAlpha(1);

        final CharSequence newTitle = sdf.format(currentDate);

        if (!isTitleAnim) {
            mTvTitle.setText(newTitle);
            return;
        }
        int translation1 = translate;
        try {
            Date previousDate = sdf.parse(mTvTitle.getText().toString());
            translation1 = translate * (previousDate.before(currentDate) ? 1 : -1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final int translation = translation1;

        final ViewPropertyAnimator viewPropertyAnimator = mTvTitle.animate();
        viewPropertyAnimator.translationY(translation * -1);
        viewPropertyAnimator
                .alpha(0)
                .setDuration(titleAnimTime)
                .setInterpolator(interpolator)
                .setListener(new AnimatorListener() {

                    @Override
                    public void onAnimationCancel(Animator animator) {
                        mTvTitle.setTranslationY(0);
                        mTvTitle.setAlpha(1);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        mTvTitle.setText(newTitle);
                        mTvTitle.setTranslationY(translation);

                        final ViewPropertyAnimator viewPropertyAnimator = mTvTitle.animate();
                        viewPropertyAnimator.translationY(0);
                        viewPropertyAnimator
                                .alpha(1)
                                .setDuration(titleAnimTime)
                                .setInterpolator(interpolator)
                                .setListener(new AnimatorListener())
                                .start();
                    }
                }).start();
    }

}
