package com.example.demo;

import com.example.demo.entity.Access;
import com.example.demo.mapper.AccessMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	AccessMapper accessMapper;

	@Test
	public void contextLoads() {
		Access access = accessMapper.selectByPrimaryKey(1);
		System.out.println(access.getAccessName());
		System.out.println(access.getAccessDecs());
	}

}
