package com.lf.shoppingmall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.bean.service.ComProlemContent;
import com.lf.shoppingmall.bean.service.CommonProlem;
import com.lr.baseview.base.BaseLGAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/9/6.
 */

public class ComProblemAdapter extends BaseLGAdapter {

    public ComProblemAdapter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object object = datas.get(position);
        if (object instanceof CommonProlem) {
//            if (convertView != null && (Integer)convertView.getTag() == 0) {
//            } else {
                convertView = inflater.inflate(R.layout.item_com_problem_title, null);
//                convertView.setTag(0);
//            }
            TextView title = ViewHolder.get(convertView, R.id.tv_title);
            CommonProlem commonProlem = (CommonProlem) object;
            title.setText(commonProlem.getName());

        } else if (object instanceof ComProlemContent) {
//            if (convertView != null && (Integer)convertView.getTag() == 1) {
//            } else {
                convertView = inflater.inflate(R.layout.item_common_text, null);
//                convertView.setTag(1);
//            }
            TextView text = ViewHolder.get(convertView, R.id.tv_text);
            ComProlemContent content = (ComProlemContent) object;
            text.setText((position + 1) + "、" + content.getQuestion() + "?");
        }
        return convertView;
    }
}
