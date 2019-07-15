package com.lf.shoppingmall.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.bean.AllOrder.GoodsCateVo;
import com.lr.baseview.base.BaseRvAdapter;
import com.lr.baseview.common.OnRvItemClickListener;

import java.util.List;

/**
 *
 * Created by Administrator on 2017/8/10.
 */

public class AllOrderCateAdapter extends BaseRvAdapter<GoodsCateVo> {

    public int curruntSelect = 0;
    public AllOrderCateAdapter(Context context, List list, OnRvItemClickListener listener) {
        super(context, list, listener);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_city_list_common, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        MyViewHolder vh = (MyViewHolder) viewHolder;
        GoodsCateVo cateVo = list.get(i);
            vh.tv_text.setText(cateVo.getCategoryName());
        commonViewOnClick(vh.tv_text, i, 0);
        if (curruntSelect == i){
            vh.tv_text.setTextColor(ContextCompat.getColor(context, R.color.text_green));
            vh.tv_text.setBackgroundResource(R.color.white);
        }else {
            vh.tv_text.setTextColor(ContextCompat.getColor(context, R.color.text_norm_8));
            vh.tv_text.setBackgroundResource(R.color.bg_default);
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_text;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_text = (TextView) itemView.findViewById(R.id.tv_text);
        }
    }
}
