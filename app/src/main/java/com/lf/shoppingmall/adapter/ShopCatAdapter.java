package com.lf.shoppingmall.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.bean.index.GoodsVo;
import com.lf.shoppingmall.bean.index.GuigeVo;
import com.lf.shoppingmall.common.OnExpandItemClidkListener;
import com.lf.shoppingmall.utils.LogUtils;
import com.lr.baseview.base.BaseRvAdapter;
import com.lr.baseview.common.OnRvItemClickListener;
import com.lr.baseview.utils.ImageLoadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/11.
 */

public class ShopCatAdapter extends BaseRvAdapter<GoodsVo> {

    public boolean isDel = false;

    public OnExpandItemClidkListener expandItemClidkListener;

    public ShopCatAdapter(Context context, ArrayList<GoodsVo> datas, OnExpandItemClidkListener listener) {
        super(context, datas);
        this.expandItemClidkListener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyviewHolder(inflater.inflate(R.layout.item_shop_cat_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyviewHolder myViewHolder = (MyviewHolder) holder;
        GoodsVo goodsVo = list.get(position);
        LogUtils.e("购物车", "position---？" + position);
        LogUtils.e("购物车", "count---？" + getItemCount());
        //0 1选中 3购买 4加 5减  todo6输入数字
        onClick(myViewHolder.ll_item, position, -1, -1);//item点击事件
        if(isDel){
            myViewHolder.iv_select.setImageResource(goodsVo.getDelStatus() == 0 ? R.mipmap.img_pay_unselected : R.mipmap.img_pay_selected);

        }else {
            myViewHolder.iv_select.setImageResource(goodsVo.getCarSstatus() == 0 ? R.mipmap.img_pay_unselected : R.mipmap.img_pay_selected);
        }
        onClick(myViewHolder.iv_select, position, -1, 1);//item点击事件
        ImageLoadUtils.loadingCommonPic(context, goodsVo.getImage(), myViewHolder.iv_biz);

        myViewHolder.tv_select_num.setText("已选" + goodsVo.getTotalWeight() + "斤");
        String text = "小计:￥" + goodsVo.getCurrentPrice();
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        spannable.setSpan(new ForegroundColorSpan(Color.RED), 3, text.length(),
                //setSpan时需要指定的 flag,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括).
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        myViewHolder.tv_goods_total.setText(spannable);

        StringBuilder titleBuilder = new StringBuilder();
        if (!TextUtils.isEmpty(goodsVo.getBrand())){
            titleBuilder.append("[");
            titleBuilder.append(goodsVo.getBrand());
            titleBuilder.append("]");
        }
        titleBuilder.append(goodsVo.getFullName());
        if (!TextUtils.isEmpty(goodsVo.getFeature())){
            titleBuilder.append(" ");
            titleBuilder.append(goodsVo.getFeature());
        }
        myViewHolder.tv_title.setText(titleBuilder.toString());
            List<GuigeVo> guigeVos = goodsVo.getGuige();
        if (guigeVos != null && !guigeVos.isEmpty()) {
            myViewHolder.ll_detail.removeAllViews();
                int i = 0;
                for (GuigeVo guigeVo : guigeVos) {
                    myViewHolder.ll_detail.addView(getGuigeView(guigeVo, goodsVo.getBaseSpec(), position, i));
                    i++;
                }
        }
    }

    private View getGuigeView(GuigeVo guigeVo, String uint, int position, int childPosition) {
        View view = inflater.inflate(R.layout.include_shop_cat_cate, null);
        ImageView iv_select = (ImageView) view.findViewById(R.id.iv_select);
        // 0  1选择 4加 5减  todo6输入数字
        onClick(iv_select, position, childPosition, 1);
        if (!isDel){
            iv_select.setImageResource(guigeVo.getStatus() == 0 ? R.mipmap.img_pay_unselected : R.mipmap.img_pay_selected);
        }else {
            iv_select.setImageResource(guigeVo.getDelStatus() == 0 ? R.mipmap.img_pay_unselected : R.mipmap.img_pay_selected);
        }

        TextView tv_biz_des = (TextView) view.findViewById(R.id.tv_biz_des);
        StringBuilder desBuilder = new StringBuilder();
        desBuilder.append("￥");
        desBuilder.append(guigeVo.getCurrentPrice());
        desBuilder.append("/");
        desBuilder.append(uint);
        if (!TextUtils.isEmpty(guigeVo.getTotalWeight())){
            desBuilder.append("(");
            desBuilder.append(guigeVo.getTotalWeight());
            desBuilder.append("斤)");

        }
        tv_biz_des.setText(desBuilder.toString());

        TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
        tv_price.setText("￥" + guigeVo.getAvgPrice());

        TextView tv_price_uint = (TextView) view.findViewById(R.id.tv_price_uint);
        tv_price_uint.setText("/斤");
        //需求不显示最低价标志
//        TextView tv_price_old = (TextView) view.findViewById(R.id.tv_price_old);
//        if (Float.valueOf(guigeVo.getOldPrice()) > 0) {
//            tv_price_old.setText("￥" + guigeVo.getOldPrice());
//            tv_price_old.setVisibility(View.VISIBLE);
//            tv_price_old.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//        } else {
//            tv_price_old.setVisibility(View.GONE);
//        }

        LinearLayout ll_add_sub = (LinearLayout) view.findViewById(R.id.ll_add_sub);
        if (isDel){
            ll_add_sub.setVisibility(View.GONE);
        }else {
            ll_add_sub.setVisibility(View.VISIBLE);
            int carGoodNum = guigeVo.getCarGoodNum();
            TextView tv_add = (TextView) view.findViewById(R.id.tv_add);
            onClick(tv_add, position, childPosition, 4);
            TextView tv_sub = (TextView) view.findViewById(R.id.tv_sub);
            onClick(tv_sub, position, childPosition, 5);
            TextView tv_num = (TextView) view.findViewById(R.id.tv_num);
            onClick(tv_num, position, childPosition, 6);
            tv_num.setText("" + carGoodNum);

            TextView tv_max_hint = (TextView) view.findViewById(R.id.tv_max_hint);
//            int totalWeight = Integer.valueOf(guigeVo.getTotalWeight());
//            if (totalWeight > 0){
//                tv_max_hint.setText("可购"+totalWeight+"件");
//                tv_max_hint.setVisibility(View.VISIBLE);
//            }else {
//                tv_max_hint.setVisibility(View.GONE);
//            }
        }
        return view;
    }

    private class MyviewHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_item;
        private ImageView iv_select;
        private ImageView iv_biz;
        private TextView tv_title;
        private LinearLayout ll_detail;
        private TextView tv_select_num;
        private TextView tv_goods_total;

        public MyviewHolder(View itemView) {
            super(itemView);
            ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
            iv_select = (ImageView) itemView.findViewById(R.id.iv_select);
            iv_biz = (ImageView) itemView.findViewById(R.id.iv_biz);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            ll_detail = (LinearLayout) itemView.findViewById(R.id.ll_detail);
            tv_select_num = (TextView) itemView.findViewById(R.id.tv_select_num);
            tv_goods_total = (TextView) itemView.findViewById(R.id.tv_goods_total);
        }
    }

    private void onClick(View view, final int position, final int childPosition, final int type) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandItemClidkListener != null) {
                    expandItemClidkListener.onItemClick(position, childPosition, type);
                }
            }
        });
    }
}
