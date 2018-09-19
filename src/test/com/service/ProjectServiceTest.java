package com.service;

import com.entity.Projects;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class ProjectServiceTest {
    @Autowired
    ProjectService projectService;

    @Test
    public void selectProjectByType() {
        List<Projects> projectsList = projectService.selectProjectByType(2);
        System.out.println(projectsList.size());
    }

    @Test
    public void selectAllProjectByPage() {
        List<Projects> projectsList = projectService.selectAllProjectByPage(1,12);
        System.out.println(projectsList.size());
    }

    @Test
    public void myAllProject() {
        List<Projects> projectsList = projectService.myAllProject(2);
        System.out.println(projectsList.size());
    }

    @Test
    public void myBeginProject() {
        List<Projects> projectsList = projectService.myBeginProject(2);
        System.out.println(projectsList.size());
    }
}