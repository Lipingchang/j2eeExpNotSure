package com.example.demo.service;

import com.example.demo.entity.Article;
import com.example.demo.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleService {
    @Autowired
    ArticleMapper articleMapper;

    public List<Article> getArticles(){
        return articleMapper.selectAllArticle();
    }
    public Article getArticle(int id){
        return articleMapper.selectByPrimaryKey(id);
    }
    public int addArticles(Article article){
        articleMapper.insert(article);
        return article.getArticleId();
    }

    @Transactional(rollbackFor = {Exception.class})
    public void delArticle(int id){
        articleMapper.deleteByPrimaryKeyChannelRelate(id);
        articleMapper.deleteByPrimaryKey(id);
    }
}
