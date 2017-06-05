package com.scnu.dao;

import com.scnu.entity.Student;
import com.scnu.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

public interface StudentMapper extends MyMapper<Student> {

    /**
     * 验证学生登录
     * @param userName
     * @param password
     * @return
     */
    Student checkLogin(@Param("userName")String userName,@Param("password")String password);

}