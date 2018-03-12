package com.z2py.service;

import com.z2py.dao.AdminDao;
import com.z2py.model.Admin;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Shinelon on 2017/4/8.
 */
@Service
public class AdminService {

    @Resource
    private AdminDao adminDao;

    public Admin findByUsername(String username) throws Exception {
        return adminDao.findByUsername(username);
    }
}
