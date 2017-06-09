package com.scnu.dao;

import com.scnu.entity.Practice;
import com.scnu.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface PracticeMapper extends MyMapper<Practice> {

    /**
     * 减少实习数量
     * @param id
     * @param executeTime
     * @return 执行的记录数，>1执行成功，=0执行失败
     */
    int reduceNumber(@Param("id") Integer id, @Param("executeTime") Date executeTime);



}