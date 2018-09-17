package com.controller;

import com.entity.User;
import com.google.gson.Gson;
import com.resonse.ResponseCode;
import com.service.UserService;
import com.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping("/login.do")
    public void checkLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //校验
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            ResponseUtil.responseFailure(response, "用户名或密码为空", ResponseCode.LOGIN_ERROR_INVALID_PARAMETER);
            return;
        }
        try {
            if (checkEmail(username)) {
                List<User> users = userService.selectByEnailAndPwd(username, password);
                if (users.get(0)==null){
                    //用户名或密码错误
                    ResponseUtil.responseFailure(response,"用户名或密码错误",ResponseCode.LOGIN_ERROR_INVALID_PARAMETER);
                    return;
                }else {
                    //保存用户登陆信息到session
                    request.getSession().setAttribute("user",users.get(0));
                    String s = new Gson().toJson(users.get(0));
                    ResponseUtil.responseSuccess(response,s,ResponseCode.LOGIN_SUCCESS);
                }
            } else {
                List<User> users = userService.selectByPhoneAndPwd(username, password);
                if (users.get(0)==null){
                    //用户名或密码错误
                    ResponseUtil.responseFailure(response,"用户名密码错误",ResponseCode.LOGIN_ERROR_INVALID_PARAMETER);
                    return;
                }else {
                    //保存用户登陆信息到session
                    request.getSession().setAttribute("user",users.get(0));
                    String ss = new Gson().toJson(users.get(0));
                    ResponseUtil.responseSuccess(response,ss,ResponseCode.LOGIN_SUCCESS);
                }
            }
        } catch (Exception e) {
            log.error("{error}",e);
            ResponseUtil.responseFailure(response, "server error", ResponseCode.LOGIN_ERROR_INVALID_PARAMETER);
            return;
        }
    }
    public boolean checkEmail(String username) {
        String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式
        Pattern p = Pattern.compile(RULE_EMAIL);
        //正则表达式的匹配器
        Matcher m = p.matcher(username);
        return m.find();
    }
}
