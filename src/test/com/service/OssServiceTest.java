package com.service;

import com.aliyun.oss.model.PutObjectResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class OssServiceTest {
    @Autowired
    OssService ossService;

    @Test
    public void upload() throws FileNotFoundException {

        String l="C:\\Users\\Administrator\\Pictures\\Camera Roll\\60d43db6951028ea0f19a45704630ea7.jpg";
        File file = new File(l);
        FileInputStream fileInputStream = new FileInputStream(file);
        PutObjectResult skyurb = ossService.upload("skyurb", "project/p1.jpg", fileInputStream);

    }
}