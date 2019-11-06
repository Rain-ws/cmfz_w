package com.baizhi.service.impl;

import com.baizhi.dao.UserDAO;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryAll(Integer page, Integer rows, String starId) {
        User user = new User();
        user.setStarId(starId);
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<User> list = userDAO.selectByRowBounds(user, rowBounds);
        int count = userDAO.selectCount(user);
        HashMap<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",list);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("records",count);
        return map;
    }

    @Override
    public void edit(User user) {
        int i = userDAO.deleteByPrimaryKey(user);
        if (i == 0){
            throw new RuntimeException("删除失败");
        }
    }

    @Override
    public List<User> exprot() {
        return userDAO.selectAll();
    }

    @Override
    public Map<String, List<Integer>> selectReg() {
        Map<String, List<Integer>> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        for (int i=1;i<13;i++){
            Integer reg = userDAO.selectReg("男", i);
            list.add(reg);
        }
        map.put("nan",list);
        List<Integer> list1 = new ArrayList<>();
        for (int i=1;i<13;i++){
            Integer reg = userDAO.selectReg("女", i);
            list1.add(reg);
        }
        map.put("nv",list1);
        return map;
    }
}
