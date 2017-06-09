package com.scnu.controller;

import com.scnu.dto.*;
import com.scnu.entity.Practice;
import com.scnu.entity.Student;
import com.scnu.enums.StateEnum;
import com.scnu.exception.CloseException;
import com.scnu.exception.RepeatException;
import com.scnu.service.PracticeService;
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
@RequestMapping("/practice")//url:模块/资源/{}/细分
public class PracticeController {

    @Autowired
    private PracticeService practiceService;

    /**
     * 获取实习列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        PageBean pageBean=new PageBean(1,10);
        List<Practice> practices = practiceService.listPractice(pageBean).getRows();
        model.addAttribute("list",practices);
        return "practiceList";
    }

    /**
     * 获取单个实习
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/{id}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("id") Integer id, Model model) {
        if (id == null) {
            return "redirect:/practice/list";
        }
        Practice practice = practiceService.getPractice(id);
        if (practice == null) {
            return "forward:/practice/list";
        }
        model.addAttribute("practice", practice);
        return "practiceDetail";
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
            Exposer exposer = practiceService.exportUrl(id);
            result = Result.ok(exposer);
        } catch (Exception e) {
            e.printStackTrace();
            result = Result.isNotOK(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{practiceId}/{md5}/execution",method = RequestMethod.POST)
    @ResponseBody
    public Result execute(@PathVariable("practiceId") Integer practiceId, @PathVariable("md5") String md5,
                          @CookieValue(value = "studentId", required = false) Integer studentId,
                          @CookieValue(value = "studentMD5", required = false) String studentMD5) {
        if (studentId == null) {
            return Result.isNotOK("未注册");
        }
        Result result;
        try {
            Execution execution = practiceService.executePractice(practiceId, studentId, md5,studentMD5);
            return Result.ok(execution);
        } catch (RepeatException e1) {
            Execution execution = new Execution(practiceId, StateEnum.REPEAT_KILL);
            return Result.ok(execution);
        } catch (CloseException e2) {
            Execution execution = new Execution(practiceId, StateEnum.END);
            return Result.ok(execution);
        } catch (Exception e) {
            Execution execution = new Execution(practiceId, StateEnum.INNER_ERROR);
            return Result.ok(execution);
        }

    }

    //获取系统时间
    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public Result time() {
        Date now = new Date();
        return Result.ok(now.getTime());
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public LoginResult checkLogin(Student student){
        return practiceService.checkLogin(student);
    }


}
