package com.example.demo.mapper;

import com.example.demo.entity.Article;
import com.example.demo.entity.Channel;

import java.util.List;

public interface ArticleMapper {
    int deleteByPrimaryKey(Integer articleId);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer articleId);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);

    List<Channel> selectAllChannel(Integer articleId);

    List<Article> selectAllArticle();
}