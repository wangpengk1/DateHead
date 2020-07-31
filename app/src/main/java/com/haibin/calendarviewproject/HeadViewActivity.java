package com.haibin.calendarviewproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.haibin.calendarview.DateHeadView;

import java.util.Calendar;

public class HeadViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_view);
        DateHeadView dateHeadView = findViewById(R.id.header);

//        Calendar now = Calendar.getInstance();
//        Calendar pre = Calendar.getInstance();
//        pre.add(Calendar.MONTH,-1);
//        dateHeadView.setSelectMode(2);
//        dateHeadView.getCalendarView().setSelectCalendarRange(convertCalendar(pre),convertCalendar(now));

    }

    private com.haibin.calendarview.Calendar convertCalendar(java.util.Calendar toConvert)
    {
        com.haibin.calendarview.Calendar calendar  = new com.haibin.calendarview.Calendar();
        calendar.setYear(toConvert.get(Calendar.YEAR));
        calendar.setMonth(toConvert.get(Calendar.MONTH)+1);
        calendar.setDay(toConvert.get(Calendar.DAY_OF_MONTH));
        return  calendar;
    }
}