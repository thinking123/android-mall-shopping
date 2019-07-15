package com.lf.shoppingmall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.bean.AllOrder.GoodsCateVo;
import com.lr.baseview.base.BaseLGAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */

public class BottomPopCommonAdapter extends BaseLGAdapter {

    public BottomPopCommonAdapter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_bottom_pop, null);
        }
        TextView tv = ViewHolder.get(convertView, R.id.tv_name);
        Object object = datas.get(position);
        if (object instanceof GoodsCateVo){
            GoodsCateVo goodsCateVo = (GoodsCateVo) object;
            tv.setText(goodsCateVo.getCategoryName());
        }
        return convertView;
    }
}
