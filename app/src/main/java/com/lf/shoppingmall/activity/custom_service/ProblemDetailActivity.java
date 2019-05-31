package com.lf.shoppingmall.activity.custom_service;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.bean.service.ComProlemContent;
import com.lr.baseview.utils.ImmersionStatus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 问题详情
 * Created by Administrator on 2017/9/6.
 */

public class ProblemDetailActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_title_problem)
    TextView tv_title_problem;
    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_problem_detail;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

        ComProlemContent comProlemContent = (ComProlemContent) getIntent().getSerializableExtra("ComProlemContent");
        tvTitle.setText(getIntent().getStringExtra("title"));
        if (!TextUtils.isEmpty(comProlemContent.getQuestion())) {
            tv_title_problem.setText(comProlemContent.getQuestion());
        }

        if (!TextUtils.isEmpty(comProlemContent.getAnswer())) {
            tvContent.setText(comProlemContent.getAnswer());
        }
    }

    @Override
    protected Object getTitleText() {
        return null;
    }

}
