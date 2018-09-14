package com.controller;

import com.aliyuncs.exceptions.ClientException;

import com.util.SMSUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/register")
public class registerController {
    static String code;


    @RequestMapping("/getCode.do")
    public void getCode(HttpServletRequest req, HttpServletResponse res) throws ClientException, InterruptedException {
        String phoneNumber = req.getParameter("phoneNumber");
        System.out.println(phoneNumber);
        code = SMSUtil.sendSMS(phoneNumber);
    }

    @RequestMapping("/register")
    public void register(){
        System.out.println(code);
    }

}