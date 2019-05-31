package com.lr.baseview.utils;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by admin on 2017/3/30.
 */

public class LGAdapterViewHolder {

    public static <T extends View> T get(View convertView, int resId) {
        SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<>();
            convertView.setTag(viewHolder);
        }

        View childView = viewHolder.get(resId);
        if (childView == null) {
            childView = convertView.findViewById(resId);
            viewHolder.put(resId, childView);
        }
        return (T) childView;
    }
}
