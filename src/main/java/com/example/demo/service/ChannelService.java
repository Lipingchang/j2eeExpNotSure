package com.example.demo.service;

import com.example.demo.entity.Channel;
import com.example.demo.mapper.ChannelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelService {
    @Autowired
    ChannelMapper channelMapper;

    public Channel addChannelService(String channelName,String channelDesc){
        Channel channel = new Channel();
        channel.setChannelTitle(channelName);
        channel.setChannelDesc(channelDesc);
        channelMapper.insert(channel);
        return channel;
    }

    public void removeChannel(int id) throws Exception{
        channelMapper.deleteByPrimaryKey(id);
    }

    public List<Channel> getAllChannels() {
        return channelMapper.selectAll();
    }
}
