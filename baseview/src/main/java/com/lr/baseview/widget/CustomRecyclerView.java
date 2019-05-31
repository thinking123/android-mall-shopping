package com.lr.baseview.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;


/**解决RecyclerView与ScrollView冲突
 * Created by herui on 2016/11/11.
 */

public class CustomRecyclerView extends RecyclerView{
    //记录当前第一个View
    private View mCurrentView;

    public CustomRecyclerView(Context context) {
        super(context);
//        this.setOnScrollListener(this);
    }

    public CustomRecyclerView(Context context, AttributeSet attrs){
        super(context,attrs);
//        this.setOnScrollListener(this);
    }

    public CustomRecyclerView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
//        this.setOnScrollListener(this);
    }
    /**
     * 滚动时回调的接口
     */
    private OnItemScrollChangeListener mItemScrollChangeListener;

    public void setOnItemScrollChangeListener(OnItemScrollChangeListener mItemScrollChangeListener) {
        this.mItemScrollChangeListener = mItemScrollChangeListener;
    }

    public interface OnItemScrollChangeListener {
        void onChange(View view, int position);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        mCurrentView = getChildAt(0);

        if (mItemScrollChangeListener != null) {
            mItemScrollChangeListener.onChange(mCurrentView, getChildPosition(mCurrentView));
        }
    }
//    @Override
//    public boolean onTouchEvent(MotionEvent e) {
//        if (e.getAction() == MotionEvent.ACTION_MOVE) {
//            mCurrentView = getChildAt(0);
//
//            if (mItemScrollChangeListener != null) {
//                mItemScrollChangeListener.onChange(mCurrentView,
//                        getChildPosition(mCurrentView));
//
//            }
//        }
//
//        return super.onTouchEvent(e);
//    }


    @Override
    public void onScrolled(int dx, int dy) {
        View newView = getChildAt(0);

        if (mItemScrollChangeListener != null)
        {
            if (newView != null && newView != mCurrentView)
            {
                mCurrentView = newView ;
                mItemScrollChangeListener.onChange(mCurrentView,
                        getChildPosition(mCurrentView));
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}

