package com.lf.shoppingmall.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lf.shoppingmall.R;
import com.lr.baseview.base.BaseRvAdapter;
import com.lr.baseview.common.OnRvItemClickListener;

import java.util.List;

/**
 * Created by Administrator on 2017/8/11.
 */

public class GoodsDetailCateAdapter extends BaseRvAdapter {

    public int curruntCate;
    private String[] datas;

    public GoodsDetailCateAdapter(Context context, List list, OnRvItemClickListener listener, String[] datas) {
        super(context, list, listener);
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyVH(inflater.inflate(R.layout.item_goods_detail_cate, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyVH myVH = (MyVH) holder;
        myVH.tv_cate.setText(datas[position]);
        commonViewOnClick(myVH.tv_cate, position, 0);
        if (curruntCate == position){
            myVH.tv_cate.setTextColor(ContextCompat.getColor(context, R.color.white));
            myVH.tv_cate.setBackgroundResource(R.drawable.bg_green_corner5);
        }else {
            myVH.tv_cate.setTextColor(ContextCompat.getColor(context, R.color.text_norm_6));
            myVH.tv_cate.setBackgroundResource(R.drawable.bg_corner8_grey);
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.length;
    }

    private class MyVH extends RecyclerView.ViewHolder{

        public TextView tv_cate;
        public MyVH(View itemView) {
            super(itemView);
            tv_cate = (TextView) itemView.findViewById(R.id.tv_cate);
        }
    }
}
