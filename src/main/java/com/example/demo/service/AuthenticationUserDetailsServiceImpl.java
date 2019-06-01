package com.example.demo.service;

import com.example.demo.entity.Person;
import com.example.demo.mapper.AccessMapper;
import com.example.demo.mapper.PersonMapper;
import com.example.demo.util.AuthenticationUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    PersonMapper personMapper;
    @Autowired
    AccessMapper accessMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查找用户
        Person p = personMapper.selectByName(username);
        // 查找用户的权限
        p.setAccesses(personMapper.selectAllAccess(p.getPersonId()));
        if (p == null) {
            System.out.println("user not found");
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }else {
            return AuthenticationUserFactory.create(p);
        }
    }
}
