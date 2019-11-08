package com.qf.mapper;

import com.qf.pojo.User;

import java.util.List;

public interface UserMapper {
    public int deleteUser(int uid);

    public List<User> getUserList();

    public String getUpwdByUname(String uname);

    public String getRoleByUname(String uname);
}
