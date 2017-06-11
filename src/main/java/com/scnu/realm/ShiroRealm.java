package com.scnu.realm;

import com.scnu.entity.Admin;
import com.scnu.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ldb on 2017/4/27.
 * 自定义Realm
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private AdminService adminService;


    //权限设置
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //身份设置
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName=(String)authenticationToken.getPrincipal();
        Admin admin=adminService.getAdminByUserName(userName);
        if(admin!=null){
            SecurityUtils.getSubject().getSession().setAttribute("currentAdmin", admin);
            //设置session超时时间1h。
            SecurityUtils.getSubject().getSession().setTimeout(3600000);
            AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(admin.getUserName(),admin.getPassword(),"xx");
            return authcInfo;
        }else{
            return null;
        }
    }
}
