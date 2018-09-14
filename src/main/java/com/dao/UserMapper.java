package com.dao;

import com.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer usId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer usId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    //根据手机号和密码获取用户
    List<User> selectByPhoneAndPwd(@Param("param1")String name, @Param("param2")String password);

    //根据邮箱号和密码获取用户
    List<User> selectByEmailAndPwd(@Param("param1")String name, @Param("param2")String password);
}