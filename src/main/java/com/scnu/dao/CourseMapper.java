package com.scnu.dao;

import com.scnu.entity.Course;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface CourseMapper {

    /**
     * 减少课程数量
     * @param id
     * @param executeTime
     * @return 执行的记录数，>1执行成功，=0执行失败
     */
    int reduceNumber(@Param("id")Integer id,@Param("executeTime")Date executeTime);

    /**
     * 获取课程列表
     * @return 课程列表
     */
    List<Course> listCourse(HashMap<String,Object> param);

    /**
     * 根据id获取课程
     * @param id
     * @return 课程
     */
    Course getCourseById(@Param("id")Integer id);

    /**
     * 获取课程总数
     * @return
     */
    Integer getCourseCount();

}