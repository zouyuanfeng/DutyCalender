package com.itzyf.dutycalendarlib;

import com.itzyf.dutycalendarlib.utils.DateUtil;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        System.out.println(DateUtil.getCurrentMonthDays());
        System.out.println(DateUtil.getFirstDayOfWeek());
    }
}