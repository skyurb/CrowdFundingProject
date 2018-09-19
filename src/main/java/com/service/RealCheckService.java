package com.service;

import com.dao.RealCheckDao;
import com.entity.RealCheckEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
*@ClassName:RealCheckService
 @Description:TODO
 @Author:
 @Date:2018/9/18 15:18 
 @Version:v1.0
*/
@Service
public class RealCheckService {

    @Autowired
    RealCheckDao realCheckDao;

    public int insert(RealCheckEntity realCheckEntity){
        return realCheckDao.insert(realCheckEntity);
    }

}
