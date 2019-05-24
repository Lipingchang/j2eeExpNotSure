package com.example.demo.mapper;

import com.example.demo.entity.AccessControl;

public interface AccessControlMapper {
    int deleteByPrimaryKey(Integer accessId);

    int insert(AccessControl record);

    int insertSelective(AccessControl record);

    AccessControl selectByPrimaryKey(Integer accessId);

    int updateByPrimaryKeySelective(AccessControl record);

    int updateByPrimaryKey(AccessControl record);
}