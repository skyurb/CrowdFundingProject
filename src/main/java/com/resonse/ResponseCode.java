package com.resonse;

/*
*@ClassName:ResonseCode
 @Description:TODO
 @Author:
 @Date:2018/8/27 11:33 
 @Version:v1.0
*/
//常量类  用来封装  响应码 与响应之间的对应关系
public class ResponseCode {
    //非法参数
    public static final int LOGIN_ERROR_INVALID_PARAMETER=0;
    //参数为空
    public static final int LOGIN_ERROR_NULL_PARAMETER=1;
    //响应成功
    public static final int LOGIN_SUCCESS=2;
    //验证码发送成功
    public static final int SEND_SUCCESS=3;
    //验证码不对应
    public static final int CODE_IS_DIFFERENT=4;
    //账号已存在
    public static final int EXISTS=5;

}
