package com.lf.shoppingmall.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.bean.address.MyAddress;
import com.lf.shoppingmall.bean.address.MyAddressListBean;
import com.lf.shoppingmall.utils.LogUtils;
import com.lr.baseview.base.BaseLGAdapter;
import com.lr.baseview.base.BaseRvAdapter;
import com.lr.baseview.common.OnRvItemClickListener;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28.
 */

public class ReceiverAdapter extends BaseLGAdapter<MyAddress> {

    private OnRvItemClickListener listener;

    public ReceiverAdapter(Context context, List<MyAddress> datas,OnRvItemClickListener listener) {
        super(context, datas);
        this.listener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_recerver, parent, false);
        }
        LinearLayout ll_item = ViewHolder.get(convertView, R.id.ll_item);
        TextView tv_name = ViewHolder.get(convertView, R.id.tv_name);
        TextView tv_phone = ViewHolder.get(convertView, R.id.tv_phone);
        TextView tv_hint = ViewHolder.get(convertView, R.id.tv_hint);
        TextView tv_update = ViewHolder.get(convertView, R.id.tv_update);

        MyAddress address = datas.get(position);
        tv_name.setText(address.getStoreName());
        tv_phone.setText(address.getTelPhone());
        if (address.getAddrDefault().equals("1")) {
            Drawable select = context.getResources().getDrawable(R.mipmap.img_pay_selected);
            select.setBounds(0, 0, select.getMinimumWidth(),select.getMinimumHeight());
            tv_hint.setCompoundDrawables(select, null, null, null);
            tv_hint.setText("默认收货人");
        } else {
            Drawable select = context.getResources().getDrawable(R.mipmap.img_pay_unselected);
            select.setBounds(0, 0, select.getMinimumWidth(),select.getMinimumHeight());
            tv_hint.setCompoundDrawables(select, null, null, null);
            tv_hint.setText("设置默认收货人");
        }

        ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onItemClick(position, 1);
                }
            }
        });

        tv_hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onItemClick(position, 0);
                }
            }
        });
        return convertView;
    }

//    public ReceiverAdapter(Context context, List list, OnRvItemClickListener listener) {
//        super(context, list, listener);
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new MyVh(inflater.inflate(R.layout.item_recerver, parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        MyVh myVh = (MyVh) holder;
//        LogUtils.e("收获人列表", "listBean.size()-->" + getItemCount());
//
//        MyAddress address = list.get(position);
//        myVh.tv_name.setText(address.getStoreName());
//        myVh.tv_phone.setText(address.getTelPhone());
//        if (address.getAddrDefault().equals("1")) {
//            Drawable select = context.getResources().getDrawable(R.mipmap.img_pay_selected);
//            select.setBounds(0, 0, select.getMinimumWidth(),select.getMinimumHeight());
//            myVh.tv_hint.setCompoundDrawables(select, null, null, null);
//            myVh.tv_hint.setText("默认收货人");
//        } else {
//            Drawable select = context.getResources().getDrawable(R.mipmap.img_pay_unselected);
//            select.setBounds(0, 0, select.getMinimumWidth(),select.getMinimumHeight());
//            myVh.tv_hint.setCompoundDrawables(select, null, null, null);
//            myVh.tv_hint.setText("设置默认收货人");
//        }
//
//        commonViewOnClick(myVh.tv_hint, position, 0);
//    }
//
//    private class MyVh extends RecyclerView.ViewHolder {
//
//        private TextView tv_name;
//        private TextView tv_phone;
//        private TextView tv_hint;
//        private TextView tv_update;
//
//        public MyVh(View itemView) {
//            super(itemView);
//            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
//            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);
//            tv_hint = (TextView) itemView.findViewById(R.id.tv_hint);
//            tv_update = (TextView) itemView.findViewById(R.id.tv_update);
//        }
//    }

}
