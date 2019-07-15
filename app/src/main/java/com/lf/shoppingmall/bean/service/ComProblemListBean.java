package com.lf.shoppingmall.bean.service;

import com.lf.shoppingmall.bean.Body;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/6.
 */

public class ComProblemListBean extends Body implements Serializable {

    private List<CommonProlem> questionList;

    @Override
    public String toString() {
        return "ComProblemListBean{" +
                "questionList=" + questionList +
                '}';
    }

    public List<CommonProlem> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<CommonProlem> questionList) {
        this.questionList = questionList;
    }
}
