package com.z2py.dao;

import com.z2py.model.Admin;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Mr.Xu on 2017/4/8.
 */
public interface AdminDao {

    @Select("select * from tb_admin where username=#{0}")
    Admin findByUsername(String username) throws Exception;
}
