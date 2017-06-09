package com.scnu.dto;

import com.scnu.entity.StuCou;
import com.scnu.enums.CourseStateEnum;

/**
 * Created by ldb on 2017/5/30.
 * 封装抢课结果
 */
public class Execution {

    private int id;

    //抢课执行结果的状态
    private int state;

    //状态的明文标识
    private String stateInfo;

    //当抢课成功时，需要传递抢课成功的课程回去
    private StuCou stuCou;

    //抢课成功返回所有信息
    public Execution(int id, CourseStateEnum courseStateEnum, StuCou stuCou) {
        this.id = id;
        this.state = courseStateEnum.getState();
        this.stateInfo = courseStateEnum.getInfo();
        this.stuCou = stuCou;
    }

    //抢课失败返回信息
    public Execution(int id, CourseStateEnum courseStateEnum) {
        this.id = id;
        this.state = courseStateEnum.getState();
        this.stateInfo = courseStateEnum.getInfo();
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

    public StuCou getStuCou() {
        return stuCou;
    }

    public void setStuCou(StuCou stuCou) {
        this.stuCou = stuCou;
    }
}
