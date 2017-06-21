package com.scnu.utils;

import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Created by ldb on 2017/4/1.
 */
public class ResponseUtil {

    public static void exportExcel(HttpServletResponse response, Workbook wb, String fileName)throws Exception{
        response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("utf-8"),"iso8859-1"));
        response.setContentType("application/ynd.ms-excel;charset=UTF-8");
        OutputStream out=response.getOutputStream();
        wb.write(out);
        out.flush();
        out.close();
    }

    public static void write(Object o, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out=response.getWriter();
        out.println(o.toString());
        out.flush();
        out.close();
    }
}
