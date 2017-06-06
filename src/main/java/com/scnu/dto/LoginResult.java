package com.scnu.dto;

/**
 * Created by ldb on 2017/6/2.
 * 封装登录信息
 */
public class LoginResult {

    //登录id
    private int studentId;

    //加密MD5
    private String studentMD5;

    //是否登录成功
    private boolean is_Login;

    //回调用户名信息
    private String stuName;

    //登录失败
    public LoginResult(boolean is_Login) {
        this.is_Login = is_Login;
    }

    //登录成功
    public LoginResult(int studentId, String studentMD5, boolean is_Login,String stuName) {
        this.studentId = studentId;
        this.studentMD5 = studentMD5;
        this.is_Login = is_Login;
        this.stuName=stuName;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentMD5() {
        return studentMD5;
    }

    public void setStudentMD5(String studentMD5) {
        this.studentMD5 = studentMD5;
    }

    public boolean isIs_Login() {
        return is_Login;
    }

    public void setIs_Login(boolean is_Login) {
        this.is_Login = is_Login;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }
}
