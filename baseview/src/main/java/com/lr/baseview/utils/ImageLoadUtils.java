package com.lr.baseview.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yunlan.baseview.R;

/**
 * 网络图片加载工具类
 *
 * @author liushubao
 *         Created by admin on 2017/3/20.
 */

public class ImageLoadUtils {

    /**
     * 加载普通图片的方法，特殊情况下使用统一的默认图片
     *
     * @param context
     * @param picUrl  必须为String的URl、或者R.mipmap类型
     * @param iv
     */
    public static void loadingCommonPic(Context context, Object picUrl, ImageView iv) {
        if (context == null || picUrl == null || iv == null) {
            return;
        }

        Glide.with(context)
                .load(picUrl)
                .asBitmap()
                .placeholder(R.mipmap.img_default_common_bg)
                .error(R.mipmap.img_default_common_bg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)//是否使用缓存
                .into(iv);
    }
    /**
     * 加载大图 如banner
     *
     * @param context
     * @param picUrl  必须为String的URl、或者R.mipmap类型
     * @param iv
     */
    public static void loadingCommonPicBig(Context context, Object picUrl, ImageView iv) {
        if (context == null || picUrl == null || iv == null) {
            return;
        }

        Glide.with(context)
                .load(picUrl)
                .asBitmap()
                .placeholder(R.mipmap.img_default_common_bg_big)
                .error(R.mipmap.img_default_common_bg_big)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)//是否使用缓存
                .into(iv);
    }

    /**
     * 加载欢迎页
     *
     * @param context
     * @param picUrl
     * @param iv
     */
    public static void loadingWelcomePic(Context context, Object picUrl, ImageView iv) {
        if (context == null || picUrl == null || iv == null) {
            return;
        }

        Glide.with(context)
                .load(picUrl)
                .asBitmap()
//                .placeholder(R.mipmap.splash_page)
//                .error(R.mipmap.splash_page)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)//是否使用缓存
                .into(iv);
    }

    /**
     * 加载头像的方法，特殊情况下使用统一的默认图片
     *
     * @param context
     * @param picUrl  必须为String的URl、或者R.mipmap类型
     * @param iv
     * @param type    0为圆形蓝色 1为方形蓝色 2为方形灰色
     */
    public static void loadingCommonPortrait(Context context, Object picUrl, ImageView iv, int type) {
        if (context == null || picUrl == null || iv == null) {
            return;
        }

        Glide.with(context)
                .load(picUrl)
                .asBitmap()
                .placeholder(type == 0 ? R.mipmap.img_default_portrait_cricle : type == 1 ? R.mipmap.img_default_portrait_react_blue : R.mipmap.img_default_portrait_react)
                .error(type == 0 ? R.mipmap.img_default_portrait_cricle : type == 1 ? R.mipmap.img_default_portrait_react_blue : R.mipmap.img_default_portrait_react)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)//是否使用缓存
                .into(iv);
    }
}
