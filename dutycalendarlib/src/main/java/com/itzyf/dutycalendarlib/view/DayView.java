package com.itzyf.dutycalendarlib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;

import com.itzyf.dutycalendarlib.R;
import com.itzyf.dutycalendarlib.utils.DensityUtils;

import static android.graphics.Color.WHITE;

/**
 * @author 依风听雨
 * @version 创建时间：2017/2/23 10:26
 */

public class DayView extends View {
    private Context context;
    private Paint mPaint;
    /**
     * 初始宽高
     */
    private static final int dpWidthOrHeight = 36;
    private int width;
    private int height;

    private int centerPoint;

    /**
     * 是否有班
     */
    private boolean isDuty = false;
    /**
     * 是否是当前日期
     */
    private boolean isCurrent = false;


    /**
     * 日期
     */
    private String day = "";
    private static final String duty = "班";

    public DayView(Context context) {
        this(context, null);
    }

    public DayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        mPaint = new Paint();

        mPaint.setStyle(Paint.Style.FILL);


        mPaint.setAntiAlias(true);
//        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
//        mPaint.setTypeface(font);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isCurrent) {
            mPaint.setColor(getDutyColor());
            canvas.drawCircle(centerPoint, centerPoint, centerPoint, mPaint);
        }
        mPaint.setTextSize(DensityUtils.sp2px(context, 21));
        mPaint.setColor(Color.BLACK);
        if (isDuty) {  //有班
            if (isCurrent) {
                drawDay(canvas, WHITE);
                drawDuty(canvas, WHITE);
            } else {
                drawDay(canvas, getDutyColor());
                drawDuty(canvas, Color.BLACK);
            }
        } else {
            if (isCurrent) {
                mPaint.setColor(Color.WHITE);
            } else {
                mPaint.setColor(Color.BLACK);
            }
            float textWidth = mPaint.measureText(day);
            float x = width / 2 - textWidth / 2;

            Paint.FontMetrics metrics = mPaint.getFontMetrics();
            //metrics.ascent为负数
            float dy = -(metrics.descent + metrics.ascent) / 2;
            float y = height / 2 + dy;
            canvas.drawText(day, x, y, mPaint);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = height = Math.min(getMeasuredWidth(), getMeasuredHeight());
        if (width < DensityUtils.dp2px(context, dpWidthOrHeight)) {
            width = height = DensityUtils.dp2px(context, dpWidthOrHeight);
        }
        centerPoint = width / 2;
    }

    public void setDuty(boolean duty) {
        isDuty = duty;
        invalidate();
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
        invalidate();
    }

    public void setDay(int day) {
        this.day = String.valueOf(day);
        invalidate();
    }

    private int getDutyColor() {
        return getResources().getColor(R.color.duty);
    }

    /**
     * 绘制有班时的日期
     *
     * @param canvas
     */
    private void drawDay(Canvas canvas, @ColorInt int color) {
        mPaint.setColor(color);
        int topOffset = width / 6 - width / 12; //上面偏移1/6个像素

        float textWidth = mPaint.measureText(day);
        float x = width / 2 - textWidth / 2;

        Paint.FontMetrics metrics = mPaint.getFontMetrics();
        //metrics.ascent为负数
        float dy = -(metrics.descent + metrics.ascent) / 2;
        float y = height / 4 + topOffset + dy;
        canvas.drawText(day, x, y, mPaint);
    }

    /**
     * 绘制班字
     *
     * @param canvas
     * @param color
     */
    private void drawDuty(Canvas canvas, @ColorInt int color) {
        mPaint.setColor(color);
        mPaint.setTextSize(DensityUtils.sp2px(context, 12));
        //绘制班字
        float textWidth2 = mPaint.measureText(duty);
        float x2 = width / 2 - textWidth2 / 2;

        Paint.FontMetrics metrics2 = mPaint.getFontMetrics();
        //metrics.ascent为负数
        float dy2 = -(metrics2.descent + metrics2.ascent) / 2;
        float y2 = height / 3 * 2 + dy2 + width / 12;
        canvas.drawText(duty, x2, y2, mPaint);
    }

}
