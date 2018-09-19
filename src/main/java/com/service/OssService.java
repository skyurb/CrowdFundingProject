package com.service;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.stereotype.Service;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.InputStream;


//用于文件上传
@Service
public class OssService {
    // Endpoint以杭州为例，其它Region请按实际情况填写。
    String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    String accessKeyId = "LTAIR2pQBqvhvpfQ";
    String accessKeySecret = "olB0URdLTJpjZ8bDJVzvbwlbTBP80V";
    public PutObjectResult upload(String bucketName, String fileName, InputStream inputStream) throws FileNotFoundException {
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 上传文件流。
        PutObjectResult putObjectResult = ossClient.putObject(bucketName, fileName, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
        return putObjectResult;
    }
    public void downLoad(String bucketName,String objectName,String target){
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(target));
    // 关闭OSSClient。
        ossClient.shutdown();
    }

}
