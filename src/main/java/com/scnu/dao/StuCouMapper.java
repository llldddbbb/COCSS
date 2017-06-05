package com.scnu.dao;

import com.scnu.entity.StuCou;
import com.scnu.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import javax.persistence.Table;

@Table(name = "stu_cou")
public interface StuCouMapper extends MyMapper<StuCou> {

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