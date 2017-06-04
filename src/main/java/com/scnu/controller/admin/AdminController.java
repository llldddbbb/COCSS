package com.scnu.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ldb on 2017/6/4.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    /**
     * 登录成功，跳转到main页面
     * @return
     */
    @RequestMapping("/main")
    public String goMain(){
        return "/background/main";
    }

}
