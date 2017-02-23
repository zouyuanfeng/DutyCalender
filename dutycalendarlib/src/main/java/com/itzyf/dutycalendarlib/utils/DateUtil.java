package com.itzyf.dutycalendarlib.utils;

import java.util.Calendar;

public class DateUtil {

    public static String[] weekName = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    public static int getMonthDays(int year, int month) {
        if (month > 12) {
            month = 1;
            year += 1;
        } else if (month < 1) {
            month = 12;
            year -= 1;
        }
        int[] arr = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int days = 0;

        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            arr[1] = 29; // 闰年2月29天  
        }

        try {
            days = arr[month - 1];
        } catch (Exception e) {
            e.getStackTrace();
        }

        return days;
    }

    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int getCurrentMonthDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static int getWeekDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    public static int getHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }


    /**
     * 获取第一天是周几
     *
     * @return
     */
    public static int getPreFirstDayOfWeek() {
        return getFirstDayOfWeek(-1);
    }

    public static int getFirstDayOfWeek() {
        return getFirstDayOfWeek(0);
    }

    public static int getNextFirstDayOfWeek() {
        return getFirstDayOfWeek(1);
    }

    /**
     * 周日：1---周六：7
     *
     * @param offset
     * @return
     */
    private static int getFirstDayOfWeek(int offset) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, offset);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.get(Calendar.DAY_OF_WEEK)-1;
    }

    public static int getPreMonthDays() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        return getMonthDays(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);
    }

    public static int getCurrentMonthDays() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        return getMonthDays(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);
    }

    public static int getNextMonthDays() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 1);
        return getMonthDays(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);
    }

}  