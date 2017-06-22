package com.scnu.controller;

import com.alibaba.druid.util.StringUtils;
import com.scnu.dto.Execution;
import com.scnu.dto.Exposer;
import com.scnu.dto.PageBean;
import com.scnu.dto.Result;
import com.scnu.entity.Course;
import com.scnu.entity.StuCou;
import com.scnu.enums.StateEnum;
import com.scnu.exception.CloseException;
import com.scnu.exception.DataException;
import com.scnu.exception.RepeatException;
import com.scnu.service.CourseService;
import com.scnu.service.StuCouService;
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

    @Autowired
    private StuCouService stuCouService;

    /**
     * 获取课程列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        List<Course> courses = courseService.listCourse(new PageBean()).getRows();
        model.addAttribute("list",courses);
        return "course/courseList";
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
        return "course/courseDetail";
    }

    /**
     * 暴露秒杀接口
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}/exposer", method = RequestMethod.GET)
    @ResponseBody
    public Result exposer(@PathVariable Integer id) {
        Result result;
        try {
            Exposer exposer = courseService.exportUrl(id);
            result = Result.ok(exposer);
        } catch (Exception e) {
            e.printStackTrace();
            result = Result.isNotOK(e.getMessage());
        }
        return result;
    }

    /**
     * 执行选课
     * @param courseId
     * @param md5
     * @param studentId
     * @param studentMD5
     * @return
     */
    @RequestMapping(value = "/{courseId}/{md5}/execution",method = RequestMethod.POST)
    @ResponseBody
    public Result execute(@PathVariable("courseId") Integer courseId, @PathVariable("md5") String md5,
                          @CookieValue(value = "studentId", required = false) Integer studentId,
                          @CookieValue(value = "studentMD5", required = false) String studentMD5) {
        if (studentId == null) {
            return Result.isNotOK("未注册");
        }
        try {
            Execution execution = courseService.executeCourse(courseId, studentId, md5,studentMD5);
            return Result.ok(execution);
        }catch (DataException e0){
            Execution execution = new Execution(courseId, StateEnum.DATA_ERROR);
            return Result.ok(execution);
        }catch (RepeatException e1) {
            Execution execution = new Execution(courseId, StateEnum.REPEAT_KILL);
            return Result.ok(execution);
        } catch (CloseException e2) {
            Execution execution = new Execution(courseId, StateEnum.END);
            return Result.ok(execution);
        } catch (Exception e) {
            Execution execution = new Execution(courseId, StateEnum.INNER_ERROR);
            return Result.ok(execution);
        }

    }

    /**
     * 获取系统时间
     * @return
     */
    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public Result time() {
        Date now = new Date();
        return Result.ok(now.getTime());
    }

    /**
     * 查看选课信息
     * @param model
     * @param studentId
     * @return
     */
    @RequestMapping("/check/{studentId}")
    public String courseCheck(Model model,@PathVariable Integer studentId){
        List<StuCou> stuCouList = stuCouService.listStuCouByStudentId(studentId);
        model.addAttribute("list",stuCouList);
        return "course/courseCheck";
    }

    @RequestMapping(value = "/{courseId}/rollback",method = RequestMethod.POST)
    @ResponseBody
    public Result rollback(@PathVariable("courseId") Integer courseId,
                          @CookieValue(value = "studentId", required = false) Integer studentId,
                          @CookieValue(value = "studentMD5", required = false) String studentMD5) {
        if (studentId == null|| StringUtils.isEmpty(studentMD5)) {
            return Result.isNotOK("未登录");
        }
        try {
            Execution execution = courseService.rollBackCourse(courseId, studentId,studentMD5);
            return Result.ok(execution);
        } catch (CloseException e2) {
            Execution execution = new Execution(courseId, StateEnum.END);
            return Result.ok(execution);
        } catch (Exception e) {
            Execution execution = new Execution(courseId, StateEnum.DATE_REWRITE);
            return Result.ok(execution);
        }

    }

}
