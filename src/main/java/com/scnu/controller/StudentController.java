package com.scnu.controller;

import com.scnu.dto.LoginResult;
import com.scnu.entity.Student;
import com.scnu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ldb on 2017/6/9.
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public LoginResult checkLogin(Student student){
        return studentService.checkLogin(student);
    }
}
