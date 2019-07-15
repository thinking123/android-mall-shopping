package com.lr.baseview.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.yunlan.baseview.R;

/**
 * Created by admin on 2017/4/3.
 */

public class ComBottomPopupWindow extends PopupWindow{

    private OnPopupDismissListener dismissListener;

    public ComBottomPopupWindow(Context context, OnPopupDismissListener dismissListener, BaseAdapter mAdapter, AdapterView.OnItemClickListener itemLIstener) {
        super(context);
        this.dismissListener = dismissListener;
        init(context,mAdapter, itemLIstener);
    }

    private void init(Context context, BaseAdapter mAdapter, final AdapterView.OnItemClickListener itemLIstener) {

        View view = LayoutInflater.from(context).inflate(R.layout.popup_com_bottom, null);
        ListView lv = (ListView) view.findViewById(R.id.lv_pop);
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemLIstener.onItemClick(parent, view, position, id);
                dismiss();
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
//        setAnimationStyle(R.style.PopupWindowAnimation);
    }

    @Override
    public void dismiss() {
        if (dismissListener != null){
            dismissListener.onDismiss();
        }
        super.dismiss();
    }

    public interface OnPopupDismissListener {
        void onDismiss();
    }
}
