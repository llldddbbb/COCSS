package com.scnu.service;

import com.scnu.dto.LoginResult;
import com.scnu.dto.PageBean;
import com.scnu.dto.PageResult;
import com.scnu.dto.Result;
import com.scnu.entity.Student;

/**
 * Created by ldb on 2017/6/5.
 */
public interface StudentService {

    /**
     * 查询全部学生
     *
     * @return
     */
    PageResult<Student> listStudent(PageBean pageBean);

    /**
     * 添加学生
     * @param student
     * @return
     */
    Integer addStudent(Student student);

    /**
     * 更新学生
     * @param student
     * @return
     */
    Integer updateStudent(Student student);

    /**
     * 删除学生
     * @param id
     * @return
     */
    Result deleteStudent(Integer id);

    /**
     * 登录验证
     * @param student
     * @return
     */
    LoginResult checkLogin(Student student);
}
