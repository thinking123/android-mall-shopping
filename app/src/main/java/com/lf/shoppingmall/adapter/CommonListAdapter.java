package com.lf.shoppingmall.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.bean.address.CityItemPYVo;
import com.lf.shoppingmall.bean.address.CityVo;
import com.lr.baseview.base.BaseLGAdapter;
import com.lr.baseview.base.BaseRvAdapter;
import com.lr.baseview.common.OnRvItemClickListener;

import java.util.List;

/**
 * 普通城市列表的adapter
 * Created by Administrator on 2017/8/8.
 */

public class  CommonListAdapter extends BaseLGAdapter {
    public CommonListAdapter(Context context, List list, OnRvItemClickListener listener) {
        super(context, list, listener);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_city_list_common1, parent, false);
        }
        TextView tv_text = ViewHolder.get(convertView, R.id.tv_text);
        Object cityVo = datas.get(position);
        if (cityVo instanceof CityItemPYVo) {
            tv_text.setText(((CityItemPYVo) cityVo).getName());
            tv_text.setTextColor(ContextCompat.getColor(context, R.color.text_norm_8));
            tv_text.setBackgroundResource(R.color.grey);
        } else {
            CityVo cityVo1 = (CityVo) cityVo;
            tv_text.setText(cityVo1.getCityCaption());
            if (cityVo1.isSelect()){
                tv_text.setTextColor(ContextCompat.getColor(context, R.color.text_green));
            }else {
                tv_text.setTextColor(ContextCompat.getColor(context, R.color.text_norm_6));
            }
            commonViewOnClick(tv_text, position, 1);
        }
        return convertView;
    }

//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new MyViewHolder(inflater.inflate(R.layout.item_city_list_common, parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
//        MyViewHolder vh = (MyViewHolder) viewHolder;
//        Object cityVo = list.get(i);
//        if (cityVo instanceof CityItemPYVo) {
//            tv_text.setText(((CityItemPYVo) cityVo).getName());
//            tv_text.setTextColor(ContextCompat.getColor(context, R.color.text_norm_8));
//            tv_text.setBackgroundResource(R.color.grey);
//        } else {
//            CityVo cityVo1 = (CityVo) cityVo;
//            tv_text.setText(cityVo1.getCityCaption());
//            if (cityVo1.isSelect()){
//                tv_text.setTextColor(ContextCompat.getColor(context, R.color.text_green));
//            }else {
//                tv_text.setTextColor(ContextCompat.getColor(context, R.color.text_norm_6));
//            }
//            commonViewOnClick(tv_text, i, 1);
//        }
//    }
//
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//
//        public TextView tv_text;
//
//        public MyViewHolder(View itemView) {
//            super(itemView);
//            tv_text = (TextView) itemView.findViewById(R.id.tv_text);
//        }
//    }
}
