package com.scnu.controller.admin;

import com.scnu.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Created by ldb on 2017/6/4.
 */
@Controller
@RequestMapping("/admin")
@SessionAttributes(value={"courseCount"})
public class AdminController {

    @Autowired
    private CourseService courseService;

    /**
     * 登录成功，跳转到main页面
     * @return
     */
    @RequestMapping("/main")
    public String goMain(Model model){
        Integer courseCount = courseService.getCourseCount();
        model.addAttribute("courseCount",courseCount);
        return "/background/main";
    }



}
