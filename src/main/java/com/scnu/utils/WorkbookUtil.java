package com.scnu.utils;

import com.scnu.entity.StuCou;
import com.scnu.entity.StuPra;
import com.scnu.entity.StuThe;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

/**
 * Created by ldb on 2017/4/1.
 */
public class WorkbookUtil {

    public static void fullExcelDataStuCou(List<StuCou> stuCouList, Workbook wb){
        Sheet sheet = wb.createSheet("data");
        Row row;
        int rowIndex=0;
        for(int i=0;i<stuCouList.size();i++){
            StuCou stuCou=stuCouList.get(i);
            row = sheet.createRow(rowIndex++);
            sheet.setColumnWidth(i, 18 * 256);
            row.createCell(0).setCellValue(stuCou.getStudent().getUserName());
            row.createCell(1).setCellValue(stuCou.getStudent().getStuName());
            row.createCell(2).setCellValue(stuCou.getStudent().getGradeName()+stuCou.getStudent().getMajorName()+stuCou.getStudent().getClassName());
            row.createCell(3).setCellValue(stuCou.getCourse().getCourseName());
            row.createCell(4).setCellValue(stuCou.getCourse().getTeacher());
            row.createCell(5).setCellValue(DateUtil.formatDateToStr(stuCou.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
        }
    }

    public static void fullExcelDataStuPra(List<StuPra> stuPraList, Workbook wb) {
        Sheet sheet = wb.createSheet("data");
        Row row;
        int rowIndex = 0;
        for (int i = 0; i < stuPraList.size(); i++) {
            StuPra stuPra = stuPraList.get(i);
            row = sheet.createRow(rowIndex++);
            sheet.setColumnWidth(i, 18 * 256);
            row.createCell(0).setCellValue(stuPra.getStudent().getUserName());
            row.createCell(1).setCellValue(stuPra.getStudent().getStuName());
            row.createCell(2).setCellValue(stuPra.getStudent().getGradeName() + stuPra.getStudent().getMajorName() + stuPra.getStudent().getClassName());
            row.createCell(3).setCellValue(stuPra.getPractice().getSchoolName());
            row.createCell(4).setCellValue(stuPra.getPractice().getSite());
            row.createCell(5).setCellValue(DateUtil.formatDateToStr(stuPra.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        }
    }
    
    public static void fullExcelDataStuThe(List<StuThe> stuTheList, Workbook wb) {
        Sheet sheet = wb.createSheet("data");
        Row row;
        int rowIndex = 0;
        for (int i = 0; i < stuTheList.size(); i++) {
            StuThe stuThe = stuTheList.get(i);
            row = sheet.createRow(rowIndex++);
            sheet.setColumnWidth(i, 18 * 256);
            row.createCell(0).setCellValue(stuThe.getStudent().getUserName());
            row.createCell(1).setCellValue(stuThe.getStudent().getStuName());
            row.createCell(2).setCellValue(stuThe.getStudent().getGradeName() + stuThe.getStudent().getMajorName() + stuThe.getStudent().getClassName());
            row.createCell(3).setCellValue(stuThe.getThesis().getThesisName());
            row.createCell(4).setCellValue(stuThe.getThesis().getTeacher());
            row.createCell(5).setCellValue(DateUtil.formatDateToStr(stuThe.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        }
    }
}
