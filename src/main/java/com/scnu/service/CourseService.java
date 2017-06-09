package com.scnu.service;

import com.scnu.dto.*;
import com.scnu.entity.Course;
import com.scnu.exception.CloseException;
import com.scnu.exception.CourseException;
import com.scnu.exception.RepeatException;

import java.util.List;

/**
 * Created by ldb on 2017/5/30.
 */
public interface CourseService {

    /**
     * 查询全部选课
     *
     * @return
     */
    PageResult<Course> listCourse(PageBean pageBean);

    /**
     * 查询单个选课
     *
     * @param id
     * @return
     */
    Course getCourse(int id);

    /**
     * 选课开启时输出抢课地址，否则输出系统时间
     *
     * @param id
     * @return
     */
    Exposer exportUrl(int id);

    /**
     * 执行抢课操作
     *
     * @param id
     * @param studentId
     * @param md5
     * @param studentMD5
     * @return
     */
    Execution executeCourse(int id, int studentId, String md5, String studentMD5) throws CloseException, RepeatException, CourseException;


    /**
     * 添加选课
     * @param course
     * @return
     */
    Integer addCourse(Course course);

    /**
     * 更新选课
     * @param course
     * @return
     */
    Integer updateCourse(Course course);

    /**
     * 删除选课
     * @param id
     * @return
     */
    Result deleteCourse(Integer id);

    /**
     * 获取某学生选课列表
     * @param studentId
     * @return
     */
    List<Course> listCourseByStudentId(Integer studentId);


}
