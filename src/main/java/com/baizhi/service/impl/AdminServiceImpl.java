package com.baizhi.service.impl;

import com.baizhi.dao.AdminDAO;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDAO adminDAO;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void login(Admin admin, String inputCode, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String securityCode = (String) session.getAttribute("securityCode");//验证码
        if (securityCode.equalsIgnoreCase(inputCode)){
            Admin admin1 = adminDAO.selectOne(admin);
            if (admin1 != null){
                session.setAttribute("admin1",admin1);
            }else{
                throw new RuntimeException("用户名错误");
            }
        }else {
            throw new RuntimeException("验证码错误");
        }
    }
}
