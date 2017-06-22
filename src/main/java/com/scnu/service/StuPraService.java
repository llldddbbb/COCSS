package com.scnu.service;

import com.scnu.dto.PageBean;
import com.scnu.dto.PageResult;
import com.scnu.dto.Result;
import com.scnu.entity.StuPra;

import java.util.List;

/**
 * Created by ldb on 2017/6/5.
 */
public interface StuPraService {

    /**
     * 查询全部选课
     *
     * @return
     */
    PageResult<StuPra> listStuPra(PageBean pageBean);

    /**
     * 添加选课
     * @param stuPra
     * @return
     */
    Integer addStuPra(StuPra stuPra);

    /**
     * 更新选课
     * @param stuPra
     * @return
     */
    Integer updateStuPra(StuPra stuPra);

    /**
     * 删除选课
     * @param id
     * @return
     */
    Result deleteStuPra(Integer id);

    /**
     * 获取某学生选实习列表
     * @param studentId
     * @return
     */
    List<StuPra> listStuPraByStudentId(Integer studentId);
}
