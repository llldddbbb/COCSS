package com.scnu.entity;

import javax.persistence.Column;

/**
 * Created by ldb on 2017/6/3.
 */
public class Admin extends BaseEntity{

    @Column(name="userName")
    private String userName;
    private String password;



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
