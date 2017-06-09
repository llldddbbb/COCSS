package com.scnu.dto;

/**
 * Created by ldb on 2017/6/1.
 */
public class Result {

    //响应业务状态
    private boolean success;
    //响应中的数据
    private Object data;
    //响应信息
    private String msg;

    public Result(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public Result(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    public Result(boolean success, Object data, String msg) {
        this.success = success;
        this.data = data;
        this.msg = msg;
    }

    public Result(boolean success) {
        this.success = success;
    }

    public static Result ok(){
        return new Result(true);
    }

    public static Result isNotOK(){
        return new Result(false);
    }

    public static Result ok(Object data){
        return new Result(true,data);
    }

    public static Result isNotOK(String msg){
        return new Result(false,msg);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
