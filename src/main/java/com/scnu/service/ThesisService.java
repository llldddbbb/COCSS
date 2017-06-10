package com.scnu.service;

import com.scnu.dto.*;
import com.scnu.entity.Thesis;
import com.scnu.exception.CloseException;
import com.scnu.exception.CourseException;
import com.scnu.exception.RepeatException;

import java.util.List;

/**
 * Created by ldb on 2017/5/30.
 */
public interface ThesisService {

    /**
     * 查询全部论文
     *
     * @return
     */
    PageResult<Thesis> listThesis(PageBean pageBean);

    /**
     * 查询单个论文
     *
     * @param id
     * @return
     */
    Thesis getThesis(int id);

    /**
     * 论文开启时输出抢课地址，否则输出系统时间
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
    Execution executeThesis(int id, int studentId, String md5, String studentMD5) throws CloseException, RepeatException, CourseException;


    /**
     * 添加论文
     * @param thesis
     * @return
     */
    Result addThesis(Thesis thesis);

    /**
     * 更新论文
     * @param thesis
     * @return
     */
    Result updateThesis(Thesis thesis)throws CourseException;

    /**
     * 删除论文
     * @param id
     * @return
     */
    Result deleteThesis(Integer id);

    /**
     * 获取某学生选论文列表
     * @param studentId
     * @return
     */
    List<Thesis> listThesisByStudentId(Integer studentId);

    /**
     * 回滚执行
     * @param id
     * @param studentId
     * @param studentMD5
     * @return
     * @throws CloseException
     * @throws CourseException
     */
    Execution rollBackThesis(int id, int studentId, String studentMD5)throws CloseException,  CourseException;


}
