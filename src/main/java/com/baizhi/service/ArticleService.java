package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.Map;

public interface ArticleService {
    Map<String,Object> findAll(Integer page,Integer rows);
    String add(Article article);
    void edit(Article article);
    void del(String id);
}
