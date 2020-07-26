package com.haibin.calendarview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.haibin.calendarview.simple.CustomRangeMonthView;
import com.haibin.calendarview.simple.CustomRangeWeekView;
import com.haibin.calendarview.simple.SimpleMonthView;
import com.haibin.calendarview.simple.SimpleWeekView;

import java.util.ArrayList;
import java.util.List;

public class DateHeadView extends FrameLayout {

    private View rootView;
    private CalendarView mCalendarView;
    private TextView mYearTextView;
    private TextView mMonthTextView;

    public interface OnWeekChangeListener {
        void onWeekChange(List<Calendar> weekCalendars);
    }

    /**
     * 快速年份切换
     */
    CalendarView.OnYearChangeListener mYearChangeListener;


    /**
     * 月份切换事件
     */
    CalendarView.OnMonthChangeListener mMonthChangeListener;

    private OnWeekChangeListener weekChangeListener;
    private CalendarView.OnYearChangeListener yearChangeListener;
    private CalendarView.OnMonthChangeListener monthChangeListener;


    public void setWeekChangeListener(OnWeekChangeListener weekChangeListener) {
        this.weekChangeListener = weekChangeListener;
    }

    public void setYearChangeListener(CalendarView.OnYearChangeListener yearChangeListener) {
        this.yearChangeListener = yearChangeListener;
    }

    public void setMonthChangeListener(CalendarView.OnMonthChangeListener monthChangeListener) {
        this.monthChangeListener = monthChangeListener;
    }



    public DateHeadView(Context context)
    {
        super(context);
        initView();
    }

    public DateHeadView(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        initView();
    }


    private void initView()
    {
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.header_layout,this,true);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCalendarView = rootView.findViewById(R.id.calendarView);
        mYearTextView = rootView.findViewById(R.id.text_year);
        mMonthTextView = rootView.findViewById(R.id.text_month);
        mCalendarView.getMonthViewPager().setVisibility(GONE);
        mCalendarView.getWeekViewPager().setVisibility(VISIBLE);
        java.util.Calendar cur = java.util.Calendar.getInstance();
        mYearTextView.setText(cur.get(java.util.Calendar.YEAR)+"年");
        mMonthTextView.setText(cur.get(java.util.Calendar.MONTH)+"月");

        findViewById(R.id.head_left_layout).setOnClickListener(view1->{
            if(mCalendarView.getMonthViewPager().getVisibility()!=VISIBLE)
            {
                mCalendarView.getMonthViewPager().setVisibility(VISIBLE);
                mCalendarView.getWeekViewPager().setVisibility(GONE);

            }
            else
            {
                mCalendarView.getMonthViewPager().setVisibility(GONE);
                mCalendarView.getWeekViewPager().getAdapter().notifyDataSetChanged();
                mCalendarView.getWeekViewPager().setVisibility(VISIBLE);
            }
        });

        mCalendarView.setOnWeekChangeListener(weekCalendars->{
            int year = 0;
            ArrayList<Integer> months = new ArrayList<>();
            for(Calendar calendar:weekCalendars)
            {
                if(calendar.getYear()!=year) year=calendar.getYear();
                if(!months.contains(calendar.getMonth())) months.add(calendar.getMonth());
            }
            mYearTextView.setText(year+"年");
            mMonthTextView.setText(months.size()>1?(months.get(0)+"-"+months.get(1)+"月"):(months.get(0)+"月"));

            if(weekChangeListener!=null)
            {
                weekChangeListener.onWeekChange(weekCalendars);
            }
        });

        mCalendarView.setOnYearChangeListener(year -> {
            mYearTextView.setText(year+"年");
            if(yearChangeListener!=null)
            {
                yearChangeListener.onYearChange(year);
            }
        });
        mCalendarView.setOnMonthChangeListener((year, month) -> {
            mMonthTextView.setText(month+"月");
            if(monthChangeListener!=null)
            {
                monthChangeListener.onMonthChange(year,month);
            }
        });
    }




    public CalendarView getCalendarView()
    {
        return mCalendarView;
    }

    public void setSelectMode(int mode)
    {
        switch (mode)
        {
            case 0:
                mCalendarView.setSelectDefaultMode();
                mCalendarView.setWeekView(SimpleWeekView.class);
                mCalendarView.setMonthView(SimpleMonthView.class);
                break;
            case 1:
                mCalendarView.setSelectSingleMode();
                mCalendarView.setWeekView(SimpleWeekView.class);
                mCalendarView.setMonthView(SimpleMonthView.class);
                break;
            case 2:
                mCalendarView.setSelectRangeMode();
                mCalendarView.setWeekView(CustomRangeWeekView.class);
                mCalendarView.setMonthView(CustomRangeMonthView.class);
                break;
            default:
                break;
        }
    }

    public static Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }

}