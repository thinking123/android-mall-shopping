package com.lr.baseview.widget;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yunlan.baseview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ListPopupWindow的标准适配器
 * T必须实现ListPopupExtra接口
 *
 * @author xiezhenyu 2016/7/25.
 */
public class ListPopupAdapter extends BaseAdapter {
    private List<String> list;
    private LayoutInflater inflater;
    private Context context;
    private int pos = -1;

    public ListPopupAdapter(Context context, List list) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public ListPopupAdapter(Context context, String[] strings) {
        if (strings != null && strings.length > 0) {
            if (this.list == null) {
                this.list = new ArrayList<>();
            }
            for (String str : strings) {
                list.add(str);
            }
        }
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 设置选中状态
     *
     * @param pos 选中条目的index
     */
    public void setDataAndRefresh(List<String> list, int pos) {
        this.list = list;
        this.pos = pos >= 0 && pos < list.size() ? pos : 0;
        notifyDataSetChanged();
    }
    /**
     * 设置选中状态
     *
     * @param pos 选中条目的index
     */
    public void setDataAndRefresh(String[] strings, int type, int pos) {
        if (strings != null && strings.length > 0) {

            if (this.list == null) {
                this.list = new ArrayList<>();
            }

            if (type <= 1) {
                this.list.clear();
            }

            for (String str : strings) {
                this.list.add(str);
            }
        }
        this.pos = pos >= 0 && pos < list.size() ? pos : 0;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_team_popup_text, parent, false);
            holder.text = (CheckableTextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String t = list.get(position);
        holder.text.setChecked(pos != position ? false : true);
        holder.text.setText(t);
        return convertView;
    }

    class ViewHolder {
        CheckableTextView text;
    }
}
