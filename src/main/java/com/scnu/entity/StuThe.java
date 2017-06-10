package com.scnu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Table(name = "stu_the")
public class StuThe {

    @Column(name = "thesisId")
    private Integer thesisId;//所属课程

    @Column(name = "stuId")
    private Integer stuId;//所属学生

    private Integer state;//状态标识:-1:无效 0:成功

    @Column(name = "createTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;//创建时间

    @Transient
    private Thesis thesis;

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

    public Integer getThesisId() {
        return thesisId;
    }

    public void setThesisId(Integer thesisId) {
        this.thesisId = thesisId;
    }

    public Thesis getThesis() {
        return thesis;
    }

    public void setThesis(Thesis thesis) {
        this.thesis = thesis;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public StuThe() {
    }

    public StuThe(Integer stuId) {
        this.stuId = stuId;
    }

    public StuThe(Integer thesisId, Integer stuId) {
        this.thesisId = thesisId;
        this.stuId = stuId;
    }
}