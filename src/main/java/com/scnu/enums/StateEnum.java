package com.scnu.enums;

/**
 * Created by ldb on 2017/5/30.
 */
public enum StateEnum {

    SUCCESS(1,"选课成功"),
    END(0,"选课结束"),
    REPEAT_KILL(-1,"已选"),
    INNER_ERROR(-2,"系统异常"),
    DATE_REWRITE(-3,"数据篡改");

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
