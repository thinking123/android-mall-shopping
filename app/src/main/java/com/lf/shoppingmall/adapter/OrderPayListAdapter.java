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
import com.lr.baseview.base.BaseLGAdapter;
import com.lr.baseview.utils.ImageLoadUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/9/2.
 */

public class OrderPayListAdapter extends BaseLGAdapter<GoodsVo> {

    public OrderPayListAdapter(Context context, List<GoodsVo> datas) {
        super(context, datas);
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
        iv_select.setVisibility(View.GONE);
        ImageLoadUtils.loadingCommonPic(context, goodsVo.getImage(), iv_biz);

        float totalPrice = 0;
        int totalWeight = 0;
        for (GuigeVo guige : goodsVo.getGoodsSpec()){
            if (guige.getCarGoodState() == 1){
                totalWeight += guige.getCarGoodNum() * Float.valueOf(guige.getTotalWeight());
                totalPrice +=  guige.getCarGoodNum()* Float.valueOf(guige.getCurrentPrice());
            }
        }
        tv_select_num.setText("已选" + totalWeight + "斤");
        String text = "小计:￥" +totalPrice;
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        spannable.setSpan(new ForegroundColorSpan(Color.RED), 3, text.length(),
                //setSpan时需要指定的 flag,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括).
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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
        View view = inflater.inflate(R.layout.item_order_pay_list_guige, null);

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

        TextView tv_car_num = (TextView) view.findViewById(R.id.tv_car_num);
        tv_car_num.setText("X" + guigeVo.getCarGoodNum());

        return view;
    }
}
