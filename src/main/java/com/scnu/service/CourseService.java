package com.scnu.service;

import com.scnu.dto.Exposer;
import com.scnu.entity.Course;

import java.util.List;

/**
 * Created by ldb on 2017/5/30.
 */
public interface CourseService {

    /**
     * 查询全部选课
     * @return
     */
    List<Course> listCourse();

    /**
     * 查询单个选课
     * @param id
     * @return
     */
    Course getCourse(int id);

    /**
     * 选课开启时输出抢课地址，否则输出系统时间
     * @param id
     * @return
     */
    Exposer exportUrl(int id);






}
