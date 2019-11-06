package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("queryAll")
    public Map<String,Object> queryAll(Integer page,Integer rows,String starId){
        Map<String, Object> map = userService.queryAll(page, rows, starId);
        return map;
    }
    @RequestMapping("upload")
    public Map<String,Object> upload(MultipartFile photo, String id, HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        try {
            photo.transferTo(new File(request.getServletContext().getRealPath("/back/img/"),photo.getOriginalFilename()));
            User user = new User();
            user.setId(id);
            user.setPhoto(photo.getOriginalFilename());
            userService.edit(user);
            map.put("status",true);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return map;
    }
        @RequestMapping("export")
    public void export(HttpServletResponse response){
        //准备数据
        List<User> list = userService.exprot();
            for (User user : list) {
                user.setPhoto("D:/Intellij IDEA/project/cmfz_w/src/main/webapp/back/img/"+user.getPhoto());
            }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"), User.class, list);
        String fileName = "用户报表("+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+").xls";
        //处理中文下载名乱码
        try {
            fileName = new String(fileName.getBytes("gbk"),"iso-8859-1");
            //设置 response
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-disposition","attachment;filename="+fileName);
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("selectReg")
    public Map<String,List<Integer>> selectReg(){
        Map<String, List<Integer>> map = userService.selectReg();
        return map;
    }
}
