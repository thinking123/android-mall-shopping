package com.lr.baseview.utils;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * 显示日期Dialog, 可通过日期监听接口 {@link OnDateSettingListener}, 获取设置后的日期字符串. <br/>
 * 可以通过{setDateStr}设置日期Dialog的默认日期. <br/>
 * <br/>
 */
public class DatePickerDialogFragment extends DialogFragment implements OnDateSetListener {

    private static final String FRAGMENT_TAG = DatePickerDialogFragment.class.getSimpleName();

    private static final String DEFAULT_REGULAR_STR = "-";

    private String mDateStr = "";
    private String mRegularStr = DEFAULT_REGULAR_STR;

    // 日期设置监听
    private OnDateSettingListener mOnDateSettingListener;

    private long mMaxDate = -1; // 最大时间限制,-1表示不限制
    private long mMinDate = -1; // 最小时间限制,-1表示不限制

    /**
     * 显示Dialog
     *
     * @param manager  : FragmentManager实例
     * @param date     : 日期字符串(yyyy-MM-dd)
     * @param listener : 日期设置监听
     */
    public static void showDialog(FragmentManager manager,
                                  String date, OnDateSettingListener listener) {
        showDialog(manager, date, DEFAULT_REGULAR_STR, listener, -1, -1);
    }

    /**
     * 显示Dialog
     *
     * @param manager  : FragmentManager实例
     * @param date     : 日期字符串(yyyy-MM-dd)
     * @param listener : 日期设置监听
     * @param maxDate  : 最大时间限制
     * @param minDate  : 最小时间限制
     */
    public static void showDialog(FragmentManager manager,
                                  String date, OnDateSettingListener listener, long maxDate, long minDate) {
        showDialog(manager, date, DEFAULT_REGULAR_STR, listener, maxDate, minDate);
    }

    /**
     * 显示Dialog
     *
     * @param manager  : FragmentManager实例
     * @param date     : 日期字符串
     * @param regular  : 日期字符串格式
     * @param listener : 日期设置监听
     * @param maxDate  : 最大时间限制
     * @param minDate  : 最小时间限制
     */
    public static void showDialog(FragmentManager manager, String date,
                                  String regular, OnDateSettingListener listener, long maxDate, long minDate) {
        DatePickerDialogFragment dialog = new DatePickerDialogFragment();
        dialog.setDateStr(date, regular);
        dialog.setOnDateSettingListener(listener);
        dialog.setMaxDate(maxDate);
        dialog.setMinDate(minDate);
        dialog.show(manager, FRAGMENT_TAG);
    }

    /**
     * 设置最大时间限制，-1表示不限制。
     *
     * @param maxDate
     */
    public void setMaxDate(long maxDate) {
        this.mMaxDate = maxDate;
    }

    /**
     * 设置最小时间限制，-1表示不限制。
     *
     * @param minDate
     */
    public void setMinDate(long minDate) {
        this.mMinDate = minDate;
    }

    /**
     * 设置日期字符串及其拆分方式。
     *
     * @param date    : 日期字符串
     * @param regular : 拆分方式
     */
    public void setDateStr(String date, String regular) {
        this.mDateStr = date;
        this.mRegularStr = regular;
    }

    /**
     * 设置日期字符串, 默认其拆分方式为 {@link #DEFAULT_REGULAR_STR}.
     *
     * @param date : 日期字符串
     */
    public void setDateStr(String date) {
        setDateStr(date, DEFAULT_REGULAR_STR);
    }

    /**
     * 设置日期设置监听
     *
     * @param listener : 日期设置监听
     */
    public void setOnDateSettingListener(OnDateSettingListener listener) {
        this.mOnDateSettingListener = listener;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        String month = String.valueOf(monthOfYear + 1);
        if (month.length() == 1) {
            month = '0' + month;
        }

        String day = String.valueOf(dayOfMonth);
        if (day.length() == 1) {
            day = '0' + day;
        }

        String date = year + mRegularStr + month + mRegularStr + day;
        if (null != mOnDateSettingListener) {
            mOnDateSettingListener.onDateSet(date);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        if (!TextUtils.isEmpty(mDateStr)) {
            String[] date = mDateStr.split(mRegularStr);
            if (isValidDate(mDateStr)) {
                year = Integer.parseInt(date[0]);
                month = Integer.parseInt(date[1]) - 1;
                day = Integer.parseInt(date[2]);
            }
        }

        DatePickerDialog dateDialog = new DatePickerDialog(getContext(), this, year, month, day);
        DatePicker datePicker = dateDialog.getDatePicker();
        if (-1 != mMaxDate) {
            datePicker.setMaxDate(mMaxDate);
        }
        if (-1 != mMinDate) {
            datePicker.setMinDate(mMinDate);
        }
        return dateDialog;
    }

    private boolean isValidDate(String date) {
        //set the format to use as a constructor argument
        StringBuilder pattern = new StringBuilder("yyyy");
        pattern.append(mRegularStr).append("MM").append(mRegularStr).append("dd");
        SimpleDateFormat format = new SimpleDateFormat(pattern.toString(),
                Locale.getDefault());
        if (date.length() != format.toPattern().length()) {
            return false;
        }

        format.setLenient(false);
        try {
            //parse the date parameter
            format.parse(date);
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    /**
     * 日期设置监听接口
     */
    public interface OnDateSettingListener {
        /**
         * 日期被设置后调用.
         *
         * @param date : 格式化的日期字符串.
         */
        void onDateSet(String date);
    }

}

