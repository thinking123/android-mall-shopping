package com.lr.baseview.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.yunlan.baseview.R;

/**
 * 列表popupWindow
 *
 * @author xiezhenyu 2017/2/17.
 */
public class ListPopupWindow extends PopupWindow {
    private Activity mContext;
    private DisplayMetrics dm;
    private View mShadowView;
    private ListView mListView;
    private BaseAdapter mAdapter;
    /**
     * 最大高度：item高度的倍数
     */
    private int mMaxHeightMultiplier;
    /**
     * 最大高度：px
     */
    private int mMaxHeightPixels;

    public ListPopupWindow(Activity context, BaseAdapter adapter) {
        this(context, adapter, null);
    }

    public ListPopupWindow(Activity context, View shadowView) {
        this(context, null, shadowView);
    }

    /**
     * @param shadowView 阴影view
     */
    public ListPopupWindow(Activity context, BaseAdapter adapter, View shadowView) {
        super(context);
        mContext = context;
        dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        mAdapter = adapter;
        mShadowView = shadowView;
        init();
    }

    private void init() {
        mListView = new ListView(mContext);
        mListView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        mListView.setDividerHeight(0);
        mListView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        mListView.setSelector(R.drawable.bg_pressed_white_gray);
        mListView.setVerticalScrollBarEnabled(false);
        setContentView(mListView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable dw = new ColorDrawable(0x00000000); // 实例化一个ColorDrawable颜色为半透明
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景；使用该方法点击窗体之外，才可关闭窗体
        setBackgroundDrawable(dw); // Background不能设置为null，dismiss会失效
        setOutsideTouchable(true);
        setFocusable(true);
        setAnimationStyle(R.style.PopupWindowAnimation);
        if (mAdapter != null) {
            mListView.setAdapter(mAdapter);
        }
        if (mShadowView != null) {
            setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss() {
                    showShadowView(false);
                }
            });
        }
    }

    public void setAdapter(BaseAdapter adapter) {
        this.mAdapter = adapter;
        mListView.setAdapter(mAdapter);
    }

    public void setMaxHeightPixels(int pixels) {
        this.mMaxHeightPixels = pixels;
    }

    public void setMaxHeightMultiplier(int multiplier) {
        this.mMaxHeightMultiplier = multiplier;
    }

    private void setTotalHeight() {
        ListAdapter listAdapter = mListView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        int itemHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, mListView);
            listItem.measure(0, 0);
            itemHeight = listItem.getMeasuredHeight();
            totalHeight += listItem.getMeasuredHeight();
        }
        int maxHeight = Math.max(mMaxHeightPixels, mMaxHeightMultiplier * itemHeight);

        ViewGroup.LayoutParams params = mListView.getLayoutParams();
        params.height = Math.min(totalHeight + (mListView.getDividerHeight() * (listAdapter.getCount() - 1)), maxHeight == 0 ? dm.heightPixels / 3 : maxHeight);

        mListView.setLayoutParams(params);
        setHeight(params.height);
    }

    public BaseAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        showShadowView(true);
        setTotalHeight();
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
        showShadowView(true);
        setTotalHeight();
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);
        showShadowView(true);
        setTotalHeight();
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        showShadowView(true);
        setTotalHeight();
    }

    private void showShadowView(boolean show) {
        if (mShadowView != null) {
            mShadowView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    public void setOnPopupItemClickListener(final OnPopupItemClickListener listener) {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null) {
                    Object obj = mAdapter.getItem(position);
                    listener.onItemClick(position);
                    ListPopupWindow.this.dismiss();
                }
            }
        });
    }

    public void setOnPopupDismissListener(final OnPopupDismissListener listener) {
        if (listener != null) {
            setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss() {
                    listener.onDismiss();
                    if (mShadowView != null) {
                        showShadowView(false);
                    }
                }
            });
        }
    }

    public interface OnPopupItemClickListener {
        void onItemClick(int position);
    }

    public interface OnPopupDismissListener {
        void onDismiss();
    }
}
