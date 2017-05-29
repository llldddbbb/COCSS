package com.scnu.dao;

import com.scnu.entity.Student;
import org.apache.ibatis.annotations.Param;

public interface StudentMapper {

    Student checkLogin(@Param("userName")String userName,@Param("password")String password);

}