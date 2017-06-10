package com.scnu.controller.admin;

import com.scnu.dto.PageBean;
import com.scnu.dto.PageResult;
import com.scnu.dto.Result;
import com.scnu.entity.Thesis;
import com.scnu.service.ThesisService;
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
public class ThesisAdminController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
    }

    @Autowired
    private ThesisService thesisService;

    @RequestMapping("/thesisManage")
    public String showThesisManage(){
        return "background/thesisManage";
    }


    @RequestMapping(value = "/thesis/list/{page}",method = RequestMethod.GET)
    @ResponseBody
    public PageResult<Thesis> getThesisList(@PathVariable Integer page, Integer pageSize){
        //获取评论列表
        PageBean pageBean=new PageBean(page, pageSize);
        PageResult<Thesis> result = thesisService.listThesis(pageBean);
        return result;
    }


    @RequestMapping(value = "/thesis",method = RequestMethod.POST)
    public String addThesis(Thesis thesis){
        Result result =  thesisService.addThesis(thesis);
        if(result.isSuccess()){
            return "redirect:/admin/thesisManage";
        }else{
            return null;
        }
    }

    @RequestMapping(value = "/thesis",method = RequestMethod.PUT)
    public String updateThesis(Thesis thesis){
        Result result;
        try {
            result = thesisService.updateThesis(thesis);
            if(result.isSuccess()){
                return "redirect:/admin/thesisManage";
            }else{
                return null;
            }
        }catch ( Exception e){
            return null;
        }

    }

    @RequestMapping(value = "/thesis/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public Result deleteThesis(@PathVariable Integer id){
        try {
            return thesisService.deleteThesis(id);
        }catch ( Exception e){
            return Result.isNotOK("删除失败");
        }
    }

}
