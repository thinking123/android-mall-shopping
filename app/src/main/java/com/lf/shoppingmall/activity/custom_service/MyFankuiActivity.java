package com.lf.shoppingmall.activity.custom_service;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.adapter.GoodsDetailCateGridAdapter;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.bean.UserVo;
import com.lr.baseview.utils.ImmersionStatus;
import com.lr.baseview.widget.AlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 快速反馈
 * Created by Administrator on 2017/9/6.
 */

public class MyFankuiActivity extends BaseActivity {
    @BindView(R.id.rv_cate)
    GridView rvCate;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.et_phone)
    EditText et_phone;
    private GoodsDetailCateGridAdapter cateGridAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fankui;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

        UserVo userVo = ((BaseApplication) getApplication()).getUserVo();
        et_phone.setText(userVo.getStoreTelephone());

        String[] strings = getResources().getStringArray(R.array.fankuai_cate);
        cateGridAdapter = new GoodsDetailCateGridAdapter(context, null, strings);
        cateGridAdapter.curruntCate = strings.length - 1;
        rvCate.setAdapter(cateGridAdapter);
        rvCate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 4) {
                    new AlertDialog(context).builder()
                            .setTitle("提示")
                            .setMsg("确定反馈 新品需求 信息?")
                            .setNegativeButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(MyFankuiActivity.this, SubmitGoodsActivity.class));
                                    finish();
                                }
                            })
                            .setPositiveButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            })
                            .show();
                } else {
                    cateGridAdapter.curruntCate = position;
                    cateGridAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected Object getTitleText() {
        return "快速反馈";
    }

    @OnClick(R.id.btn_login)
    public void onClick() {
        String content = etContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            showToast("请填写反馈内容!");
            return;
        }
        String phone = et_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            showToast("请填写联系电话!");
            return;
        }

        showLoading("");
        showToast("反馈成功");
        dissmissLoading();
        finish();
    }
}
