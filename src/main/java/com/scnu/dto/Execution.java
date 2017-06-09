package com.scnu.dto;

import com.scnu.enums.StateEnum;

/**
 * Created by ldb on 2017/5/30.
 * 封装执行结果
 */
public class Execution {

    private int id;

    //抢课执行结果的状态
    private int state;

    //状态的明文标识
    private String stateInfo;


    public Execution(int id, StateEnum stateEnum) {
        this.id = id;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getInfo();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }


}
