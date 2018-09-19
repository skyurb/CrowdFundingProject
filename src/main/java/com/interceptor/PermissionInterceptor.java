package com.interceptor;

import com.annotation.Permission;
import com.entity.Admin;
import com.service.AdminService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class PermissionInterceptor implements HandlerInterceptor {
    @Autowired
    AdminService adminService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        if (admin==null){
            return false;
        }
        HandlerMethod method = (HandlerMethod) handler;
        boolean Present = method.getMethod().isAnnotationPresent(Permission.class);
        //如果方法上有此注解   则需要进行权限控制  如果没有 则认为所有人都可以访问
        if (!Present){
            return true;
        }else {
            if (admin.getRoles()==null){
                admin.setRoles(adminService.findRoleByAdminId(admin.getAdminId()));
            }
        }
        List<String> rolesList = admin.getRoles();
        String[] roles = method.getMethod().getAnnotation(Permission.class).roles();
        Collection intersection = CollectionUtils.intersection(rolesList, Arrays.asList(roles));
        if (intersection.size()>0){
            return  true;
        }else{
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
