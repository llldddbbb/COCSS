package com.scnu.service;

import com.scnu.dto.CourseExecution;
import com.scnu.dto.Exposer;
import com.scnu.dto.LoginResult;
import com.scnu.entity.Course;
import com.scnu.entity.Student;
import com.scnu.exception.CloseException;
import com.scnu.exception.CourseException;
import com.scnu.exception.RepeatException;

import java.util.HashMap;
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
    List<Course> listCourse(HashMap<String,Object> param);

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
    CourseExecution executeCourse(int id, int studentId, String md5,String studentMD5) throws CloseException, RepeatException, CourseException;


    /**
     * 登录验证
     * @param student
     * @return
     */
    LoginResult checkLogin(Student student);

    /**
     * 获取总记录数
     * @return
     */
    Integer getCourseCount();


}
