package com.scnu.utils;

import com.scnu.entity.Student;
import org.springframework.util.DigestUtils;

/**
 * Created by LSY on 2017/6/9.
 */
public class SecureUtil {

    //加入盐值，混淆md5
    private static final String salt = "asdfgasrf^&*23*&(hjkKH;74jhsdfsdajhkl&*(&kljf";

    public static String getMD5(int id) {
        //添加盐值，混淆MD5
        String str = id + "/" + salt;
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    public static String getMD5(Student student) {
        if(student==null){
            return null;
        }
        //添加盐值，混淆MD5
        String str = student.getId()+ "/" +student.getPassword() + "/" + salt;
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }
}
