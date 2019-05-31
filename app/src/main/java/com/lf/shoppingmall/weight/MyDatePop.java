package com.lf.shoppingmall.weight;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.lf.shoppingmall.R;
import com.lr.baseview.widget.wheel.OnWheelChangedListener;
import com.lr.baseview.widget.wheel.WheelView;
import com.lr.baseview.widget.wheel.adapters.ArrayWheelAdapter;

/**
 * Created by Administrator on 2017/9/4.
 */

public class MyDatePop extends PopupWindow {

    //    private int currentMonth;
//    private int currentDay;
    private OnDatePopListener datePopListener;
    private String[] months;
    private String[] dayWeeks;
    private String[] dayMonths;

    public MyDatePop(Context context, int currentMonth, int currentDay, OnDatePopListener datePopListener) {
        super(context);
//        this.currentMonth = currentMonth;
//        this.currentDay = currentDay;
        this.datePopListener = datePopListener;
        months = context.getResources().getStringArray(R.array.fapiao_months);
        dayWeeks = context.getResources().getStringArray(R.array.fapiao_weeks);
        dayMonths = getDayMonths();
        init(context, currentMonth, currentDay);
    }

    private void init(final Context context, int currentMonth, int currentDay) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_pop_date, null);

        final WheelView wheel_month = (WheelView) view.findViewById(R.id.wheel_month);
        final WheelView wheel_day = (WheelView) view.findViewById(R.id.wheel_day);

        wheel_month.setViewAdapter(new ArrayWheelAdapter<String>(context, months));
        wheel_day.setViewAdapter(new ArrayWheelAdapter<String>(context, currentMonth == 2 ? dayMonths : dayWeeks));

        wheel_month.setCurrentItem(currentMonth);
        wheel_month.setVisibleItems(5);
        wheel_day.setCurrentItem(currentDay);
        wheel_day.setVisibleItems(5);
        wheel_month.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                int monthIndex = wheel_month.getCurrentItem();
                if (monthIndex >= 2) {
                    wheel_day.setViewAdapter(new ArrayWheelAdapter<String>(context, dayMonths));
                    wheel_day.setCurrentItem(0);
                } else {
                    wheel_day.setViewAdapter(new ArrayWheelAdapter<String>(context, dayWeeks));
                    wheel_day.setCurrentItem(0);
                }
            }
        });

        view.findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (datePopListener != null) {
                    int monthIndex = wheel_month.getCurrentItem();
                    int dayIndex = wheel_day.getCurrentItem();
                    datePopListener.onDateClick(months[monthIndex], monthIndex == 2 ? dayMonths[dayIndex] : dayWeeks[dayIndex], monthIndex, dayIndex);
                }
                MyDatePop.this.dismiss();
            }
        });

        View empty = view.findViewById(R.id.view_pop_empty);
        empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        ColorDrawable bg = new ColorDrawable(0x00000000);
        setBackgroundDrawable(bg);
        setOutsideTouchable(true);
        setFocusable(true);
    }

    public String[] getDayMonths() {
        dayMonths = new String[31];
        for (int i = 1; i <= 31; i++) {
            dayMonths[i - 1] = i + "日";
        }
        return dayMonths;
    }

    public interface OnDatePopListener {
        void onDateClick(String month, String day, int currentMonth, int currentDay);
    }
}
