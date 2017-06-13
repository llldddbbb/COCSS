package com.scnu.controller;

import com.alibaba.druid.util.StringUtils;
import com.scnu.dto.Execution;
import com.scnu.dto.Exposer;
import com.scnu.dto.PageBean;
import com.scnu.dto.Result;
import com.scnu.entity.Practice;
import com.scnu.enums.StateEnum;
import com.scnu.exception.CloseException;
import com.scnu.exception.DataException;
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
        List<Practice> practices = practiceService.listPractice(new PageBean()).getRows();
        model.addAttribute("list",practices);
        return "practice/practiceList";
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
        return "practice/practiceDetail";
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
        try {
            Execution execution = practiceService.executePractice(practiceId, studentId, md5,studentMD5);
            return Result.ok(execution);
        }catch (DataException e0){
            Execution execution = new Execution(practiceId, StateEnum.DATA_ERROR);
            return Result.ok(execution);
        }catch (RepeatException e1) {
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

    @RequestMapping("/check/{studentId}")
    public String courseCheck(Model model,@PathVariable Integer studentId){
        List<Practice> practiceList = practiceService.listPracticeByStudentId(studentId);
        model.addAttribute("list",practiceList);
        return "practice/practiceCheck";
    }

    @RequestMapping(value = "/{practiceId}/rollback",method = RequestMethod.POST)
    @ResponseBody
    public Result rollback(@PathVariable("practiceId") Integer practiceId,
                           @CookieValue(value = "studentId", required = false) Integer studentId,
                           @CookieValue(value = "studentMD5", required = false) String studentMD5) {
        if (studentId == null|| StringUtils.isEmpty(studentMD5)) {
            return Result.isNotOK("未登录");
        }
        try {
            Execution execution = practiceService.rollBackPractice(practiceId, studentId,studentMD5);
            return Result.ok(execution);
        } catch (CloseException e2) {
            Execution execution = new Execution(practiceId, StateEnum.END);
            return Result.ok(execution);
        } catch (Exception e) {
            Execution execution = new Execution(practiceId, StateEnum.DATE_REWRITE);
            return Result.ok(execution);
        }

    }




}
