package com.lf.shoppingmall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lf.shoppingmall.R;
import com.lr.baseview.base.BaseRvAdapter;
import com.lr.baseview.common.OnRvItemClickListener;

import java.util.List;

/**
 * 商品详情参数的adapter
 * Created by Administrator on 2017/8/11.
 */

public class GoodsDetailParamAdapter extends BaseRvAdapter {

    private  List<String> type;
    private  List<String> typeStr;

    public GoodsDetailParamAdapter(Context context, List list,   List<String> type, List<String> typeStr) {
        super(context, list);
        this.type = type;
        this.typeStr = typeStr;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_goods_detail_params, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.tv_hint.setText(type.get(position));
        myViewHolder.tv_name.setText(typeStr.get(position));
    }

    @Override
    public int getItemCount() {
        return type.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_hint;
        private TextView tv_name;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_hint = (TextView) itemView.findViewById(R.id.tv_hint);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
