package com.scnu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scnu.dao.PracticeMapper;
import com.scnu.dao.StuPraMapper;
import com.scnu.dao.StudentMapper;
import com.scnu.dto.PageBean;
import com.scnu.dto.PageResult;
import com.scnu.dto.Result;
import com.scnu.entity.Practice;
import com.scnu.entity.StuPra;
import com.scnu.entity.StuThe;
import com.scnu.entity.Student;
import com.scnu.service.StuPraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by ldb on 2017/6/5.
 */
@Service
public class StuPraServiceImpl implements StuPraService{
    
    @Autowired
    private StuPraMapper stuPraMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private PracticeMapper practiceMapper;
    
    @Override
    public PageResult<StuPra> listStuPra(PageBean pageBean) {
        PageResult<StuPra> result=new PageResult<>();
        //PageHelper封装分页逻辑
        PageHelper.startPage(pageBean.getPage(),pageBean.getPageSize());
        //获取分页后列表
        Example example=new Example(StuPra.class);
        example.setOrderByClause("stuId asc");
        List<StuPra> stuPraList = stuPraMapper.selectByExample(example);
        //封装student和course信息
        this.fullStuThe(stuPraList,studentMapper,practiceMapper);
        // 取分页信息
        PageInfo<StuPra> pageInfo = new PageInfo<>(stuPraList);
        long total = pageInfo.getTotal(); //获取总记录数
        //填充值
        result.setRows(stuPraList);
        result.setTotal(total);
        return result;
    }

    @Override
    public Integer addStuPra(StuPra stuPra) {
        return stuPraMapper.insert(stuPra);
    }

    @Override
    public Integer updateStuPra(StuPra stuPra) {
        return stuPraMapper.updateByPrimaryKey(stuPra);
    }

    @Override
    public Result deleteStuPra(Integer id) {
        int result = stuPraMapper.deleteByPrimaryKey(id);
        if(result >0){
            return Result.ok();
        }else{
            return Result.isNotOK();
        }
    }

    @Override
    public List<StuPra> listStuPraByStudentId(Integer studentId) {
        //根据学生id查询获取所选论文的Id
        StuPra example =new StuPra();
        example.setStuId(studentId);
        List<StuPra> stuPraList=stuPraMapper.select(example);
        this.fullStuThe(stuPraList,studentMapper,practiceMapper);
        return stuPraList;
    }

    private void fullStuThe(List<StuPra> stuPraList, StudentMapper studentMapper, PracticeMapper practiceMapper) {
        for (StuPra stuPra : stuPraList) {
            Student student = studentMapper.selectByPrimaryKey(stuPra.getStuId());
            Practice practice = practiceMapper.selectByPrimaryKey(stuPra.getPracticeId());
            stuPra.setPractice(practice);
            stuPra.setStudent(student);
        }
    }
}
