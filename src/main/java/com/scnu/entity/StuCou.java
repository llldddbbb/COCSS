package com.scnu.entity;

import javax.persistence.Column;
import java.util.Date;

public class StuCou {

    private Integer courseId;//所属课程

    private Integer stuId;//所属学生

    private Integer state;//状态标识:-1:无效 0:成功

    @Column(name = "createTime")
    private Date createTime;//创建时间

    private Course course;

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

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}