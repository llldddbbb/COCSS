package com.scnu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ldb on 2017/3/29.
 */
public class DateUtil {

    //获取分钟差
    public static long  getMinDifference(String dateStart){
        long result = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date d1 = format.parse(dateStart);
            Date d2 = new Date();
            //毫秒ms
            long diff = d2.getTime() - d1.getTime();
            result=diff/60000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String formatStrToStr(String date,String type){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result="";
        try {
            Date d=format.parse(date);
            result=DateUtil.formatDateToStr(d,type);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String getCurrentDateStr(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    public static String formatDateToStr(Date date,String type){
        SimpleDateFormat format = new SimpleDateFormat(type);
        return format.format(date);
    }

    public static Date formatStrToDate(String date,String type) throws Exception{
        SimpleDateFormat format = new SimpleDateFormat(type);
        return format.parse(date);
    }
}
