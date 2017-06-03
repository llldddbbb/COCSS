package com.scnu.service;

import com.scnu.dto.CourseResult;
import com.scnu.entity.Admin;

/**
 * Created by ldb on 2017/6/3.
 */
public interface AdminService {

    CourseResult checkLogin(Admin admin);

}
