package com.itzyf.dutycalendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.itzyf.dutycalendarlib.view.DutyCalendar;

/**
 * @author 依风听雨
 * @version 2017/2/22
 */
public class MainActivity extends AppCompatActivity {
    private DutyCalendar dutyCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dutyCalendar = (DutyCalendar) findViewById(R.id.duty);
        Integer pre[] = {1, 2, 3, 11, 12, 13, 14, 17, 19};
        Integer current[] = {1, 5, 9, 11, 12, 13, 14, 17, 19};
        Integer next[] = {2, 3, 4, 5, 11, 12, 13, 14, 15, 21};
        dutyCalendar.setCurrentListDuty(current);
        dutyCalendar.setPreListDuty(pre);
        dutyCalendar.setNextListDuty(next);
    }
}
