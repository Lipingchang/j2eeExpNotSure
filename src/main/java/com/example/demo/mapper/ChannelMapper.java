package com.example.demo.mapper;

import com.example.demo.entity.Channel;

import java.util.List;

public interface ChannelMapper {
    int deleteByPrimaryKey(Integer channelId);

    int insert(Channel record);

    int insertSelective(Channel record);

    Channel selectByPrimaryKey(Integer channelId);

    int updateByPrimaryKeySelective(Channel record);

    int updateByPrimaryKey(Channel record);

    List<Channel> selectAll();
}