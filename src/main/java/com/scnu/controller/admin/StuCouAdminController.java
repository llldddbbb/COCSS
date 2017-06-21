package com.scnu.controller.admin;

import com.scnu.dto.PageBean;
import com.scnu.dto.PageResult;
import com.scnu.dto.Result;
import com.scnu.entity.StuCou;
import com.scnu.service.StuCouService;
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
public class StuCouAdminController {
    
    @Autowired
    private StuCouService stuCouService;

    @RequestMapping("/stuCouManage")
    public String showStuCouManage(){
        return "background/stuCouManage";
    }


    @RequestMapping(value = "/stuCou/list/{page}",method = RequestMethod.GET)
    @ResponseBody
    public PageResult<StuCou> getStuCouList(@PathVariable Integer page, Integer pageSize){
        //获取评论列表
        PageBean pageBean=new PageBean(page, pageSize);
        PageResult<StuCou> result = stuCouService.listStuCou(pageBean);
        return result;
    }


    @RequestMapping(value = "/stuCou",method = RequestMethod.POST)
    public String addStuCou(StuCou stuCou){
        Integer result = stuCouService.addStuCou(stuCou);
        if(result>0){
            return "redirect:/admin/stuCouManage";
        }else{
            return null;
        }
    }

    @RequestMapping(value = "/stuCou",method = RequestMethod.PUT)
    public String updateStuCou(StuCou stuCou){
        Integer result = stuCouService.updateStuCou(stuCou);
        if(result>0){
            return "redirect:/admin/stuCouManage";
        }else{
            return null;
        }
    }

    @RequestMapping(value = "/stuCou/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public Result deleteStuCou(@PathVariable Integer id){
        return stuCouService.deleteStuCou(id);
    }

    @RequestMapping("/stuCou/export")
    public void  exportExcel(HttpServletResponse response) throws  Exception{
        Workbook wb=new HSSFWorkbook();
        PageResult<StuCou> result = stuCouService.listStuCou(new PageBean());
        List<StuCou> stuCouList=result.getRows();
        WorkbookUtil.fullExcelDataStuCou(stuCouList,wb);
        ResponseUtil.exportExcel(response,wb,"计网选实习列表.xls");
    }

}
