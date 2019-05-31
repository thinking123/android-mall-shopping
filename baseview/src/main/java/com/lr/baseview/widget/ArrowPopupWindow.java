package com.lr.baseview.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lr.baseview.utils.DensityUtil;
import com.yunlan.baseview.R;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

/**
 * 带箭头的popupWindow
 * 用法：
 * PopupWindow popupWindow = new ArrowPopupWindow(context).Builder()
 * .addSheetItem("标题",  new ArrowPopupWindow.OnSheetItemClickListener() {
 * public void onClick(int which) {
 * }
 * })
 * .build();
 *
 * @author xiezhenyu 2016/6/3.
 */
public class ArrowPopupWindow extends PopupWindow {
    private Context mContext;
    private PopupWindow mPopupWindow;
    private DisplayMetrics dm;
    private List<SheetItem> sheetItemList;
    private LinearLayout lLayout_content;
    private ScrollView sLayout_content;

    public ArrowPopupWindow(Context context) {
        this.mContext = context;
        dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
    }

    public ArrowPopupWindow Builder() {
        // 获取PopupWindow布局
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.view_arrow_popup, null);

        // 获取自定义PopupWindow布局中的控件
        sLayout_content = (ScrollView) view.findViewById(R.id.sLayout_content);
        lLayout_content = (LinearLayout) view
                .findViewById(R.id.lLayout_content);

        // 定义PopupWindow布局和参数
        mPopupWindow = new PopupWindow(mContext);
        mPopupWindow.setContentView(view);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);

        // 设置弹出窗体可点击
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        // 刷新状态
        mPopupWindow.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xffffff);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismissListener ，设置其他控件变化等操作
        mPopupWindow.setBackgroundDrawable(dw);
        return this;
    }

    /**
     * @param strItem 条目名称
     */
    public ArrowPopupWindow addSheetItem(String strItem,
                                         OnSheetItemClickListener listener) {
        if (sheetItemList == null) {
            sheetItemList = new ArrayList<SheetItem>();
        }
        sheetItemList.add(new SheetItem(strItem, null, null, null, listener));
        return this;
    }

    /**
     * @param strItem 条目名称
     * @param hide    是否隐藏
     */
    public ArrowPopupWindow addSheetItem(String strItem,
                                         OnSheetItemClickListener listener, boolean hide) {
        if (!hide) {
            if (sheetItemList == null) {
                sheetItemList = new ArrayList<SheetItem>();
            }
            sheetItemList.add(new SheetItem(strItem, null, null, null, listener));
        }
        return this;
    }

    /**
     * @param strItem 条目名称
     * @param color   条目字体颜色，设置null则默认（蓝-白）
     * @param bg      条目背景颜色，设置null则默认（白-蓝）
     */
    public ArrowPopupWindow addSheetItem(String strItem, SheetItemColorRes color, SheetItemBgRes bg,
                                         OnSheetItemClickListener listener) {
        if (sheetItemList == null) {
            sheetItemList = new ArrayList<SheetItem>();
        }
        sheetItemList.add(new SheetItem(strItem, color, bg, null, listener));
        return this;
    }

    /**
     * @param strItem    条目名称
     * @param color      条目字体颜色，设置null则默认（蓝-白）
     * @param drawableId 资源id
     */
    public ArrowPopupWindow addSheetItem(String strItem, SheetItemColorRes color, int drawableId,
                                         OnSheetItemClickListener listener) {
        if (sheetItemList == null) {
            sheetItemList = new ArrayList<SheetItem>();
        }
        Drawable drawable = ContextCompat.getDrawable(mContext, drawableId);
        sheetItemList.add(new SheetItem(strItem, color, null, drawable, listener));
        return this;
    }

    /**
     * 设置条目布局
     */
    private void setSheetItems() {
        if (sheetItemList == null || sheetItemList.size() <= 0) {
            return;
        }

        int size = sheetItemList.size();

        // 添加条目过多的时候控制高度(高度控制，非最佳解决办法)
        if (size >= 12) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) sLayout_content
                    .getLayoutParams();
            params.height = dm.heightPixels / 2;
            sLayout_content.setLayoutParams(params);
        }

        // 循环添加条目
        for (int i = 1; i <= size; i++) {
            final int index = i;
            SheetItem sheetItem = sheetItemList.get(i - 1);
            String strItem = sheetItem.name;
            SheetItemColorRes color = sheetItem.color;
            SheetItemBgRes bg = sheetItem.bg;
            Drawable drawable = sheetItem.drawable;
            final OnSheetItemClickListener listener = sheetItem.itemClickListener;

            TextView textView = new TextView(mContext);
            textView.setText(strItem);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            textView.setGravity(Gravity.CENTER);

            // 背景
            if (bg == null) {
                textView.setBackgroundResource(SheetItemBgRes.WhiteBlue.getId());
            } else {
                textView.setBackgroundResource(bg.getId());
            }

            // 字体颜色
            int id = SheetItemColorRes.BlueWhite.getId();
            if (color != null) {
                id = color.getId();
            }
            XmlPullParser xrp = mContext.getResources().getXml(id);
            try {
                ColorStateList csl = ColorStateList.createFromXml(mContext.getResources(), xrp);
                textView.setTextColor(csl);
            } catch (Exception e) {
                textView.setTextColor(ContextCompat.getColor(mContext, R.color.blue));
            }
            if (drawable != null) {
                textView.setGravity(GravityCompat.START | Gravity.CENTER_VERTICAL);
                textView.setCompoundDrawablePadding(DensityUtil.dip2px(mContext, 6));
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                textView.setCompoundDrawables(drawable, null, null, null);
            }

            // 宽高
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(mContext, 45));
            textView.setLayoutParams(params);
            textView.setPadding(DensityUtil.dip2px(mContext, 63), 0, DensityUtil.dip2px(mContext, 40), 0);

            // 点击事件
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(index);
                    mPopupWindow.dismiss();
                }
            });

            lLayout_content.addView(textView);
        }
    }

    public PopupWindow build() {
        setSheetItems();
        return sheetItemList == null || sheetItemList.size() <= 0 ? null : mPopupWindow;
    }

    public interface OnSheetItemClickListener {
        void onClick(int which);
    }

    public class SheetItem {
        String name;
        OnSheetItemClickListener itemClickListener;
        SheetItemColorRes color;
        SheetItemBgRes bg;
        Drawable drawable;

        public SheetItem(String name, SheetItemColorRes color, SheetItemBgRes bg, Drawable drawable,
                         OnSheetItemClickListener itemClickListener) {
            this.name = name;
            this.color = color;
            this.bg = bg;
            this.drawable = drawable;
            this.itemClickListener = itemClickListener;
        }
    }

    public enum SheetItemColorRes {
        BlueWhite(R.drawable.color_blue_white), Black(R.drawable.bg_pressed_status_white);

        private int id;

        SheetItemColorRes(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public enum SheetItemBgRes {
        WhiteBlue(R.drawable.btn_popup_arrow_middle);

        private int id;

        SheetItemBgRes(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

}
