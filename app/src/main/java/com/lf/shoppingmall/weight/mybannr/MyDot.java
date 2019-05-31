package com.lf.shoppingmall.weight.mybannr;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.lf.shoppingmall.R;


/**
 * 页面小圆点标识
 *
 * @author liushubao
 *         Created by Administrator on 2017/2/15.
 */

public class MyDot extends LinearLayout {

    private int num;

    private int width_height = 10;
    private int leftMargin = 15;

    public MyDot(Context context) {
        this(context, null);
    }

    public MyDot(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyDot(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyDot);
        width_height = a.getDimensionPixelSize(R.styleable.MyDot_with_height, width_height);
        leftMargin = a.getDimensionPixelSize(R.styleable.MyDot_leftMargin, leftMargin);
        init();
    }

    private void init() {
//        this.setBackgroundResource(R.drawable.dot_bg_corners3);
        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setGravity(Gravity.CENTER);
        this.setPadding(10, 10, 10, 10);
    }

    public void setCountDot(int num) {
        if (num > 0) {
            this.num = num;
            for (int i = 0; i < num; i++) {
                View view = new View(getContext());
                view.setBackgroundResource(R.drawable.dot_selector);
                LayoutParams params = new LayoutParams(width_height, width_height);
                if (i != 0) {
                    params.leftMargin = leftMargin;
                }
                view.setLayoutParams(params);
                view.setEnabled(i == 0 ? true : false);
                this.addView(view);
            }
        }
    }

    public void refreshStatus(int index) {
        if (index >= 0 && index < num) {
            for (int i = 0; i < num; i++) {
                if (index == i)
                    this.getChildAt(i).setEnabled(true);
                else
                    this.getChildAt(i).setEnabled(false);
            }
        }
    }
}
