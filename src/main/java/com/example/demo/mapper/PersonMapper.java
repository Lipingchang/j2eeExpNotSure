package com.example.demo.mapper;

import com.example.demo.entity.Person;

public interface PersonMapper {
    int deleteByPrimaryKey(Integer personId);

    int insert(Person record);

    int insertSelective(Person record);

    Person selectByPrimaryKey(Integer personId);

    int updateByPrimaryKeySelective(Person record);

    int updateByPrimaryKey(Person record);
}