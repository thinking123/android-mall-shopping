package com.lf.shoppingmall.activity.custom_service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.adapter.ComProblemAdapter;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.bean.UserVo;
import com.lf.shoppingmall.bean.service.ComProblemListBean;
import com.lf.shoppingmall.bean.service.ComProlemContent;
import com.lf.shoppingmall.bean.service.CommonProlem;
import com.lf.shoppingmall.common.Constans;
import com.lf.shoppingmall.http_utils.DES;
import com.lf.shoppingmall.http_utils.MyStringCallback;
import com.lf.shoppingmall.utils.LogUtils;
import com.lr.baseview.utils.ImmersionStatus;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 常见问题
 * Created by Administrator on 2017/9/6.
 */

public class CommonProlemActivity extends BaseActivity {
    @Bind(R.id.lv_problems)
    ListView lvProblems;
    private ComProblemAdapter problemAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common_problem;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);
        problemAdapter = new ComProblemAdapter(context, null);
        lvProblems.setAdapter(problemAdapter);
        lvProblems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = problemAdapter.getItem(position);
                if (obj instanceof ComProlemContent) {
                    String title = "常见问题";
                    for (int i = position; i >= 0; i--) {
                        Object items = problemAdapter.getItem(i);
                        if (items instanceof CommonProlem) {
                            title = ((CommonProlem) items).getName();
                            break;
                        }

                    }
                    startActivity(new Intent(CommonProlemActivity.this, ProblemDetailActivity.class)
                            .putExtra("title", title)
                            .putExtra("ComProlemContent", (ComProlemContent) obj));
                }
            }
        });
        questionList();
    }

    @Override
    protected Object getTitleText() {
        return "常见问题";
    }

    /**
     * 常见问题
     */
    private void questionList() {
        String params = null;
        try {
            Map<String, String> map = new HashMap<>();
            UserVo userVo = ((BaseApplication) getApplication()).getUserVo();
            map.put("token", userVo.getToken());
            String des = new Gson().toJson(map);
            LogUtils.e("常见问题", "des-->" + des);
            params = DES.encryptDES(des);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showLoading("");
        OkHttpUtils.get().url(Constans.questionList)
                .addParams("params", params)
                .build().execute(new MyStringCallback(this) {
            @Override
            public void onSuccess(String isSucceed, String info, String body) {
                dissmissLoading();
                LogUtils.e("常见问题", "body---->" + body);
                ComProblemListBean listBean = new Gson().fromJson(body, ComProblemListBean.class);
                if (listBean != null) {
                    List<CommonProlem> prolems = listBean.getQuestionList();
                    if (prolems != null && !prolems.isEmpty()) {
                        List list = new ArrayList();
                        for (CommonProlem commonProlem : prolems) {
                            CommonProlem items = new CommonProlem();
                            items.setId(commonProlem.getId());
                            items.setName(commonProlem.getName());
                            list.add(items);
                            if (commonProlem.getQuestions() != null && !commonProlem.getQuestions().isEmpty()) {
                                list.addAll(commonProlem.getQuestions());
                            }
                        }
                        problemAdapter.setList(list, 0);
                    } else {
                        showToast("请点击重新获取");
                    }
                }

            }

            @Override
            public void onResponseError(String errorMsg, String isSucceed) {
                dissmissLoading();
                showToast(errorMsg);
            }
        });
    }
}
