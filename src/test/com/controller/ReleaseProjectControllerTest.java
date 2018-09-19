package com.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml","classpath:spring-mvc.xml"})
@WebAppConfiguration
public class ReleaseProjectControllerTest {
    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
       mockMvc= MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void addProject() throws Exception {
        mockMvc.perform(post("/Projects/addProject.do")
                .param("id","10")
        .param("psCustName","张全蛋")
        .param("psCustPhone","1568548")
        .param("psName","运动平衡车"))
                .andDo(print())
                .andExpect(status().isOk());

    }
}