package com.scnu.entity;

import javax.persistence.Column;

public class Student extends BaseEntity{

    @Column(name = "userName")
    private String userName;//用户名，学号

    private String password;//密码

    @Column(name = "stuName")
    private String stuName;//姓名

    @Column(name = "gradeName")
    private String gradeName;//年级

    @Column(name = "className")
    private String className;//班级

    @Column(name = "majorName")
    private String majorName;//专业

    @Column(name = "is_fifteen")
    private Integer is_fifteen;//是否15周实习


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public Integer getIs_fifteen() {
        return is_fifteen;
    }

    public void setIs_fifteen(Integer is_fifteen) {
        this.is_fifteen = is_fifteen;
    }
}