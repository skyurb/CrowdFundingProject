package com.controller;

import com.annotation.Permission;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


//主要处理后台业务逻辑 如项目审核 实名
@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/manualCheck.do")
    @Permission(roles = {"super"})
    public String manualRealCheck(){
        String j="ha";
        return j;
    }
}
