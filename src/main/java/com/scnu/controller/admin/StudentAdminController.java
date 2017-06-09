package com.scnu.controller.admin;

import com.scnu.dto.Result;
import com.scnu.dto.PageBean;
import com.scnu.dto.PageResult;
import com.scnu.entity.Student;
import com.scnu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ldb on 2017/6/4.
 */
@Controller
@RequestMapping("/admin")
public class StudentAdminController {
    
    @Autowired
    private StudentService studentService;

    @RequestMapping("/studentManage")
    public String showStudentManage(){
        return "background/studentManage";
    }


    @RequestMapping(value = "/student/list/{page}",method = RequestMethod.GET)
    @ResponseBody
    public PageResult<Student> getStudentList(@PathVariable Integer page, Integer pageSize){
        //获取评论列表
        PageBean pageBean=new PageBean(page, pageSize);
        PageResult<Student> result = studentService.listStudent(pageBean);
        return result;
    }


    @RequestMapping(value = "/student",method = RequestMethod.POST)
    public String addStudent(Student student){
        Integer result = studentService.addStudent(student);
        if(result>0){
            return "redirect:/admin/studentManage";
        }else{
            return null;
        }
    }

    @RequestMapping(value = "/student",method = RequestMethod.PUT)
    public String updateStudent(Student student){
        Integer result = studentService.updateStudent(student);
        if(result>0){
            return "redirect:/admin/studentManage";
        }else{
            return null;
        }
    }

    @RequestMapping(value = "/student/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public Result deleteStudent(@PathVariable Integer id){
        return studentService.deleteStudent(id);
    }

}
