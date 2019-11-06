package com.baizhi.service.impl;

import com.baizhi.dao.ChapterDAO;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDAO chapterDAO;
    @Override
    public Map<String, Object> findAll(Integer page, Integer rows, String albumId) {
        Map<String, Object> map = new HashMap<>();
        Chapter chapter = new Chapter();
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Chapter> list = chapterDAO.selectByRowBounds(chapter, rowBounds);
        int count = chapterDAO.selectCount(chapter);
        map.put("page",page);
        map.put("rows",list);
        map.put("total",count%rows==0?count/rows:count/rows+1);//总共有几页
        map.put("records",count);//总共有多少条数据
        return map;
    }

    @Override
    public String add(Chapter chapter) {
        chapter.setId(UUID.randomUUID().toString());
        chapter.setCreate_date(new Date());
        int i = chapterDAO.insertSelective(chapter);
        if (i == 0){
            throw new RuntimeException("添加章节失败");
        }
        return chapter.getId();
    }

    @Override
    public void edit(Chapter chapter) {
        int i = chapterDAO.updateByPrimaryKeySelective(chapter);
        if(i == 0){
            throw new RuntimeException("修改章节失败");
        }
    }
}
