package com.lf.shoppingmall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.bean.index.GoodsVo;
import com.lf.shoppingmall.bean.other.MyOrder;
import com.lf.shoppingmall.utils.ComUtils;
import com.lr.baseview.base.BaseLGAdapter;
import com.lr.baseview.base.BaseRvAdapter;
import com.lr.baseview.common.OnRvItemClickListener;
import com.lr.baseview.utils.DateUtils;
import com.lr.baseview.utils.ImageLoadUtils;

import java.util.List;

/**
 * 我的订单
 * Created by Administrator on 2017/8/11.
 */

public class MyOrderAdapter extends BaseLGAdapter<MyOrder> {

    private int orderType;

    public MyOrderAdapter(Context context, List<MyOrder> list, OnRvItemClickListener listener, int orderType) {
        super(context, list, listener);
        this.orderType = orderType;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_my_order, parent, false);
        }
        LinearLayout ll_item = ViewHolder.get(convertView, R.id.ll_item);
        LinearLayout ll_other = ViewHolder.get(convertView, R.id.ll_other);
        TextView tv_date = ViewHolder.get(convertView, R.id.tv_date);
        TextView tv_order_status = ViewHolder.get(convertView, R.id.tv_order_status);
        ImageView iv_icon0 = ViewHolder.get(convertView, R.id.iv_icon0);
        ImageView iv_icon1 = ViewHolder.get(convertView, R.id.iv_icon1);
        ImageView iv_icon2 = ViewHolder.get(convertView, R.id.iv_icon2);
        ImageView iv_icon3 = ViewHolder.get(convertView, R.id.iv_icon3);
        TextView tv_cate = ViewHolder.get(convertView, R.id.tv_cate);
        TextView tv_price = ViewHolder.get(convertView, R.id.tv_price);
        TextView btn_quxiao = ViewHolder.get(convertView, R.id.btn_quxiao);
        TextView btn_agin = ViewHolder.get(convertView, R.id.btn_agin);

        MyOrder items = datas.get(position);
        commonViewOnClick(ll_item, position, 0);
        commonViewOnClick(btn_agin, position, 1);

        String time = !TextUtils.isEmpty(items.getTime()) ? items.getTime() :
                !TextUtils.isEmpty(items.getOkTime()) ? items.getOkTime() :  items.getYujiTime();
        tv_date.setText("下单时间\t" + DateUtils.timedate(time));

        List<GoodsVo> goodsVoList = items.getGoods();
        String ivUrl0 = goodsVoList.get(0).getImage();
        ImageLoadUtils.loadingCommonPic(context, ivUrl0, iv_icon0);
        int size = goodsVoList.size();
        tv_cate.setText("共" + size + "类商品");
        if (size > 1) {
            iv_icon1.setVisibility(View.VISIBLE);
            String ivUrl1 = goodsVoList.get(1).getImage();
            ImageLoadUtils.loadingCommonPic(context, ivUrl1, iv_icon0);
        } else {
            iv_icon1.setVisibility(View.INVISIBLE);
        }

        if (size > 2) {
            iv_icon2.setVisibility(View.VISIBLE);
            String ivUr2 = goodsVoList.get(2).getImage();
            ImageLoadUtils.loadingCommonPic(context, ivUr2, iv_icon2);
        } else {
            iv_icon2.setVisibility(View.INVISIBLE);
        }

        if (size > 3) {
            iv_icon3.setVisibility(View.VISIBLE);
            String ivUrl3 = goodsVoList.get(3).getImage();
            ImageLoadUtils.loadingCommonPic(context, ivUrl3, iv_icon3);
        } else {
            iv_icon3.setVisibility(View.INVISIBLE);
        }

        tv_order_status.setText(ComUtils.getOrderStatus(items.getOrderStatus()));
        tv_price.setText("￥" + items.getTotalPrice());
        if (items.getOrderStatus() == 0) {//带配送
            ll_other.setVisibility(View.VISIBLE);
            btn_quxiao.setText("取消购买");
            commonViewOnClick(btn_quxiao, position, 2);

        }
        else if (items.getOrderStatus() == 2) {//已完成
            ll_other.setVisibility(View.VISIBLE);
            btn_quxiao.setText("确认收货");
            commonViewOnClick(btn_quxiao, position, 4);

        }
        else if (items.getOrderStatus() == 3) {//已完成
            ll_other.setVisibility(View.VISIBLE);
            btn_quxiao.setText("评价");
            commonViewOnClick(btn_quxiao, position, 3);

        }
        else {
            ll_other.setVisibility(View.GONE);
        }
        btn_agin.setVisibility(View.GONE);

        return convertView;
    }
}
