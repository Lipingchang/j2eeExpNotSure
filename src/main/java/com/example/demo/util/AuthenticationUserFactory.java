package com.example.demo.util;

import com.example.demo.entity.Access;
import com.example.demo.entity.AuthenticationUser;
import com.example.demo.entity.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/*
    把Person对象转换成Spring Boot Security识别的AuthenticatonUser
 */
public class AuthenticationUserFactory {
    private AuthenticationUserFactory(){
    }
    public static AuthenticationUser create(Person person){
        return new AuthenticationUser(
                person.getPersonId(),
                person.getPersonName(),
                person.getPersonPwd(),
                person.getRoleName(),
                mapToGrantedAuthorities(person.getAccesses())
        );
    }
    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Access> authorities) {
        // 流处理方式，map出Access的名字，map出SimpleGrantedAuthority，最后转化成一个list 返回
        return authorities.stream()
                .map(Access::getAccessName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
