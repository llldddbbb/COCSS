package com.scnu.dao;

import com.scnu.entity.Admin;
import com.scnu.utils.MyMapper;

/**
 * Created by ldb on 2017/6/3.
 */
public interface AdminMapper extends MyMapper<Admin> {

    /**
     * 验证管理员登录
     * @param admin
     * @return
     */
    Admin checkLogin(Admin admin);

}
