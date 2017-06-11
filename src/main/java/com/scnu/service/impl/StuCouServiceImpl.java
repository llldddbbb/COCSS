package com.scnu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scnu.dao.CourseMapper;
import com.scnu.dao.StuCouMapper;
import com.scnu.dao.StudentMapper;
import com.scnu.dto.PageBean;
import com.scnu.dto.PageResult;
import com.scnu.dto.Result;
import com.scnu.entity.Course;
import com.scnu.entity.StuCou;
import com.scnu.entity.Student;
import com.scnu.service.StuCouService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldb on 2017/6/5.
 */
@Service
public class StuCouServiceImpl implements StuCouService{
    
    @Autowired
    private StuCouMapper stuCouMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private CourseMapper courseMapper;
    
    @Override
    public PageResult<StuCou> listStuCou(PageBean pageBean) {
        PageResult<StuCou> result=new PageResult<>();
        //PageHelper封装分页逻辑
        PageHelper.startPage(pageBean.getPage(),pageBean.getPageSize());
        //获取分页后列表
        List<StuCou> stuCouList = stuCouMapper.selectAll();
        //封装student和course信息
        for (StuCou stuCou : stuCouList) {
            Student student = studentMapper.selectByPrimaryKey(stuCou.getStuId());
            Course course = courseMapper.selectByPrimaryKey(stuCou.getCourseId());
            stuCou.setCourse(course);
            stuCou.setStudent(student);
        }
        // 取分页信息
        PageInfo<StuCou> pageInfo = new PageInfo<>(stuCouList);
        long total = pageInfo.getTotal(); //获取总记录数
        //填充值
        result.setRows(stuCouList);
        result.setTotal(total);
        return result;
    }

    @Override
    public Integer addStuCou(StuCou stuCou) {
        return stuCouMapper.insert(stuCou);
    }

    @Override
    public Integer updateStuCou(StuCou stuCou) {
        return stuCouMapper.updateByPrimaryKey(stuCou);
    }

    @Override
    public Result deleteStuCou(Integer id) {
        int result = stuCouMapper.deleteByPrimaryKey(id);
        if(result >0){
            return Result.ok();
        }else{
            return Result.isNotOK();
        }
    }


}
