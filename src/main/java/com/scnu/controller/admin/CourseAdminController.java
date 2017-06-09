package com.scnu.controller.admin;

import com.scnu.dto.Result;
import com.scnu.dto.PageBean;
import com.scnu.dto.PageResult;
import com.scnu.entity.Course;
import com.scnu.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ldb on 2017/6/4.
 */
@Controller
@RequestMapping("/admin")
public class CourseAdminController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
    }

    @Autowired
    private CourseService courseService;

    @RequestMapping("/courseManage")
    public String showCourseManage(){
        return "background/courseManage";
    }


    @RequestMapping(value = "/course/list/{page}",method = RequestMethod.GET)
    @ResponseBody
    public PageResult<Course> getCourseList(@PathVariable Integer page, Integer pageSize){
        //获取评论列表
        PageBean pageBean=new PageBean(page, pageSize);
        PageResult<Course> result = courseService.listCourse(pageBean);
        return result;
    }


    @RequestMapping(value = "/course",method = RequestMethod.POST)
    public String addCourse(Course course){
        Integer result = courseService.addCourse(course);
        if(result>0){
            return "redirect:/admin/courseManage";
        }else{
            return null;
        }
    }

    @RequestMapping(value = "/course",method = RequestMethod.PUT)
    public String updateCourse(Course course){
        Integer result = courseService.updateCourse(course);
        if(result>0){
            return "redirect:/admin/courseManage";
        }else{
            return null;
        }
    }

    @RequestMapping(value = "/course/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public Result deleteCourse(@PathVariable Integer id){
        return courseService.deleteCourse(id);
    }

}
