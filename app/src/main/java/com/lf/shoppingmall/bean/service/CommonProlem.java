package com.lf.shoppingmall.bean.service;

import com.lf.shoppingmall.bean.Body;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/6.
 */

public class CommonProlem implements Serializable {

    private String id;
    private String name;
    private List<ComProlemContent> questions;

    @Override
    public String toString() {
        return "CommonProlem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", questions=" + questions +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ComProlemContent> getQuestions() {
        return questions;
    }

    public void setQuestions(List<ComProlemContent> questions) {
        this.questions = questions;
    }
    //    {
//        "id": "1",
//            "name": "常见问题",
//            "questions": [
//        {
//            "id": 3,
//                "type": "1",
//                "question": "66666666666666",
//                "answer": "啊打蝴蝶卡灰色的卡仕达卡仕达的好动爱上"
//        },
//        {
//            "id": 4,
//                "type": "1",
//                "question": "浪费回复网红哇啊哦和我",
//                "answer": "哇大大无回答后发货方法黑色哦哈佛色黄肤色of和欧式粉色哈佛色佛色哈佛色"
//        }
//        ]
//    },

}
