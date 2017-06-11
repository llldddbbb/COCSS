package com.scnu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scnu.dao.ThesisMapper;
import com.scnu.dao.StuTheMapper;
import com.scnu.dao.StudentMapper;
import com.scnu.dto.PageBean;
import com.scnu.dto.PageResult;
import com.scnu.dto.Result;
import com.scnu.entity.Thesis;
import com.scnu.entity.StuThe;
import com.scnu.entity.Student;
import com.scnu.service.StuTheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ldb on 2017/6/5.
 */
@Service
public class StuTheServiceImpl implements StuTheService{
    
    @Autowired
    private StuTheMapper stuTheMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private ThesisMapper courseMapper;
    
    @Override
    public PageResult<StuThe> listStuThe(PageBean pageBean) {
        PageResult<StuThe> result=new PageResult<>();
        //PageHelper封装分页逻辑
        PageHelper.startPage(pageBean.getPage(),pageBean.getPageSize());
        //获取分页后列表
        List<StuThe> stuTheList = stuTheMapper.selectAll();
        //封装student和course信息
        for (StuThe stuThe : stuTheList) {
            Student student = studentMapper.selectByPrimaryKey(stuThe.getStuId());
            Thesis course = courseMapper.selectByPrimaryKey(stuThe.getThesisId());
            stuThe.setThesis(course);
            stuThe.setStudent(student);
        }
        // 取分页信息
        PageInfo<StuThe> pageInfo = new PageInfo<>(stuTheList);
        long total = pageInfo.getTotal(); //获取总记录数
        //填充值
        result.setRows(stuTheList);
        result.setTotal(total);
        return result;
    }

    @Override
    public Integer addStuThe(StuThe stuThe) {
        return stuTheMapper.insert(stuThe);
    }

    @Override
    public Integer updateStuThe(StuThe stuThe) {
        return stuTheMapper.updateByPrimaryKey(stuThe);
    }

    @Override
    public Result deleteStuThe(Integer id) {
        int result = stuTheMapper.deleteByPrimaryKey(id);
        if(result >0){
            return Result.ok();
        }else{
            return Result.isNotOK();
        }
    }
}
