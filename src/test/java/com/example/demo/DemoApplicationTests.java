package com.example.demo;

import com.example.demo.entity.*;
import com.example.demo.mapper.AccessMapper;
import com.example.demo.mapper.ArticleMapper;
import com.example.demo.mapper.PersonMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	AccessMapper accessMapper;
	@Autowired
	PersonMapper personMapper;
	@Autowired
	ArticleMapper articleMapper;
	@Test
	public void contextLoads() {
		// 测试 mabatis 映射关系
		Access access = accessMapper.selectByPrimaryKey(1);
		System.out.println(access.getAccessName());
		System.out.println(access.getAccessDecs());

		// 插入
		Person np = new Person();
		np.setPersonName("康师傅"+System.currentTimeMillis());
		np.setPersonPwd("ksf");
		int k = personMapper.insert(np);
		System.out.println(np.getPersonId());

		// 插入权限
		List<Access> list = Arrays.asList(Role.Editor.access);
		Integer ggg = personMapper.insertAccess(np.getPersonId(),list);
		System.out.println(ggg);
		System.out.println(np.getPersonId());

// 		// 读取权限
//		personMapper.selectAllAccess(1);
//		List<Integer> ret = personMapper.selectAllAccess(1);
//		System.out.println(ret);

		List<Channel> rret = articleMapper.selectAllChannel(1);
		System.out.println(rret);
	}

}
