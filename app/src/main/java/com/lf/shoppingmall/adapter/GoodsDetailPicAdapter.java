package com.lf.shoppingmall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lf.shoppingmall.R;
import com.lr.baseview.base.BaseRvAdapter;
import com.lr.baseview.common.OnRvItemClickListener;

import java.util.List;

/**
 * Created by Administrator on 2017/8/11.
 */

public class GoodsDetailPicAdapter extends BaseRvAdapter {

    public GoodsDetailPicAdapter(Context context, List list, OnRvItemClickListener listener) {
        super(context, list, listener);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_goods_detail_pic, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.iv_icon.setImageResource(R.mipmap.guide_3);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
        }
    }
}
