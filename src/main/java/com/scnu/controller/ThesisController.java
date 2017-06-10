package com.scnu.controller;

import com.alibaba.druid.util.StringUtils;
import com.scnu.dto.Execution;
import com.scnu.dto.Exposer;
import com.scnu.dto.PageBean;
import com.scnu.dto.Result;
import com.scnu.entity.Thesis;
import com.scnu.enums.StateEnum;
import com.scnu.exception.CloseException;
import com.scnu.exception.RepeatException;
import com.scnu.service.ThesisService;
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
@RequestMapping("/thesis")//url:模块/资源/{}/细分
public class ThesisController {

    @Autowired
    private ThesisService thesisService;

    /**
     * 获取论文列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        PageBean pageBean=new PageBean(1,10);
        List<Thesis> thesiss = thesisService.listThesis(pageBean).getRows();
        model.addAttribute("list",thesiss);
        return "thesis/thesisList";
    }

    /**
     * 获取单个论文
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/{id}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("id") Integer id, Model model) {
        if (id == null) {
            return "redirect:/thesis/list";
        }
        Thesis thesis = thesisService.getThesis(id);
        if (thesis == null) {
            return "forward:/thesis/list";
        }
        model.addAttribute("thesis", thesis);
        return "thesis/thesisDetail";
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
            Exposer exposer = thesisService.exportUrl(id);
            result = Result.ok(exposer);
        } catch (Exception e) {
            e.printStackTrace();
            result = Result.isNotOK(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{thesisId}/{md5}/execution",method = RequestMethod.POST)
    @ResponseBody
    public Result execute(@PathVariable("thesisId") Integer thesisId, @PathVariable("md5") String md5,
                          @CookieValue(value = "studentId", required = false) Integer studentId,
                          @CookieValue(value = "studentMD5", required = false) String studentMD5) {
        if (studentId == null) {
            return Result.isNotOK("未注册");
        }
        try {
            Execution execution = thesisService.executeThesis(thesisId, studentId, md5,studentMD5);
            return Result.ok(execution);
        } catch (RepeatException e1) {
            Execution execution = new Execution(thesisId, StateEnum.REPEAT_KILL);
            return Result.ok(execution);
        } catch (CloseException e2) {
            Execution execution = new Execution(thesisId, StateEnum.END);
            return Result.ok(execution);
        } catch (Exception e) {
            Execution execution = new Execution(thesisId, StateEnum.INNER_ERROR);
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
        List<Thesis> thesisList = thesisService.listThesisByStudentId(studentId);
        model.addAttribute("list",thesisList);
        return "thesis/thesisCheck";
    }

    @RequestMapping(value = "/{thesisId}/rollback",method = RequestMethod.POST)
    @ResponseBody
    public Result rollback(@PathVariable("thesisId") Integer thesisId,
                           @CookieValue(value = "studentId", required = false) Integer studentId,
                           @CookieValue(value = "studentMD5", required = false) String studentMD5) {
        if (studentId == null|| StringUtils.isEmpty(studentMD5)) {
            return Result.isNotOK("未登录");
        }
        try {
            Execution execution = thesisService.rollBackThesis(thesisId, studentId,studentMD5);
            return Result.ok(execution);
        } catch (CloseException e2) {
            Execution execution = new Execution(thesisId, StateEnum.END);
            return Result.ok(execution);
        } catch (Exception e) {
            Execution execution = new Execution(thesisId, StateEnum.DATE_REWRITE);
            return Result.ok(execution);
        }

    }




}
