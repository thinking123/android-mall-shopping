package com.lr.baseview.widget.mybannr;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lr.baseview.utils.ImageLoadUtils;
import com.yunlan.baseview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/16.
 */

public class MyBanner extends RelativeLayout implements ViewPager.OnPageChangeListener {

    private ViewPager vp_banner;
    private MyDot ll_myDot;
    private TextView tv_img_desc;
    private ImageView iv_hint;
    private ListView lv;

    private int currntIndex = 0;
    private boolean isSwitch = false;
    private int[] imags;
    private List<String> imageList;//图片集合
    private List<String> decsList;//说明的集合
    private OnImageClickListener listener;

    int length;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (currntIndex >= length) {
                currntIndex = 0;
            }
            vp_banner.setCurrentItem(currntIndex, false);
        }
    };

    public MyBanner(Context context) {
        this(context, null);
    }

    public MyBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        View view = LayoutInflater.from(context).inflate(R.layout.view_my_banner, null);
        vp_banner = (ViewPager) view.findViewById(R.id.vp_banner_biz);
        ll_myDot = (MyDot) view.findViewById(R.id.ll_myDot_biz);
        tv_img_desc = (TextView) view.findViewById(R.id.tv_img_desc);
        iv_hint = (ImageView) view.findViewById(R.id.iv_hint);

        this.addView(view);
    }

    private ArrayList<ImageView> getIvList() {
        final ArrayList<ImageView> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            final ImageView iv = new ImageView(getContext());
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (imageList == null) {
                ImageLoadUtils.loadingCommonPicBig(getContext(), imags[i], iv);
            } else {
                ImageLoadUtils.loadingCommonPicBig(getContext(), imageList.get(i), iv);
            }
            final int index = i;
            iv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onImageClick(index);
                }
            });
            list.add(iv);
        }

        ll_myDot.setCountDot(list.size());
        return list;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        ll_myDot.refreshStatus(position);
        if (decsList != null && position < decsList.size()) {
            tv_img_desc.setText(decsList.get(position));
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    /**
     * 设置当前的页面
     *
     * @param currntIndex
     */
    public void setCurrntIndex(int currntIndex) {
        this.currntIndex = currntIndex;
        vp_banner.setCurrentItem(this.currntIndex);
    }

    /**
     * 是否自动滑动
     *
     * @param isSwitch
     */
    public void setIsSwitch(boolean isSwitch) {
        this.isSwitch = isSwitch;
        invalidate();
    }

    /**
     * 设置图片和说明
     *
     * @param imageList 集合形式
     * @param decsList  文字说明的集合
     */
    public void setImagesList(List imageList, List<String> decsList, OnImageClickListener listener) {
        this.imageList = imageList;
        this.decsList = decsList;
        this.listener = listener;
        initVp();
    }

    /**
     * 一般用于测试
     *
     * @param imageList 设置图片数组
     * @param decsList  文字说明的集合
     */
    public void setImagesList(int[] imageList, List<String> decsList, OnImageClickListener listener) {
        this.imags = imageList;
        this.decsList = decsList;
        this.listener = listener;
        initVp();
    }

    /**
     *
     */
    public void setImagesList(List listData, OnImageClickListener listener) {
        if (listData != null && !listData.isEmpty()) {
            this.imageList = new ArrayList();
            this.decsList = new ArrayList<>();
//            for (InfoAdvertVo vo : listData) {
//                this.imageList.add(vo.getAdvertPic());
//                this.decsList.add(vo.getAdvertTitle());
//            }

            this.listener = listener;
            initVp();
        }
    }

    private void initVp() {
        if ((imageList == null || imageList.isEmpty()) && (imags == null || imags.length == 0)) {
            return;
        } else {
            iv_hint.setVisibility(GONE);
        }

        length = (imageList != null ? imageList.size() : imags.length);
        tv_img_desc.setVisibility(decsList == null ? GONE : VISIBLE);

        vp_banner.setAdapter(new MyBannerAdapter(getIvList()));
        vp_banner.setCurrentItem(currntIndex);
        vp_banner.addOnPageChangeListener(this);
        tv_img_desc.setText(decsList.get(currntIndex));

        new Thread() {
            @Override
            public void run() {
                while (!isSwitch) {
                    try {
                        Thread.sleep(2500);
                        currntIndex++;
                        handler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public interface OnImageClickListener {
        void onImageClick(int position);
    }
}
