package com.scnu.controller;

import com.scnu.dto.CourseExecution;
import com.scnu.dto.CourseResult;
import com.scnu.dto.Exposer;
import com.scnu.dto.LoginResult;
import com.scnu.entity.Course;
import com.scnu.entity.Student;
import com.scnu.enums.CourseStateEnum;
import com.scnu.exception.CloseException;
import com.scnu.exception.RepeatException;
import com.scnu.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by ldb on 2017/6/1.
 */
@Controller
@RequestMapping("/course")//url:模块/资源/{}/细分
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * 获取课程列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        List<Course> courses = courseService.listCourse();
        model.addAttribute("list",courses);
        return "list";
    }

    /**
     * 获取单个课程
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/{id}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("id") Integer id, Model model) {
        if (id == null) {
            return "redirect:/course/list";
        }
        Course course = courseService.getCourse(id);
        if (course == null) {
            return "forward:/course/list";
        }
        model.addAttribute("course", course);
        return "detail";
    }

    /**
     * 暴露秒杀接口
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}/exposer", method = RequestMethod.GET)
    @ResponseBody
    public CourseResult<Exposer> exposer(@PathVariable Integer id) {
        CourseResult<Exposer> result;
        try {
            Exposer exposer = courseService.exportUrl(id);
            result = new CourseResult<Exposer>(true, exposer);
        } catch (Exception e) {
            e.printStackTrace();
            result = new CourseResult<Exposer>(false, e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{courseId}/{md5}/execution",method = RequestMethod.POST)
    @ResponseBody
    public CourseResult<CourseExecution> execute(@PathVariable("courseId") Integer courseId,@PathVariable("md5") String md5,
                                                   @CookieValue(value = "studentId", required = false) Integer studentId,
                                                 @CookieValue(value = "studentMD5", required = false) String studentMD5) {
        if (studentId == null) {
            return new CourseResult<CourseExecution>(false, "未注册");
        }
        CourseResult<CourseExecution> result;
        try {
            CourseExecution execution = courseService.executeCourse(courseId, studentId, md5,studentMD5);
            return new CourseResult<CourseExecution>(true, execution);
        } catch (RepeatException e1) {
            CourseExecution execution = new CourseExecution(courseId, CourseStateEnum.REPEAT_KILL);
            return new CourseResult<CourseExecution>(true, execution);
        } catch (CloseException e2) {
            CourseExecution execution = new CourseExecution(courseId, CourseStateEnum.END);
            return new CourseResult<CourseExecution>(true, execution);
        } catch (Exception e) {
            CourseExecution execution = new CourseExecution(courseId, CourseStateEnum.INNER_ERROR);
            return new CourseResult<CourseExecution>(true, execution);
        }

    }

    //获取系统时间
    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public CourseResult<Long> time() {
        Date now = new Date();
        return new CourseResult<Long>(true, now.getTime());
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public LoginResult checkLogin(Student student){
        return courseService.checkLogin(student);
    }


}
