package com.example.demo.service;

import com.example.demo.entity.Access;
import com.example.demo.entity.Person;
import com.example.demo.entity.Role;
import com.example.demo.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.util.Tools.strIsEmpty;

@Service
public class PersonService {
    @Autowired
    PersonMapper personDao;

    @Transactional(rollbackFor = {Exception.class}) // TODO 要配置类才可以回滚，跟网上说的不一样
    public void registerPerson(Person newP) throws Exception{
        if ( strIsEmpty( newP.getPersonName() ) ){
            throw new Exception("名字不能为空");
        }
        if ( strIsEmpty(newP.getPersonPwd() ) || newP.getPersonPwd().length()<4 ){
            throw new Exception("密码不符合要求");
        }
        if ( personDao.selectByName(newP.getPersonName()) != null ){
            throw new Exception("用户名已存在");
        }

        // 默认设置为游客
        try {
            newP.setRoleName(Role.Visitor.name);
            newP.setAccesses(Arrays.asList(Role.Visitor.access));
            personDao.insert(newP);
            personDao.insertAccess(newP.getPersonId(), Arrays.asList(Role.Visitor.access));
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("数据库错误");
        }
    }

    public void changePassword(int personID, String newpassword) throws Exception {
        if (personID<=0 || strIsEmpty( newpassword ) ){
            throw new Exception("参数不能空");
        }
        Person newp = new Person();
        newp.setPersonId(personID);
        newp.setPersonPwd(newpassword);
        personDao.updateByPrimaryKeySelective(newp);
    }

    public void changeAccess(int personID, List<Integer> accessList){
        List<Access> list = accessList.stream()
                .filter(id -> (id < 1 || id > 6) )
                .map( (id)->{ Access k = new Access(); k.setAccessId(id); return k;} )
                .collect(Collectors.toList());
        accessList.forEach(System.out::println);
        personDao.deleteAccess(personID);
        personDao.insertAccess(personID,list);

    }
}
