package com.scnu.dto;

/**
 * Created by ldb on 2017/6/1.
 */
public class CourseResult {

    //响应业务状态
    private boolean success;
    //响应中的数据
    private Object data;
    //响应信息
    private String msg;

    public CourseResult(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public CourseResult(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    public CourseResult(boolean success, Object data, String msg) {
        this.success = success;
        this.data = data;
        this.msg = msg;
    }

    public CourseResult(boolean success) {
        this.success = success;
    }

    public static CourseResult ok(){
        return new CourseResult(true);
    }

    public static CourseResult isNotOK(){
        return new CourseResult(false);
    }

    public static CourseResult ok(Object data){
        return new CourseResult(true,data);
    }

    public static CourseResult isNotOK(String msg){
        return new CourseResult(false,msg);
    }


}
