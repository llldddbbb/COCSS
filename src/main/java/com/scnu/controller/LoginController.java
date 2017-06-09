package com.scnu.controller;

import com.scnu.dto.Result;
import com.scnu.entity.Admin;
import com.scnu.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ldb on 2017/6/3.
 */
@Controller
public class LoginController {

    @Autowired
    private AdminService adminService;


    /**
     * 跳转到后台页面
     * @return
     */
    @RequestMapping("/background")
    public String goIndex(){
        return "/background/index";
    }

    /**
     * 登录验证
     * @param admin
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public Result checkLogin(Admin admin){
        return  adminService.checkLogin(admin);
    }


}
