package com.example.demo.service;


import com.example.demo.entity.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTest {

    @Autowired
    PersonService service;

    @Test
    public void registerPerson() throws Exception{
        Person newp = new Person();
        newp.setPersonName("老李"+System.currentTimeMillis());
        newp.setPersonPwd("111111");
        service.registerPerson(newp);
    }
}