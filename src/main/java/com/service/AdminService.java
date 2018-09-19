package com.service;

import com.dao.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    AdminDao adminDao;
    public List<String> findRoleByAdminId(int id){
        List<String> roles = adminDao.findRoleByAdminId(id);
        return roles;
    }
}
