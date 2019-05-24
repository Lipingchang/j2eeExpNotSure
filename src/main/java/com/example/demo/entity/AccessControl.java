package com.example.demo.entity;

public class AccessControl {
    private Integer accessId;

    private String accessName;

    private String accessDecs;

    public Integer getAccessId() {
        return accessId;
    }

    public void setAccessId(Integer accessId) {
        this.accessId = accessId;
    }

    public String getAccessName() {
        return accessName;
    }

    public void setAccessName(String accessName) {
        this.accessName = accessName == null ? null : accessName.trim();
    }

    public String getAccessDecs() {
        return accessDecs;
    }

    public void setAccessDecs(String accessDecs) {
        this.accessDecs = accessDecs == null ? null : accessDecs.trim();
    }
}