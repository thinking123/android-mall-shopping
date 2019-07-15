package com.lf.shoppingmall.bean.service;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/6.
 */

public class ComProlemContent implements Serializable {
    //    "id": 3,
//                "type": "1",
//                "question": "66666666666666",
//                "answer": "啊打蝴蝶卡灰色的卡仕达卡仕达的好动爱上"
    private String id;
    private int type;
    private String question;
    private String answer;

    @Override
    public String toString() {
        return "ComProlemContent{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
