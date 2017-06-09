package com.scnu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Table(name = "stu_pra")
public class StuPra {

    @Column(name = "practiceId")
    private Integer practiceId;//所属课程

    @Column(name = "stuId")
    private Integer stuId;//所属学生

    private Integer state;//状态标识:-1:无效 0:成功

    @Column(name = "createTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;//创建时间

    @Transient
    private Practice practice;

    @Transient
    private Student student;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public Integer getPracticeId() {
        return practiceId;
    }

    public void setPracticeId(Integer practiceId) {
        this.practiceId = practiceId;
    }

    public Practice getPractice() {
        return practice;
    }

    public void setPractice(Practice practice) {
        this.practice = practice;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public StuPra(Integer stuId) {
        this.stuId = stuId;
    }

    public StuPra(Integer practiceId, Integer stuId) {
        this.practiceId = practiceId;
        this.stuId = stuId;
    }

    public StuPra() {
    }
}