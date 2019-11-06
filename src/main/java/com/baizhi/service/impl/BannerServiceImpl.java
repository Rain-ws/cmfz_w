package com.baizhi.service.impl;

import com.baizhi.dao.BannerDAO;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerDAO bannerDAO;

    @Override
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        Banner banner = new Banner();
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Banner> list = bannerDAO.selectByRowBounds(banner, rowBounds);
        int count = bannerDAO.selectCount(banner);

        Map<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",list);
        map.put("total",count%rows==0?count/rows:count/rows+1);//总共有几页
        map.put("records",count);//总共有多少条数据

        return map;
    }

    @Override
    public String add(Banner banner) {
        banner.setId(UUID.randomUUID().toString());
        banner.setCreate_date(new Date());
        int i = bannerDAO.insertSelective(banner);
        if(i == 0){
            throw new RuntimeException("添加失败");
        }
        return banner.getId();
    }

    @Override
    public void edit(Banner banner) {
        if("".equals(banner.getCover())){
            banner.setCover(null);
        }
        try {
            bannerDAO.updateByPrimaryKeySelective(banner);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }
    }

    @Override
    public void del(String id, HttpServletRequest request) {
        Banner banner = bannerDAO.selectByPrimaryKey(id);
        int i = bannerDAO.deleteByPrimaryKey(id);
        if(i == 0){
            throw new RuntimeException("删除失败");
        }else{
            String cover = banner.getCover();
            File file = new File(request.getServletContext().getRealPath("/back/img/"), cover);
            boolean b = file.delete();
            if(b == false){
                throw new RuntimeException("删除轮播图失败");
            }
        }
    }
}












