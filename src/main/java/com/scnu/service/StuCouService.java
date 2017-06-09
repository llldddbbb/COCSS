package com.scnu.service;

import com.scnu.dto.PageBean;
import com.scnu.dto.PageResult;
import com.scnu.dto.Result;
import com.scnu.entity.Course;
import com.scnu.entity.StuCou;

import java.util.List;

/**
 * Created by ldb on 2017/6/5.
 */
public interface StuCouService {

    /**
     * 查询全部选课
     *
     * @return
     */
    PageResult<StuCou> listStuCou(PageBean pageBean);

    /**
     * 添加选课
     * @param stuCou
     * @return
     */
    Integer addStuCou(StuCou stuCou);

    /**
     * 更新选课
     * @param stuCou
     * @return
     */
    Integer updateStuCou(StuCou stuCou);

    /**
     * 删除选课
     * @param id
     * @return
     */
    Result deleteStuCou(Integer id);


}
