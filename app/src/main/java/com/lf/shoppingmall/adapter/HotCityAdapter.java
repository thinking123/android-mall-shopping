package com.lf.shoppingmall.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.bean.address.CityVo;
import com.lr.baseview.base.BaseLGAdapter;
import com.lr.baseview.base.BaseRvAdapter;
import com.lr.baseview.common.OnRvItemClickListener;

import java.util.List;

/**
 * 热门城市adapter
 * Created by Administrator on 2017/8/7.
 */

public class HotCityAdapter extends BaseLGAdapter<CityVo> {

    public HotCityAdapter(Context context, List list, OnRvItemClickListener listener) {
        super(context, list, listener);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_city_list_hot, parent, false);
        }
        TextView tv_text = ViewHolder.get(convertView, R.id.tv_text);
        CityVo cityVo = (CityVo) datas.get(i);
        tv_text.setText(cityVo.getCityCaption());
        commonViewOnClick(tv_text, i, 0);
        if (cityVo.isSelect()){
            tv_text.setBackgroundResource(R.drawable.bg_corner25_storke_green);
            tv_text.setTextColor(ContextCompat.getColor(context, R.color.text_green));
        }else {
            tv_text.setBackgroundResource(R.drawable.bg_corner25_storke_grey);
            tv_text.setTextColor(ContextCompat.getColor(context, R.color.text_norm_6));
        }
        return convertView;
    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        return new MyViewHolder(inflater.inflate(R.layout.item_city_list_hot, viewGroup, false));
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
//        MyViewHolder vh = (MyViewHolder) viewHolder;
//        CityVo cityVo = (CityVo) list.get(i);
//        tv_text.setText(cityVo.getCityCaption());
//        commonViewOnClick(tv_text, i, 0);
//        if (cityVo.isSelect()){
//            tv_text.setBackgroundResource(R.drawable.bg_corner25_storke_green);
//            tv_text.setTextColor(ContextCompat.getColor(context, R.color.text_green));
//        }else {
//            tv_text.setBackgroundResource(R.drawable.bg_corner25_storke_grey);
//            tv_text.setTextColor(ContextCompat.getColor(context, R.color.text_norm_6));
//        }
//    }
//
//    public class MyViewHolder extends RecyclerView.ViewHolder{
//
//        public TextView tv_text;
//
//        public MyViewHolder(View itemView) {
//            super(itemView);
//            tv_text = (TextView) itemView.findViewById(R.id.tv_text);
//        }
//    }
}
