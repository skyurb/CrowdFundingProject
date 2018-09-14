package com.util;
import com.google.gson.Gson;
import com.resonse.ResponseMessage;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
*@ClassName:ResponseUtil
 @Description:TODO
 @Author:
 @Date:2018/8/27 18:11 
 @Version:v1.0
*/
public class ResponseUtil {

    public static void response(HttpServletResponse resp,String content,String error,boolean isSuccess,int code) throws IOException {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setContent(content);
        responseMessage.setSuccess(isSuccess);
        responseMessage.setError(error);
        responseMessage.setCode(code);
        String json = new Gson().toJson(responseMessage);
        resp.getWriter().println(json);
    }

    public static void responseSuccess(HttpServletResponse resp,String content,int code) throws IOException {
        response(resp,content,null,true,code);
    }


    public static void responseFailure(HttpServletResponse resp,String error,int code) throws IOException {
        response(resp,null,error,false,code);
    }




}
