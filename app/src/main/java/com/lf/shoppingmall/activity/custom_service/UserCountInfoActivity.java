package com.lf.shoppingmall.activity.custom_service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.bean.UserVo;
import com.lf.shoppingmall.bean.address.MyAddress;
import com.lr.baseview.utils.ImmersionStatus;
import com.lr.baseview.widget.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 帐号信息
 * Created by Administrator on 2017/9/4.
 */

public class UserCountInfoActivity extends BaseActivity {
//    @BindView(R.id.civ_my_pic)
//    CircleImageView civMyPic;
    @BindView(R.id.tv_main_account)
    TextView tvMainAccount;
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.tv_store_name)
    TextView tvStoreName;
    @BindView(R.id.tv_head)
    TextView tvHead;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_recever)
    TextView tvRecever;
    @BindView(R.id.tv_recever_time)
    TextView tvReceverTime;
    @BindView(R.id.tv_louceng)
    TextView tvLouceng;
    @BindView(R.id.tv_dianti)
    TextView tvDianti;

    private final int recever = 0;
    private final int update = 1;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_count_info;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

        initInfo();
    }

    private void initInfo() {
        UserVo userVo = ((BaseApplication)getApplication()).getUserVo();
        tvMainAccount.setText(userVo.getStoreTelephone());

        tvId.setText(userVo.getId());
        tvStoreName.setText(userVo.getStoreName());
        tvHead.setText(userVo.getManagerName());
        tvAddress.setText(userVo.getStoreAddress());

        tvRecever.setText(userVo.getManagerName());//// TODO: 2017/9/5  登录返回收货人信息
//        tvReceverTime.setText();
//        tvLouceng.setText();
//        tvDianti.setText();
    }

    @Override
    protected Object getTitleText() {
        return "帐号信息";
    }

    @OnClick({R.id.llchoose_recever, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llchoose_recever:
                startActivityForResult(new Intent(this, ReceiverActivity.class)
                        .putExtra("type", 1), recever);
                break;

            case R.id.btn_login:
                startActivityForResult(new Intent(this, UpdateUserInfoActivity.class), update);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case recever:
                    if (data != null) {
                        MyAddress address = (MyAddress) data.getSerializableExtra("MyAddress");
                        //// TODO: 2017/9/5 保存默认收货人
                        tvRecever.setText(address.getTrueName());
                    }
                    break;

                case update:
                    initInfo();
                    break;
            }
        }
    }
}
