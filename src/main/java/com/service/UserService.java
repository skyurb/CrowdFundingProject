package com.service;

import com.dao.UserMapper;
import com.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    public List<User> selectByPhoneAndPwd(String name,String password){
        List<User> users = userMapper.selectByPhoneAndPwd(name, password);
        return users;
    }

    public List<User> selectByEnailAndPwd(String name,String password){
        List<User> users = userMapper.selectByEmailAndPwd(name, password);
        return users;
    }

    public List<User> findByEmailName(String userName){
        List<User> users = userMapper.findByEmailName(userName);
        return users;
    }

    public List<User> findByPhone(String userName){
        List<User> users = userMapper.findByPhone(userName);
        return users;
    }

    public int insert(User user){
        int insert = userMapper.insert(user);
        return insert;
    }

}
