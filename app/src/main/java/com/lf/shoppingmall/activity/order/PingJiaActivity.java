package com.lf.shoppingmall.activity.order;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.base.BaseActivity;
import com.lr.baseview.utils.ImmersionStatus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 评价配送员
 * Created by Administrator on 2017/9/6.
 */

public class PingJiaActivity extends BaseActivity {

    @BindView(R.id.iv_0)
    ImageView iv0;
    @BindView(R.id.iv_1)
    ImageView iv1;
    @BindView(R.id.iv_2)
    ImageView iv2;
    @BindView(R.id.iv_3)
    ImageView iv3;
    @BindView(R.id.iv_4)
    ImageView iv4;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ping_jia;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);
    }

    @Override
    protected Object getTitleText() {
        return "评价";
    }

    @OnClick({R.id.iv_0, R.id.iv_1, R.id.iv_2, R.id.iv_3, R.id.iv_4, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_0: {
                String tag0 = (String) iv0.getTag();
                if (tag0.equals("yes")) {
                    iv0.setTag("no");
                    iv0.setImageResource(R.mipmap.star_empty);

                    String tag1 = (String) iv1.getTag();
                    if (tag1.equals("yes")) {
                        iv1.setTag("no");
                        iv1.setImageResource(R.mipmap.star_empty);
                    }

                    String tag2 = (String) iv2.getTag();
                    if (tag2.equals("yes")) {
                        iv2.setTag("no");
                        iv2.setImageResource(R.mipmap.star_empty);
                    }

                    String tag3 = (String) iv3.getTag();
                    if (tag3.equals("yes")) {
                        iv3.setTag("no");
                        iv3.setImageResource(R.mipmap.star_empty);
                    }

                    String tag4 = (String) iv4.getTag();
                    if (tag4.equals("yes")) {
                        iv4.setTag("no");
                        iv4.setImageResource(R.mipmap.star_empty);
                    }

                } else {
                    iv0.setTag("yes");
                    iv0.setImageResource(R.mipmap.star_full);
                }
            }
                break;

            case R.id.iv_1: {
                String tag1 = (String) iv1.getTag();
                if (tag1.equals("yes")) {
                    iv1.setTag("no");
                    iv1.setImageResource(R.mipmap.star_empty);

                    String tag2 = (String) iv2.getTag();
                    if (tag2.equals("yes")) {
                        iv2.setTag("no");
                        iv2.setImageResource(R.mipmap.star_empty);
                    }

                    String tag3 = (String) iv3.getTag();
                    if (tag3.equals("yes")) {
                        iv3.setTag("no");
                        iv3.setImageResource(R.mipmap.star_empty);
                    }

                    String tag4 = (String) iv4.getTag();
                    if (tag4.equals("yes")) {
                        iv4.setTag("no");
                        iv4.setImageResource(R.mipmap.star_empty);
                    }

                } else {
                    iv0.setTag("yes");
                    iv0.setImageResource(R.mipmap.star_full);
                    iv1.setTag("yes");
                    iv1.setImageResource(R.mipmap.star_full);
                }
            }
                break;

            case R.id.iv_2:{
                String tag2 = (String) iv2.getTag();
                if (tag2.equals("yes")) {
                    iv2.setTag("no");
                    iv2.setImageResource(R.mipmap.star_empty);

                    String tag3 = (String) iv3.getTag();
                    if (tag3.equals("yes")) {
                        iv3.setTag("no");
                        iv3.setImageResource(R.mipmap.star_empty);
                    }

                    String tag4 = (String) iv4.getTag();
                    if (tag4.equals("yes")) {
                        iv4.setTag("no");
                        iv4.setImageResource(R.mipmap.star_empty);
                    }

                } else {
                    iv0.setTag("yes");
                    iv0.setImageResource(R.mipmap.star_full);
                    iv1.setTag("yes");
                    iv1.setImageResource(R.mipmap.star_full);
                    iv2.setTag("yes");
                    iv2.setImageResource(R.mipmap.star_full);
                }
            }
                break;
            case R.id.iv_3:{
                String tag3 = (String) iv3.getTag();
                if (tag3.equals("yes")) {
                    iv3.setTag("no");
                    iv3.setImageResource(R.mipmap.star_empty);

                    String tag4 = (String) iv4.getTag();
                    if (tag4.equals("yes")) {
                        iv4.setTag("no");
                        iv4.setImageResource(R.mipmap.star_empty);
                    }

                } else {
                    iv0.setTag("yes");
                    iv0.setImageResource(R.mipmap.star_full);
                    iv1.setTag("yes");
                    iv1.setImageResource(R.mipmap.star_full);
                    iv2.setTag("yes");
                    iv2.setImageResource(R.mipmap.star_full);
                    iv3.setTag("yes");
                    iv3.setImageResource(R.mipmap.star_full);
                }
        }
                break;
            case R.id.iv_4:{
                String tag4 = (String) iv4.getTag();
                if (tag4.equals("yes")) {
                    iv4.setTag("no");
                    iv4.setImageResource(R.mipmap.star_empty);

                } else {
                    iv0.setTag("yes");
                    iv0.setImageResource(R.mipmap.star_full);
                    iv1.setTag("yes");
                    iv1.setImageResource(R.mipmap.star_full);
                    iv2.setTag("yes");
                    iv2.setImageResource(R.mipmap.star_full);
                    iv3.setTag("yes");
                    iv3.setImageResource(R.mipmap.star_full);
                    iv4.setTag("yes");
                    iv4.setImageResource(R.mipmap.star_full);
                }
            }
                break;


            case R.id.btn_login:
                //// TODO: 2017/9/11 联网
                showLoading("");
                showToast("评价成功，感谢你对本次配送员的评价！");
                dissmissLoading();
                finish();
                break;
        }
    }
}
