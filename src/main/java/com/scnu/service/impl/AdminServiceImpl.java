package com.scnu.service.impl;

import com.scnu.dao.AdminMapper;
import com.scnu.dto.Result;
import com.scnu.entity.Admin;
import com.scnu.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ldb on 2017/6/3.
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Result checkLogin(Admin admin) {
        Admin currentAdmin = adminMapper.checkLogin(admin);
        if(currentAdmin==null){
            return Result.isNotOK("用户名或密码错误");
        }
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(currentAdmin.getUserName(), currentAdmin.getPassword());
        subject.login(token);//登录验证
        currentAdmin.setPassword("");
        return Result.ok(currentAdmin);
    }

    @Override
    public Admin getAdminByUserName(String userName) {
        Admin admin=new Admin();
        admin.setUserName(userName);
        List<Admin> list = adminMapper.select(admin);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }
}
