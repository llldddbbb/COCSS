package com.scnu.dao;

import com.scnu.entity.StuThe;
import com.scnu.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import javax.persistence.Table;

@Table(name = "stu_the")
public interface StuTheMapper extends MyMapper<StuThe> {

    /**
     * 插入抢论文成功的记录
     * @param thesisId
     * @param stuId
     * @return 执行结果
     */
    int insertStuThe(@Param("thesisId") Integer thesisId, @Param("stuId") Integer stuId);

    /**
     * 根据stuId查询，封装所属Thesis
     * @param stuId
     * @return
     */
    StuThe getByStuIdWithThesis(@Param("thesisId") Integer thesisId, @Param("stuId") Integer stuId);

}