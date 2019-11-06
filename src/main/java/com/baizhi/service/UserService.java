package com.baizhi.service;


import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String,Object> queryAll(Integer page,Integer rows,String starId);
    void edit(User user);
    List<User> exprot();
    //柱状图
    Map<String,List<Integer>> selectReg();
}
