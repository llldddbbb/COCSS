package com.scnu.dao;

import com.scnu.entity.Thesis;
import com.scnu.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ThesisMapper extends MyMapper<Thesis> {

    /**
     * 减少论文数量
     * @param id
     * @param executeTime
     * @return 执行的记录数，>1执行成功，=0执行失败
     */
    int reduceNumber(@Param("id") Integer id, @Param("executeTime") Date executeTime);

    /**
     *  根据学生id查询获取所选论文
     * @param studentId
     * @return
     */
    List<Thesis> listThesisByStudentId(@Param("studentId") Integer studentId);

    /**
     * 增加论文数量
     * @param id
     * @param executeTime
     * @return 执行的记录数，>1执行成功，=0执行失败
     */
    int addNumber(@Param("id") Integer id, @Param("executeTime") Date executeTime);


}