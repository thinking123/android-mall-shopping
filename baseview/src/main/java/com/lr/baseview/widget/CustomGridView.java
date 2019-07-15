package com.lr.baseview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 *解决 GridView 与 ScrollView 冲突
 * @author liushubao
 * Created by Administrator on 2017/2/15.
 */

public class CustomGridView extends GridView{

    public CustomGridView(Context context) {
        super(context);
    }
    public CustomGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public CustomGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
