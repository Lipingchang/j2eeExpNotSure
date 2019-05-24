package com.example.demo.entity;

public class Channel {
    private Integer channelId;

    private String channelTitle;

    private String channelDesc;

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle == null ? null : channelTitle.trim();
    }

    public String getChannelDesc() {
        return channelDesc;
    }

    public void setChannelDesc(String channelDesc) {
        this.channelDesc = channelDesc == null ? null : channelDesc.trim();
    }
}