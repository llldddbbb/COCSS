package com.scnu.controller.admin;

import com.scnu.dto.PageBean;
import com.scnu.dto.PageResult;
import com.scnu.dto.Result;
import com.scnu.entity.StuThe;
import com.scnu.service.StuTheService;
import com.scnu.utils.ResponseUtil;
import com.scnu.utils.WorkbookUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by ldb on 2017/6/4.
 */
@Controller
@RequestMapping("/admin")
public class StuTheAdminController {
    
    @Autowired
    private StuTheService stuTheService;

    @RequestMapping("/stuTheManage")
    public String showStuTheManage(){
        return "background/stuTheManage";
    }


    @RequestMapping(value = "/stuThe/list/{page}",method = RequestMethod.GET)
    @ResponseBody
    public PageResult<StuThe> getStuTheList(@PathVariable Integer page, Integer pageSize){
        //获取分页列表
        PageBean pageBean=new PageBean(page, pageSize);
        PageResult<StuThe> result = stuTheService.listStuThe(pageBean);
        return result;
    }


    @RequestMapping(value = "/stuThe",method = RequestMethod.POST)
    public String addStuThe(StuThe stuThe){
        Integer result = stuTheService.addStuThe(stuThe);
        if(result>0){
            return "redirect:/admin/stuTheManage";
        }else{
            return null;
        }
    }

    @RequestMapping(value = "/stuThe",method = RequestMethod.PUT)
    public String updateStuThe(StuThe stuThe){
        Integer result = stuTheService.updateStuThe(stuThe);
        if(result>0){
            return "redirect:/admin/stuTheManage";
        }else{
            return null;
        }
    }

    @RequestMapping(value = "/stuThe/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public Result deleteStuThe(@PathVariable Integer id){
        return stuTheService.deleteStuThe(id);
    }


    @RequestMapping("/stuThe/export")
    public void exportExcel(HttpServletResponse response) throws Exception {
        Workbook wb = new HSSFWorkbook();
        PageResult<StuThe> result = stuTheService.listStuThe(new PageBean());
        List<StuThe> stuTheList = result.getRows();
        WorkbookUtil.fullExcelDataStuThe(stuTheList, wb);
        ResponseUtil.exportExcel(response, wb, "电商选论文列表.xls");
    }
}
