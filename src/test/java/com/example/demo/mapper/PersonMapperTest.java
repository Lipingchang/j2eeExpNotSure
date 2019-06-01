package com.example.demo.mapper;

import com.example.demo.entity.Access;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonMapperTest {
    @Autowired
    PersonMapper personMapper;

    @Test
    public void kk(){
        List<Access> k = personMapper.selectAllAccess(45);
        for ( Access acc : k ){
            System.out.println(acc.getAccessDecs());
        }

    }
}