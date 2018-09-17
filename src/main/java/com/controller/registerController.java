package com.controller;
import com.aliyuncs.exceptions.ClientException;
import com.entity.User;
import com.resonse.ResponseCode;
import com.service.UserService;
import com.util.ResponseUtil;
import com.util.SendMailUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Controller
@RequestMapping("/register")
@Slf4j
public class registerController {
    @Autowired
    UserService userService;
    @RequestMapping("/getCode.do")
    public void getCode(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String phoneNumber = req.getParameter("phoneNumber");
        //检查账号类型
        if (checkEmail(phoneNumber)) {
            List<User> byEmailName = userService.findByEmailName(phoneNumber);
            if (byEmailName.size() > 0) {
                ResponseUtil.responseFailure(res, "账号已存在", ResponseCode.LOGIN_ERROR_INVALID_PARAMETER);
                return;
            } else {
               String code=identifyingCode(4);
                try {
                    SendMailUtil.SendEmail(phoneNumber,code);
                } catch (EmailException e) {
                    log.error("{error}", e);
                    ResponseUtil.responseFailure(res, "发送失败", 2);
                }
                //保存验证码
                req.getSession().setAttribute("code",code);
                //设置验证时长
                long current = System.currentTimeMillis();
                long expireTime=current+1000*60*5;
                req.getSession().setAttribute("codeExpireTime",expireTime);
            }
        } else {
            List<User> byPhone = userService.findByPhone(phoneNumber);
            if (byPhone.size() > 0) {
                ResponseUtil.responseFailure(res, "账号已存在", ResponseCode.LOGIN_ERROR_INVALID_PARAMETER);
                return;
            } else {
                String code = identifyingCode(4);
                try {
                    userService.sendSms(phoneNumber, code);
                } catch (ClientException e) {
                    log.error("{error}", e);
                    ResponseUtil.responseFailure(res, "发送失败", 2);
                }
                //保存验证码
                req.getSession().setAttribute("code",code);
                //设置验证时长
                long current = System.currentTimeMillis();
                long expireTime=current+1000*60*5;
                req.getSession().setAttribute("codeExpireTime",expireTime);
            }
        }
    }
    @RequestMapping("/register.do")
    public void register(HttpServletRequest req, HttpServletResponse res) throws IOException {
        User user = new User();
        String userNumber = req.getParameter("userNumber");
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String reCode = req.getParameter("code");
        //校验时长
        HttpSession session = req.getSession();
        Long expireTime = (Long) session.getAttribute("codeExpireTime");
        long current = System.currentTimeMillis();
        if (current>expireTime){
            //过期
            ResponseUtil.responseFailure(res,"验证码已过期，请重试",2);
            return;
        }
        //校验
        String  sessionCode= (String) session.getAttribute("code");
        if (!sessionCode.equals(reCode)) {
            ResponseUtil.responseFailure(res, "验证码错误", ResponseCode.LOGIN_ERROR_INVALID_PARAMETER);
            return;
        }
        //检查账号类型
        if (checkEmail(userNumber)) {
            user.setUsEmail(userNumber);
            user.setUsName(userName);
            user.setUsPassword(password);
            user.setUsCreateTime(new Date());
            int insert = userService.insert(user);
            ResponseUtil.responseSuccess(res, "注册成功" + insert, 2);
        } else {
            user.setUsPhone(userNumber);
            user.setUsName(userName);
            user.setUsPassword(password);
            user.setUsCreateTime(new Date());
            int insert = userService.insert(user);
            ResponseUtil.responseSuccess(res, "注册成功" + insert, 2);
        }
        //System.out.println(code);
    }

    //验证是否邮箱
    public boolean checkEmail(String username) {
        String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式
        Pattern p = Pattern.compile(RULE_EMAIL);
        //正则表达式的匹配器
        Matcher m = p.matcher(username);
        return m.find();
    }

    //生成验证码
    private String identifyingCode(int i) {
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random();
        for (int j = 0; j < i; j++) {
            int num = random.nextInt(9);
            stringBuffer.append(num);
        }
        return stringBuffer.toString();
    }


}