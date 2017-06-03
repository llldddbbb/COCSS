package com.scnu.service.impl;

import com.scnu.dao.AdminMapper;
import com.scnu.dto.CourseResult;
import com.scnu.entity.Admin;
import com.scnu.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ldb on 2017/6/3.
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public CourseResult checkLogin(Admin admin) {
        Admin currentAdmin = adminMapper.checkLogin(admin);
        if(currentAdmin==null){
            return CourseResult.isNotOK("用户名或密码错误");
        }
        currentAdmin.setPassword("");
        return CourseResult.ok(currentAdmin);
    }
}
