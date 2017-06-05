package com.scnu.controller.admin;

import com.scnu.dto.PageBean;
import com.scnu.entity.Course;
import com.scnu.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ldb on 2017/6/4.
 */
@Controller
@RequestMapping("/admin")
public class CourseAdminController {

    @Autowired
    private CourseService courseService;

    @RequestMapping("/courseManage")
    public String showCourseManage(){
        return "background/courseManage";
    }


    @RequestMapping(value = "/course/list/{page}",method = RequestMethod.GET)
    @ResponseBody
    public List<Course> getCourseList(@PathVariable Integer page,Integer pageSize){
        //获取评论列表
        PageBean pageBean=new PageBean(page, pageSize);
        HashMap<String,Object> param=new HashMap<>();
        param.put("start",pageBean.getStart());
        param.put("pageSize",pageBean.getPageSize());
        List<Course> courseList = courseService.listCourse(param);
        return courseList;
    }

}
