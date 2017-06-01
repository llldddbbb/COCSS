package com.scnu.dao;

import com.scnu.entity.StuCou;
import org.apache.ibatis.annotations.Param;

public interface StuCouMapper {

    /**
     * 插入抢课成功的记录
     * @param courseId
     * @param stuId
     * @return 执行结果
     */
    int insertStuCou(@Param("courseId")Integer courseId,@Param("stuId")Integer stuId);

    /**
     * 根据stuId查询，封装所属Course
     * @param stuId
     * @return
     */
    StuCou getByStuIdWithCourse(@Param("courseId")Integer courseId,@Param("stuId")Integer stuId);

}