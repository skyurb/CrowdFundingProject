package com.controller;

import com.aliyuncs.exceptions.ClientException;


import com.dao.UserMapper;
import com.entity.User;
//import com.google.gson.Gson;
import com.resonse.ResponseCode;
//import com.resonse.ResponseMessage;
import com.util.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/register")
@Slf4j
public class registerController {
    static String code;
    static long createTime;
    static String account;
    @Autowired
    UserMapper userMapper;


    @RequestMapping("/getCode.do")
    public void getCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        account = req.getParameter("account");
        code = GetCodeUtil.getCode();
        createTime =System.currentTimeMillis()+5*60*1000;

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
            log.error(e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }





    }

    @RequestMapping("/reg.do")
    public void register(HttpServletRequest req, HttpServletResponse resp) throws IOException, InstantiationException, IllegalAccessException {
        long nowTime = System.currentTimeMillis();
        if (nowTime>createTime){
            code=null;
            ResponseUtil.responseFailure(resp,"code is overTime",ResponseCode.CODE_IS_DIFFERENT);
            return;
        }


        String usAccount = req.getParameter("account");
        String usName = req.getParameter("usName");
        String usCode = req.getParameter("usCode");

        /*User user = new User();*/
        /*User user = userMapper.selectByMail(account);
        System.out.println(user);*/
        if (!code.equals(usCode)||!usAccount.equals(account)) {
            ResponseUtil.responseFailure(resp,"code is different",ResponseCode.CODE_IS_DIFFERENT);
            return;
        }

        User userByName = userMapper.selectByName(usName);
        if (userByName!=null){
            ResponseUtil.responseFailure(resp,"mail is exists",ResponseCode.EXISTS);
            return;
        }

        if (account.contains("@")){
            User userByMail = userMapper.selectByMail(account);
            if (userByMail!=null){
                ResponseUtil.responseFailure(resp,"mail is exists",ResponseCode.EXISTS);
                return;
            }/*else {
                user.setUsEmail(account);
            }*/
        }else {
            User userByPhone = userMapper.selectByPhone(account);
            if (userByPhone!=null){
                ResponseUtil.responseFailure(resp,"phone is exists",ResponseCode.EXISTS);
                return;
            }
            /*else {
                user.setUsPhone(account);
            }*/
        }


        /*user.setUsCode(usCode);
        user.setUsName(usName);
        user.setUsPassword(usPassword);*/
        User user = BeanUtil.parseFromReq(req, User.class);
        int i = userMapper.insert(user);
        System.out.println(i+"条记录成功");
        ResponseUtil.responseSuccess(resp,"success",ResponseCode.SEND_SUCCESS);

    }

}