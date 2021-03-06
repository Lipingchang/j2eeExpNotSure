package com.example.demo.mapper;

import com.example.demo.entity.Article;
import com.example.demo.entity.Channel;

import java.util.List;
import java.util.Map;

public interface ArticleMapper {
    int deleteByPrimaryKey(Integer articleId);
    int deleteByPrimaryKeyChannelRelate(Integer articleId);

    int insert(Article record);
    int insertChannelId(Integer articleId, List<Integer> channelIds);
    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer articleId);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);

    List<Channel> selectAllChannel(Integer articleId);

    List<Article> selectAllArticle();
    List<Map<String,Object>> selectAllArticleWithOutContent();
    void deleteAllChannels(Integer articleId);
}