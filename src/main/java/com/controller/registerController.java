package com.controller;

import com.aliyuncs.exceptions.ClientException;

import com.util.GetCode;
import com.util.GetCodeUtil;
import com.util.MailUtil;
import com.util.SMSUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/register")
public class registerController {
    static String code;


    @RequestMapping("/getCode.do")
    public void getCode(HttpServletRequest req, HttpServletResponse res) throws ClientException, InterruptedException, IOException {
        String account = req.getParameter("account");

        code = GetCodeUtil.getCode();

        MailUtil mailUtil = new MailUtil(account, code);

        System.out.println(account);
        registerController.code = SMSUtil.sendSMS(account,code);
        res.getWriter().println("{\"res\":\"success\"}");
    }

    @RequestMapping("/reg.do")
    public void register(){

    }

}