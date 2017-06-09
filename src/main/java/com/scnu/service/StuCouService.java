package com.scnu.service;

import com.scnu.dto.Result;
import com.scnu.dto.PageBean;
import com.scnu.dto.PageResult;
import com.scnu.entity.StuCou;

/**
 * Created by ldb on 2017/6/5.
 */
public interface StuCouService {

    /**
     * 查询全部学生
     *
     * @return
     */
    PageResult<StuCou> listStuCou(PageBean pageBean);

    /**
     * 添加学生
     * @param stuCou
     * @return
     */
    Integer addStuCou(StuCou stuCou);

    /**
     * 更新学生
     * @param stuCou
     * @return
     */
    Integer updateStuCou(StuCou stuCou);

    /**
     * 删除学生
     * @param id
     * @return
     */
    Result deleteStuCou(Integer id);
}
