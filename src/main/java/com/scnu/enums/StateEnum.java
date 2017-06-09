package com.scnu.enums;

/**
 * Created by ldb on 2017/5/30.
 */
public enum StateEnum {

    SUCCESS(1,"执行成功"),
    END(0,"选课结束"),
    REPEAT_KILL(-1,"重复选择"),
    INNER_ERROR(-2,"系统异常"),
    DATE_REWRITE(-3,"数据错误");

    private int state;
    private String info;

    StateEnum(int state, String info) {
        this.state = state;
        this.info = info;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
