package com.controller;

import com.aliyuncs.exceptions.ClientException;


import com.google.gson.Gson;
import com.resonse.ResponseCode;
import com.resonse.ResponseMessage;
import com.util.GetCodeUtil;

import com.util.MailUtil;
import com.util.ResponseUtil;
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
    public void getCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String account = req.getParameter("account");
        code = GetCodeUtil.getCode();


        try {
            if (account.contains("@")) {
                System.out.println(account);
                new MailUtil(account, code).run();
            } else {
                registerController.code = SMSUtil.sendSMS(account, code);
            }
            ResponseUtil.responseSuccess(resp, "success", ResponseCode.SEND_SUCCESS);
        } catch (ClientException e) {
            ResponseUtil.responseSuccess(resp, "", 404);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }





    }

    @RequestMapping("/reg.do")
    public void register(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userCode = req.getParameter("code");
        if (!code.equals(userCode)) {
            ResponseUtil.responseFailure(resp,"code is different",ResponseCode.CODE_IS_DIFFERENT);
        }


    }

}