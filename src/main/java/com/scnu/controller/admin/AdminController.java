package com.scnu.controller.admin;

import com.scnu.dao.AdminMapper;
import com.scnu.dto.CourseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ldb on 2017/6/3.
 */
@Controller
public class AdminController {


    @RequestMapping("/background")
    public String goIndex(){
        return "/background/index";
    }

    @RequestMapping("/login")
    public CourseResult checkLogin(){
        CourseResult result=null;

    }

}
