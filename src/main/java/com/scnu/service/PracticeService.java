package com.scnu.service;

import com.scnu.dto.*;
import com.scnu.entity.Practice;
import com.scnu.exception.CloseException;
import com.scnu.exception.CourseException;
import com.scnu.exception.RepeatException;

/**
 * Created by ldb on 2017/5/30.
 */
public interface PracticeService {

    /**
     * 查询全部实习
     *
     * @return
     */
    PageResult<Practice> listPractice(PageBean pageBean);

    /**
     * 查询单个实习
     *
     * @param id
     * @return
     */
    Practice getPractice(int id);

    /**
     * 实习开启时输出抢课地址，否则输出系统时间
     *
     * @param id
     * @return
     */
    Exposer exportUrl(int id);

    /**
     * 执行抢课操作
     *
     * @param id
     * @param studentId
     * @param md5
     * @param studentMD5
     * @return
     */
    Execution executePractice(int id, int studentId, String md5, String studentMD5) throws CloseException, RepeatException, CourseException;


    /**
     * 添加实习
     * @param practice
     * @return
     */
    Integer addPractice(Practice practice);

    /**
     * 更新实习
     * @param practice
     * @return
     */
    Integer updatePractice(Practice practice);

    /**
     * 删除实习
     * @param id
     * @return
     */
    Result deletePractice(Integer id);


}