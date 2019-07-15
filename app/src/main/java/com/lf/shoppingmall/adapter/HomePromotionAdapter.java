package com.lf.shoppingmall.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.bean.index.ProductionInfoVo;
import com.lf.shoppingmall.bean.index.PromotionVo;
import com.lr.baseview.base.BaseLGAdapter;
import com.lr.baseview.base.BaseRvAdapter;
import com.lr.baseview.common.OnRvItemClickListener;
import com.lr.baseview.utils.ImageLoadUtils;

import java.util.List;

/**
 * 首页活动的adapter
 * Created by Administrator on 2017/8/10.
 */

public class HomePromotionAdapter extends BaseLGAdapter<PromotionVo> {

    public HomePromotionAdapter(Context context, List list, OnRvItemClickListener listener) {
        super(context, list, listener);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_home_promotion, parent, false);
        }

        ImageView iv_icon = ViewHolder.get(convertView, R.id.iv_icon);
        iv_icon.setImageResource(R.mipmap.guide_2);
        PromotionVo promotionVo = datas.get(position);
        Glide.with(context)
                .load(promotionVo.getActivityImg())
                .asBitmap()
                .placeholder(R.mipmap.img_default_common_bg_activity)
                .error(R.mipmap.img_default_common_bg_activity)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)//是否使用缓存
                .into(iv_icon);
//        ImageLoadUtils.loadingCommonPicBig(context, promotionVo.getActivityImg(), iv_icon);
        commonViewOnClick(iv_icon, position, 0);
        return convertView;
    }

//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new MyViewHolder(inflater.inflate(R.layout.item_home_promotion, parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        MyViewHolder myViewHolder = (MyViewHolder) holder;
////        myViewHolder.iv_icon.setImageResource(R.mipmap.guide_2);
//        PromotionVo promotionVo = list.get(position);
//        ImageLoadUtils.loadingCommonPic(context, promotionVo.getActivityImg(), myViewHolder.iv_icon);
//        commonViewOnClick(myViewHolder.iv_icon, position, 0);
//    }
//
//    private class MyViewHolder extends RecyclerView.ViewHolder {
//
//        public ImageView iv_icon;
//        public MyViewHolder(View itemView) {
//            super(itemView);
//            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
//        }
//    }
}
