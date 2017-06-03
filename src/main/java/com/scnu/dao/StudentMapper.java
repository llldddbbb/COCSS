package com.scnu.dao;

import com.scnu.entity.Student;
import org.apache.ibatis.annotations.Param;

public interface StudentMapper {

    /**
     * 验证学生登录
     * @param userName
     * @param password
     * @return
     */
    Student checkLogin(@Param("userName")String userName,@Param("password")String password);

}