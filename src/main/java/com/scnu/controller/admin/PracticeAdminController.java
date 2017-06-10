package com.scnu.controller.admin;

import com.scnu.dto.PageBean;
import com.scnu.dto.PageResult;
import com.scnu.dto.Result;
import com.scnu.entity.Practice;
import com.scnu.exception.CourseException;
import com.scnu.service.PracticeService;
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
public class PracticeAdminController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
    }

    @Autowired
    private PracticeService practiceService;

    @RequestMapping("/practiceManage")
    public String showPracticeManage(){
        return "background/practiceManage";
    }


    @RequestMapping(value = "/practice/list/{page}",method = RequestMethod.GET)
    @ResponseBody
    public PageResult<Practice> getPracticeList(@PathVariable Integer page, Integer pageSize){
        //获取评论列表
        PageBean pageBean=new PageBean(page, pageSize);
        PageResult<Practice> result = practiceService.listPractice(pageBean);
        return result;
    }


    @RequestMapping(value = "/practice",method = RequestMethod.POST)
    public String addPractice(Practice practice){
        Result result =  practiceService.addPractice(practice);
        if(result.isSuccess()){
            return "redirect:/admin/practiceManage";
        }else{
            return null;
        }
    }

    @RequestMapping(value = "/practice",method = RequestMethod.PUT)
    public String updatePractice(Practice practice){
        Result result;
        try {
            result = practiceService.updatePractice(practice);
            if(result.isSuccess()){
                return "redirect:/admin/practiceManage";
            }else{
                return null;
            }
        }catch ( Exception e){
            return null;
        }

    }

    @RequestMapping(value = "/practice/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public Result deletePractice(@PathVariable Integer id){
        try {
            return practiceService.deletePractice(id);
        }catch ( Exception e){
            return Result.isNotOK("删除失败");
        }
    }

}
