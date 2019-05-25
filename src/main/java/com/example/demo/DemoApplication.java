package com.example.demo;

import com.example.demo.entity.Access;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan(basePackages = {"com.example.demo.mapper"})
public class DemoApplication {
	static Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
