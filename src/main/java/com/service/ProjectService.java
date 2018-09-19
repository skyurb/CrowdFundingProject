package com.service;

import com.dao.ProjectsMapper;
import com.entity.Projects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    ProjectsMapper projectsMapper;
    //添加项目
    public int addProject(Projects projects){
        int insert = projectsMapper.insert(projects);
        return insert;
    }
    //根据种类查询（展示，审核通过）
    public List<Projects> selectProjectByType(int type){
        List<Projects> projectsList = projectsMapper.selectProjectByType(type);
        return projectsList;
    }
    //分页查询全部项目（展示，审核通过）
    public List<Projects> selectAllProjectByPage(int page,int num){
        int startPage=num*page-num;
        int endPage=12*num;
        List<Projects> projectsList = projectsMapper.selectAllProjectByPage(startPage,endPage);
        return projectsList;
    }
    //我的项目（所有）
    public List<Projects> myAllProject(int id){
        List<Projects> projectsList = projectsMapper.myAllProject(id);
        return projectsList;
    }

    //我的项目（进行中）
    public List<Projects> myBeginProject(int id){
        List<Projects> projectsList = projectsMapper.myBeginProject(id);
        return projectsList;
    }
}
