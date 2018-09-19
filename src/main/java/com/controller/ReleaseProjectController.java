package com.controller;

import com.entity.Projects;
import com.entity.User;
import com.service.ProjectService;
import com.service.UserService;
import com.util.BeanUtil;
import com.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/Projects")
@Slf4j
public class ReleaseProjectController {
    @Autowired
    ProjectService projectService;
    @Autowired
    UserService userService;
    //（用户）添加项目（判断是否为实名认证）
    @RequestMapping("/addProject.do")
    public void addProject(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uId = request.getParameter("id");
        if(StringUtils.isEmpty(uId)){
            ResponseUtil.responseFailure(response,"请先进行登陆",1);
            return;
        }
        int id = Integer.parseInt(uId);
        //判断是否为实名认证(待更新实名工具类)
        User user = userService.selectById(id);
        if (user==null){
            ResponseUtil.responseFailure(response,"请先进行登陆",1);
            return;
        }
        if (user.getUsIdcard()==null){
            ResponseUtil.responseFailure(response,"请完成实名认证",1);
            return;
        }
        try {
            //添加项目
            Projects projects = BeanUtil.parseFromReq(request, Projects.class);
            projects.setPsUsId(id);
            projects.setPsType(0);
            projectService.addProject(projects);
            ResponseUtil.responseSuccess(response,"插入成功",1);

        } catch (Exception e) {
            log.error("{error}",e);
            ResponseUtil.responseFailure(response,"插入错误",2);
        }

    }



}
