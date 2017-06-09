package com.scnu.dao;

import com.scnu.entity.StuPra;
import com.scnu.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import javax.persistence.Table;

@Table(name = "stu_pra")
public interface StuPraMapper extends MyMapper<StuPra> {

    /**
     * 插入抢课成功的记录
     * @param practiceId
     * @param stuId
     * @return 执行结果
     */
    int insertStuPra(@Param("practiceId") Integer practiceId, @Param("stuId") Integer stuId);

    /**
     * 根据stuId查询，封装所属Practice
     * @param stuId
     * @return
     */
    StuPra getByStuIdWithPractice(@Param("practiceId") Integer practiceId, @Param("stuId") Integer stuId);

}