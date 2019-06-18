package com.example.demo.service;

import com.example.demo.entity.Article;
import com.example.demo.entity.Channel;
import com.example.demo.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ArticleService {
    @Autowired
    ArticleMapper articleMapper;

    public List<Map<String,Object>> getArticles(){
        List<Map<String,Object>> ret = articleMapper.selectAllArticleWithOutContent();
        for( Map<String,Object> map : ret){
            map.put( "channelIds", articleMapper.selectAllChannel(
                    (Integer)map.get("article_id"))
                    .stream()
                    .map((Channel channel)->{return channel.getChannelId();}) );
        }
        return ret;
    }
    public Article getArticle(int id){
        return articleMapper.selectByPrimaryKey(id);
    }

    @Transactional(rollbackFor = {Exception.class})
    public int addArticles(Article article, List<Integer> channelIds ){
        articleMapper.insert(article);
        if ( channelIds.size() != 0 )
            articleMapper.insertChannelId(article.getArticleId(),channelIds);
        else
            System.out.println("channelid list is null!!");

        return article.getArticleId();
    }

    @Transactional(rollbackFor = {Exception.class})
    public void delArticle(int id){
        articleMapper.deleteByPrimaryKeyChannelRelate(id);
        articleMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void updateArticle(Article article, List<Integer> channelIds){
        articleMapper.updateByPrimaryKey(article);
        articleMapper.deleteAllChannels(article.getArticleId());
        if ( channelIds.size()!= 0)
            articleMapper.insertChannelId(article.getArticleId(),channelIds);
    }
}
