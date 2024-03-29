package com.lf.shoppingmall.adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.lr.baseview.base.BaseLGAdapter;
import com.lr.baseview.common.OnRvItemClickListener;
import com.lr.baseview.utils.CommonUilts;
import com.lr.baseview.utils.ImageLoadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */

public class ShopCatLiListAdapter extends BaseLGAdapter<GoodsVo> {

    public boolean isDel = false;

    public OnExpandItemClidkListener expandItemClidkListener;

    public ShopCatLiListAdapter(Context context, ArrayList<GoodsVo> datas, OnExpandItemClidkListener listener) {
        super(context, datas);
        this.expandItemClidkListener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_shop_cat_adapter, parent, false);
        }

        LinearLayout ll_item =  ViewHolder.get(convertView, R.id.ll_item);
        ImageView iv_select=  ViewHolder.get(convertView, R.id.iv_select);
        ImageView iv_biz=  ViewHolder.get(convertView, R.id.iv_biz);
        TextView tv_title=  ViewHolder.get(convertView, R.id.tv_title);
        LinearLayout ll_detail=  ViewHolder.get(convertView, R.id.ll_detail);
        TextView tv_select_num=  ViewHolder.get(convertView, R.id.tv_select_num);
        TextView tv_goods_total=  ViewHolder.get(convertView, R.id.tv_goods_total);

        GoodsVo goodsVo = datas.get(position);
        //0 1选中 3购买 4加 5减  todo6输入数字
        onClick(ll_item, position, -1, -1);//item点击事件
        if(isDel){
            iv_select.setImageResource(goodsVo.getDelStatus() == 0 ? R.mipmap.img_pay_unselected : R.mipmap.img_pay_selected);
        }else {
            iv_select.setImageResource(goodsVo.getCarSstatus() == 0 ? R.mipmap.img_pay_unselected : R.mipmap.img_pay_selected);
        }
        onClick(iv_select, position, -1, 1);//item点击事件
        ImageLoadUtils.loadingCommonPic(context, goodsVo.getImage(), iv_biz);
        float totalPrice = 0;
        int totalWeight = 0;
        for (GuigeVo guige : goodsVo.getGuige()){
            if (guige.getCarGoodState() == 1){
                totalWeight += guige.getCarGoodNum() * Float.valueOf(guige.getTotalWeight());
                totalPrice +=  guige.getCarGoodNum()* Float.valueOf(guige.getCurrentPrice());
            }
        }
        String text0 = "已选" + totalWeight + "斤";
        SpannableStringBuilder spannable0 = new SpannableStringBuilder(text0);
        spannable0.setSpan(new ForegroundColorSpan(Color.RED), 2, text0.length()-1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_select_num.setText(spannable0);

        String text = "小计:￥" + CommonUilts.getDoubleTwo(totalPrice);
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        spannable.setSpan(new ForegroundColorSpan(Color.RED), 3, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_goods_total.setText(spannable);

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
        tv_title.setText(titleBuilder.toString());
        List<GuigeVo> guigeVos = goodsVo.getGuige();
        if (guigeVos != null && !guigeVos.isEmpty()) {
            ll_detail.removeAllViews();
            int i = 0;
            for (GuigeVo guigeVo : guigeVos) {
                ll_detail.addView(getGuigeView(guigeVo, goodsVo.getBaseSpec(), position, i));
                i++;
            }
        }
        return convertView;
    }


    private View getGuigeView(GuigeVo guigeVo, String uint, int position, int childPosition) {
        View view = inflater.inflate(R.layout.include_shop_cat_cate, null);
        ImageView iv_select = (ImageView) view.findViewById(R.id.iv_select);
        // 0  1选择 4加 5减  todo6输入数字
        onClick(iv_select, position, childPosition, 1);
        if (!isDel){
            iv_select.setImageResource(guigeVo.getCarGoodState() == 0 ? R.mipmap.img_pay_unselected : R.mipmap.img_pay_selected);
        }else {
            iv_select.setImageResource(guigeVo.getDelStatus() == 0 ? R.mipmap.img_pay_unselected : R.mipmap.img_pay_selected);
        }

        TextView tv_price_feature = (TextView) view.findViewById(R.id.tv_price_feature);
        tv_price_feature.setVisibility(guigeVo.getDiscount().equals("1") ? View.VISIBLE : View.GONE);

        TextView tv_biz_des = (TextView) view.findViewById(R.id.tv_biz_des);
        StringBuilder desBuilder = new StringBuilder();
        desBuilder.append("￥");
        desBuilder.append(guigeVo.getCurrentPrice());
        desBuilder.append("元/");
        desBuilder.append(uint);
        if (!TextUtils.isEmpty(guigeVo.getTotalWeight())){
            desBuilder.append("(");
            desBuilder.append(guigeVo.getTotalWeight());
            desBuilder.append("斤)");

        }
        SpannableStringBuilder spannable = new SpannableStringBuilder(desBuilder.toString());
        spannable.setSpan(new ForegroundColorSpan(Color.RED), 0, guigeVo.getCurrentPrice().length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_biz_des.setText(spannable);

        TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
        tv_price.setText("￥" + guigeVo.getAvgPrice() + "元");

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
