package com.baizhi.controller;

import com.baizhi.util.SecurityCode;
import com.baizhi.util.SecurityImage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 验证码
 */
@RequestMapping("code")
@Controller
public class SecurityController {
    @RequestMapping("getCode")
    public void getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String securityCode = SecurityCode.getSecurityCode();
        System.out.println(securityCode);
        //获取session存入验证码
        HttpSession session = request.getSession();
        session.setAttribute("securityCode",securityCode);
        //生成图片
        BufferedImage image = SecurityImage.createImage(securityCode);
        response.setContentType("image/png");//设置响应类型
        //响应图片
        ImageIO.write(image,"png",response.getOutputStream());
    }
}
