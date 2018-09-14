package com.service;

import com.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml","classpath:spring-mvc.xml"})
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    public void selectByPhoneAndPwd() {
        List<User> users = userService.selectByPhoneAndPwd("18937715187", "2561");
        System.out.println(users.size());
    }

    @Test
    public void selectByEnailAndPwd() {
    }
}