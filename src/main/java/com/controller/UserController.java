package com.controller;

import com.entity.RealCheckEntity;
import com.entity.User;
import com.exception.CrowdFundingException;
import com.google.gson.Gson;
import com.resonse.ResponseCode;
import com.service.OssService;
import com.service.RealCheckService;
import com.service.UserService;
import com.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    OssService ossService;
    @Autowired
    RealCheckService realCheckService;
    @RequestMapping("/login.do")
    public void checkLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //校验
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            ResponseUtil.responseFailure(response, "用户名或密码为空", ResponseCode.LOGIN_ERROR_INVALID_PARAMETER);
            return;
        }
        if (request.getSession().getAttribute("user")==username){
            ResponseUtil.responseSuccess(response,"已经在登陆状态",2);
            return;
        }
        try {
            if (checkEmail(username)) {
                List<User> users = userService.selectByEnailAndPwd(username, password);
                if (users.size()==0){
                    //用户名或密码错误
                    ResponseUtil.responseFailure(response,"用户名或密码错误",ResponseCode.LOGIN_ERROR_INVALID_PARAMETER);
                    return;
                }else {
                    //保存用户登陆信息到session
                    request.getSession().setAttribute("user",users.get(0).getUsEmail());
                    String s = new Gson().toJson(users.get(0));
                    ResponseUtil.responseSuccess(response,s,ResponseCode.LOGIN_SUCCESS);
                }
            } else {
                List<User> users = userService.selectByPhoneAndPwd(username, password);
                if (users.size()==0){
                    //用户名或密码错误
                    ResponseUtil.responseFailure(response,"用户名密码错误",ResponseCode.LOGIN_ERROR_INVALID_PARAMETER);
                    return;
                }else {
                    //保存用户登陆信息到session
                    request.getSession().setAttribute("user",users.get(0).getUsPhone());
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
    //实名认证
    @RequestMapping("/realCheck.do")
    public String realCheck(String phone, String idCard, HttpServletRequest request,HttpServletResponse response) throws IOException {
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //获取multiRequest 中所有的文件名
            Iterator iter = multiRequest.getFileNames();

            List<MultipartFile> fileList = getUploadFiles(multiRequest);

            String idCardPositive = "";
            String idCardNegative = "";
            String idCardHand = "";

            while (iter.hasNext()) {
                //一次遍历所有文件
                MultipartFile file = multiRequest.getFile(iter.next().toString());
                if (file != null) {
                    System.out.println(file.getName());
                    try {
                        if (file.getName().contains("idCardPositive")) {
                            //上传身份证正面
                            idCardPositive = UUID.randomUUID().toString() + ".jpeg";
                            ossService.upload("crowdfundingweb", idCardPositive, file.getInputStream());
                        }
                        if (file.getName().contains("idCardNegative")) {
                            //上传身份证正面
                            idCardNegative = UUID.randomUUID().toString() + ".jpeg";
                            ossService.upload("crowdfundingweb", idCardNegative, file.getInputStream());
                        }
                        if (file.getName().contains("idCardHand")) {
                            //上传身份证正面
                            idCardHand = UUID.randomUUID().toString() + ".jpeg";
                            ossService.upload("crowdfundingweb", idCardHand, file.getInputStream());
                        }
                    } catch (Exception e) {
                        //throw new CrowdFundingException(2, "上传失败");
                    }

                }

            }
            // 插入数据库
            RealCheckEntity realCheckEntity = new RealCheckEntity(0, phone, idCard, idCardPositive, idCardNegative, idCardHand, 0);
            int rows = realCheckService.insert(realCheckEntity);
            if (rows == 1) {
                //return ResponseUtil.responseSuccess(1, "申请成功等待审核");
            } else {
                //return ResponseUtil.responseError(2, "申请失败，请重试");
            }

        }
        //return ResponseUtil.responseError(2, "请上传图片");
        ResponseUtil.responseFailure(response,"请上传图片",2);
        return "请上传图片";
    }


//获取请求中所有的file

    private List<MultipartFile> getUploadFiles(MultipartHttpServletRequest multiRequest) {
        Iterator iter = multiRequest.getFileNames();
        ArrayList<MultipartFile> multipartFiles = new ArrayList<>();
        while (iter.hasNext()) {
            if (iter.next() != null) {
                MultipartFile file = multiRequest.getFile(iter.next().toString());
                multipartFiles.add(file);
            }
        }
        return multipartFiles;
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
