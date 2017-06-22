package com.scnu.service;

import com.scnu.dto.PageBean;
import com.scnu.dto.PageResult;
import com.scnu.dto.Result;
import com.scnu.entity.StuThe;

import java.util.List;

/**
 * Created by ldb on 2017/6/5.
 */
public interface StuTheService {

    /**
     * 查询全部论文
     *
     * @return
     */
    PageResult<StuThe> listStuThe(PageBean pageBean);

    /**
     * 添加论文
     * @param stuThe
     * @return
     */
    Integer addStuThe(StuThe stuThe);

    /**
     * 更新论文
     * @param stuThe
     * @return
     */
    Integer updateStuThe(StuThe stuThe);

    /**
     * 删除论文
     * @param id
     * @return
     */
    Result deleteStuThe(Integer id);

    /**
     * 获取某学生选论文列表
     * @param studentId
     * @return
     */
    List<StuThe> listStuTheByStudentId(Integer studentId);
}
