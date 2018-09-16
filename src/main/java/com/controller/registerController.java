package com.controller;

import com.aliyuncs.exceptions.ClientException;

import com.entity.User;
import com.resonse.ResponseCode;
import com.service.UserService;
import com.util.CodeUtil;
import com.util.MailUtil;
import com.util.ResponseUtil;
import com.util.SMSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/register")
public class registerController {

    @Autowired
    UserService userService;
    static String code;


    @RequestMapping("/getCode.do")
    public void getCode(HttpServletRequest req, HttpServletResponse res) throws ClientException, InterruptedException, IOException {
        String phoneNumber = req.getParameter("phoneNumber");
        //检查账号类型
        if (checkEmail(phoneNumber)) {
            List<User> byEmailName = userService.findByEmailName(phoneNumber);
            if (byEmailName.size() > 0) {
                ResponseUtil.responseFailure(res, "账号已存在", ResponseCode.LOGIN_ERROR_INVALID_PARAMETER);
                return;
            }else {
                code =CodeUtil.generateUniqueCode();
                System.out.println(code);
                new MailUtil(phoneNumber,code).run();
            }
        } else {
            List<User> byPhone = userService.findByPhone(phoneNumber);
            if (byPhone.size() > 0) {
                ResponseUtil.responseFailure(res, "账号已存在", ResponseCode.LOGIN_ERROR_INVALID_PARAMETER);
                return;
            }else {
                code = SMSUtil.sendSMS(phoneNumber);
            }
        }
        //System.out.println(phoneNumber);

    }

    @RequestMapping("/register.do")
    public void register(HttpServletRequest req, HttpServletResponse res) throws IOException {
        User user = new User();
        String userNumber = req.getParameter("userNumber");
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String reCode = req.getParameter("code");
        if (!code.equals(reCode)) {
            ResponseUtil.responseFailure(res, "验证码错误codeError", ResponseCode.LOGIN_ERROR_INVALID_PARAMETER);
            return;
        }
        //检查账号类型
        if (checkEmail(userNumber)) {
            user.setUsEmail(userNumber);
            user.setUsName(userName);
            user.setUsPassword(password);
            user.setUsCreateTime(new Date());
            int insert = userService.insert(user);
            ResponseUtil.responseSuccess(res, "注册成功"+insert, 2);
        } else {
            user.setUsPhone(userNumber);
            user.setUsName(userName);
            user.setUsPassword(password);
            user.setUsCreateTime(new Date());
            int insert = userService.insert(user);
            ResponseUtil.responseSuccess(res, "注册成功"+insert, 2);
        }
        System.out.println(code);
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