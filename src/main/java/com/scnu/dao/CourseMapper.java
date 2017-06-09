package com.scnu.dao;

import com.scnu.entity.Course;
import com.scnu.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CourseMapper  extends MyMapper<Course> {

    /**
     * 减少课程数量
     * @param id
     * @param executeTime
     * @return 执行的记录数，>1执行成功，=0执行失败
     */
    int reduceNumber(@Param("id")Integer id,@Param("executeTime")Date executeTime);

    /**
     *  根据学生id查询获取所选课程
     * @param studentId
     * @return
     */
    List<Course> listCourseByStudentId(@Param("studentId")Integer studentId);

    /**
     * 增加课程数量
     * @param id
     * @param executeTime
     * @return 执行的记录数，>1执行成功，=0执行失败
     */
    int addNumber(@Param("id")Integer id,@Param("executeTime")Date executeTime);


}