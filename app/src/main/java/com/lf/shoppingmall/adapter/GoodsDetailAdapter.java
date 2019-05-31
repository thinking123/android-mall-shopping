package com.lf.shoppingmall.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lf.shoppingmall.R;
import com.lr.baseview.base.BaseRvAdapter;
import com.lr.baseview.common.OnRvItemClickListener;
import com.lr.baseview.widget.RVCommonItemDecoration;

import java.util.List;

/**
 * Created by Administrator on 2017/8/11.
 */

public class GoodsDetailAdapter extends BaseRvAdapter {

    public GoodsDetailAdapter(Context context, List list, OnRvItemClickListener listener) {
        super(context, list, listener);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return viewType == 0 ? new MyVH0(inflater.inflate(R.layout.item_goods_detail_0, parent, false))
                : viewType == 1 ? new MyVH1(inflater.inflate(R.layout.item_goods_detail_1, parent, false))
                : new MyVH2(inflater.inflate(R.layout.item_goods_detail_2, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (position == 0){
                MyVH0 myVH0 = (MyVH0) holder;
                myVH0.rv_cate.setLayoutManager(new GridLayoutManager(context, 3));
                myVH0.rv_cate.setAdapter(new GoodsDetailCateAdapter(context, null, listener,null));

            }else  if (position == 1){
                MyVH1 myVH1 = (MyVH1) holder;
                myVH1.rv_pic.setLayoutManager(new LinearLayoutManager(context));
                myVH1.rv_pic.setAdapter(new GoodsDetailPicAdapter(context, null, null));

            }else  if (position == 2){
                MyVH2 myVH2 = (MyVH2) holder;
                myVH2.rv_param.setLayoutManager(new LinearLayoutManager(context));
                myVH2.rv_param.addItemDecoration(new RVCommonItemDecoration(context, LinearLayoutManager.VERTICAL));
                myVH2.rv_param.setAdapter(new GoodsDetailParamAdapter(context, null, null,null));
            }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private class MyVH0 extends RecyclerView.ViewHolder{

        public RecyclerView rv_cate;
        public MyVH0(View itemView) {
            super(itemView);
            rv_cate = (RecyclerView) itemView.findViewById(R.id.rv_cate);
        }
    }

    private class MyVH1 extends RecyclerView.ViewHolder{
        public RecyclerView rv_pic;

        public MyVH1(View itemView) {
            super(itemView);
//            rv_pic = (RecyclerView) itemView.findViewById(R.id.rv_pic);
        }
    }

    private class MyVH2 extends RecyclerView.ViewHolder{
        public RecyclerView rv_param;

        public MyVH2(View itemView) {
            super(itemView);
            rv_param = (RecyclerView) itemView.findViewById(R.id.rv_param);
        }
    }
}
