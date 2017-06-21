package com.scnu.controller.admin;

import com.scnu.dto.PageBean;
import com.scnu.dto.PageResult;
import com.scnu.dto.Result;
import com.scnu.entity.StuPra;
import com.scnu.service.StuPraService;
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
public class StuPraAdminController {
    
    @Autowired
    private StuPraService stuPraService;

    @RequestMapping("/stuPraManage")
    public String showStuPraManage(){
        return "background/stuPraManage";
    }


    @RequestMapping(value = "/stuPra/list/{page}",method = RequestMethod.GET)
    @ResponseBody
    public PageResult<StuPra> getStuPraList(@PathVariable Integer page, Integer pageSize){
        //获取评论列表
        PageBean pageBean=new PageBean(page, pageSize);
        PageResult<StuPra> result = stuPraService.listStuPra(pageBean);
        return result;
    }


    @RequestMapping(value = "/stuPra",method = RequestMethod.POST)
    public String addStuPra(StuPra stuPra){
        Integer result = stuPraService.addStuPra(stuPra);
        if(result>0){
            return "redirect:/admin/stuPraManage";
        }else{
            return null;
        }
    }

    @RequestMapping(value = "/stuPra",method = RequestMethod.PUT)
    public String updateStuPra(StuPra stuPra){
        Integer result = stuPraService.updateStuPra(stuPra);
        if(result>0){
            return "redirect:/admin/stuPraManage";
        }else{
            return null;
        }
    }

    @RequestMapping(value = "/stuPra/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public Result deleteStuPra(@PathVariable Integer id){
        return stuPraService.deleteStuPra(id);
    }

    @RequestMapping("/stuPra/export")
    public void exportExcel(HttpServletResponse response) throws Exception {
        Workbook wb = new HSSFWorkbook();
        PageResult<StuPra> result = stuPraService.listStuPra(new PageBean());
        List<StuPra> stuPraList = result.getRows();
        WorkbookUtil.fullExcelDataStuPra(stuPraList, wb);
        ResponseUtil.exportExcel(response, wb, "电商选实习列表.xls");
    }

}
