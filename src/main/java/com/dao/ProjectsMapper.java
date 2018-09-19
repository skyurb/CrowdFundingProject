package com.dao;

import com.entity.Projects;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectsMapper {
    int deleteByPrimaryKey(Integer psId);

    int insert(Projects record);

    int insertSelective(Projects record);

    Projects selectByPrimaryKey(Integer psId);

    int updateByPrimaryKeySelective(Projects record);

    int updateByPrimaryKey(Projects record);
    //查询
    //根据种类查询（展示，审核通过）
    List<Projects> selectProjectByType(int type);
    //分页查询全部项目（展示，审核通过）
    List<Projects> selectAllProjectByPage(@Param("first") int startPage,@Param("second") int endPage);
    //我的项目（所有）
    List<Projects> myAllProject(int id);
    //我的项目（进行中）
    List<Projects> myBeginProject(int id);
    //我参与的项目（）
    List<Projects> myJoinProject(int id);
    //我点赞的项目（）
    List<Projects> myLickProject(int id);

    //更新
    //更新项目状态



}