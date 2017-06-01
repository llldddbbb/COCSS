package com.scnu.service;

import com.scnu.dto.CourseExecution;
import com.scnu.dto.Exposer;
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
    List<Course> listCourse();

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
     * @return
     */
    CourseExecution executeCourse(int id, int studentId, String md5) throws CloseException, RepeatException, CourseException;


}
