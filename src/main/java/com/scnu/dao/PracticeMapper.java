package com.scnu.dao;

import com.scnu.entity.Practice;
import com.scnu.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface PracticeMapper extends MyMapper<Practice> {

    /**
     * 减少实习数量
     * @param id
     * @param executeTime
     * @return 执行的记录数，>1执行成功，=0执行失败
     */
    int reduceNumber(@Param("id") Integer id, @Param("executeTime") Date executeTime);

    /**
     *  根据学生id查询获取所选实习
     * @param studentId
     * @return
     */
    List<Practice> listPracticeByStudentId(@Param("studentId")Integer studentId);

    /**
     * 增加课程数量
     * @param id
     * @param executeTime
     * @return 执行的记录数，>1执行成功，=0执行失败
     */
    int addNumber(@Param("id")Integer id,@Param("executeTime")Date executeTime);



}