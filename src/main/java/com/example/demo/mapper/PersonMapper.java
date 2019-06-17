package com.example.demo.mapper;

import com.example.demo.entity.Access;
import com.example.demo.entity.Person;

import java.util.List;

public interface PersonMapper {
    int deleteByPrimaryKey(Integer personId);

    int insert(Person record);

    int insertSelective(Person record);

    Person selectByPrimaryKey(Integer personId);

    int updateByPrimaryKeySelective(Person record);

    int updateByPrimaryKey(Person record);

    List<Access> selectAllAccess(int personId);

    Integer insertAccess(int personId, List<Access> accessList);  // 没有返回值 很奇怪
    Integer deleteAccess(int personId);

    Person selectByName(String name);
    List<Person> selectAllPerson();
}