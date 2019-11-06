package com.baizhi.service.impl;

import com.baizhi.dao.ArticleDAO;
import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDAO articleDAO;

    @Override
    public Map<String, Object> findAll(Integer page, Integer rows) {
        Article article = new Article();
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Article> list = articleDAO.selectByRowBounds(article, rowBounds);
        int count = articleDAO.selectCount(article);
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rows", list);
        map.put("total", count % rows == 0 ? count / rows : count / rows + 1);
        map.put("records", count);
        return map;
    }

    @Override
    public String add(Article article) {
        article.setId(UUID.randomUUID().toString());
        article.setCreate_date(new Date());
        int i = articleDAO.insertSelective(article);
        if (i == 0) {
            throw new RuntimeException("添加失败");
        }
        return article.getId();
    }

    @Override
    public void edit(Article article) {
        if("".equals(article.getCreate_date())){
            article.setCreate_date(null);
        }
        try {
            articleDAO.updateByPrimaryKeySelective(article);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }
    }

    @Override
    public void del(String id) {
        int i = articleDAO.deleteByPrimaryKey(id);
        if (i == 0){
            throw new RuntimeException("删除失败");
        }
    }
}
