package com.scnu.controller.admin;

import com.scnu.dto.CourseResult;
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
public class AdminController {

    @Autowired
    private AdminService adminService;


    @RequestMapping("/background")
    public String goIndex(){
        return "/background/index";
    }

    @RequestMapping("/login")
    @ResponseBody
    public CourseResult checkLogin(Admin admin){
        return  adminService.checkLogin(admin);
    }

}
