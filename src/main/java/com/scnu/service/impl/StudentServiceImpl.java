package com.scnu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scnu.dao.StudentMapper;
import com.scnu.dto.LoginResult;
import com.scnu.dto.PageBean;
import com.scnu.dto.PageResult;
import com.scnu.dto.Result;
import com.scnu.entity.Student;
import com.scnu.service.StudentService;
import com.scnu.utils.SecureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

/**
 * Created by ldb on 2017/6/5.
 */
@Service
public class StudentServiceImpl implements StudentService{
    
    @Autowired
    private StudentMapper studentMapper;

    //加入盐值，混淆md5
    private final String salt = "asdfgasadfsadfdstewsrf^&*23*&(hjkKH;sdajhkl&*(&kljf";
    
    @Override
    public PageResult<Student> listStudent(PageBean pageBean) {
        PageResult<Student> result=new PageResult<>();
        //PageHelper封装分页逻辑
        PageHelper.startPage(pageBean.getPage(),pageBean.getPageSize());
        //获取分页后列表
        List<Student> studentList = studentMapper.selectAll();
        // 取分页信息
        PageInfo<Student> pageInfo = new PageInfo<>(studentList);
        long total = pageInfo.getTotal(); //获取总记录数
        //填充值
        result.setRows(studentList);
        result.setTotal(total);
        return result;
    }

    @Override
    public Integer addStudent(Student student) {
        return studentMapper.insert(student);
    }

    @Override
    public Integer updateStudent(Student student) {
        return studentMapper.updateByPrimaryKey(student);
    }

    @Override
    public Result deleteStudent(Integer id) {
        int result = studentMapper.deleteByPrimaryKey(id);
        if(result >0){
            return Result.ok();
        }else{
            return Result.isNotOK();
        }
    }

    @Override
    public LoginResult checkLogin(Student student) {
        Student result = studentMapper.checkLogin(student.getUserName(), student.getPassword());
        if(result==null){
            return new LoginResult(false);
        }
        String studentMD5 = SecureUtil.getMD5(result);
        return new LoginResult(result.getId(),studentMD5,true,result.getStuName(),result.getIs_fifteen());
    }


}
