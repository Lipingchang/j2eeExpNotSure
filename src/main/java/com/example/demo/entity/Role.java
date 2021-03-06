package com.example.demo.entity;

import com.example.demo.mapper.AccessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("Role")
public class Role {
    public final static String[] names = {"管理员","编辑","质检员","游客" };
    public static Access accessControl;
    public static Access addArticleControl;
    public static Access editorArticleControl;
    public static Access deleteArticleControl;
    public static Access readControl;
    public static Access channelControl;
    private static Access[][] defaultAccess;

    public static Role Administrator;
    public static Role Editor;
    public static Role Checker;
    public static Role Visitor;

    @Autowired
    AccessMapper accessDao;

    @PostConstruct
    void init(){
        accessControl = accessDao.selectByPrimaryKey(1);
        addArticleControl = accessDao.selectByPrimaryKey(2);
        editorArticleControl = accessDao.selectByPrimaryKey(3);
        deleteArticleControl = accessDao.selectByPrimaryKey(4);
        readControl = accessDao.selectByPrimaryKey(5);
        channelControl = accessDao.selectByPrimaryKey(6);
        defaultAccess = new Access[][]{
                {accessControl,channelControl},
                {addArticleControl,editorArticleControl,deleteArticleControl,readControl},
                {editorArticleControl,readControl},
                {readControl}
        };
        Administrator = new Role( Role.names[0], 0);
        Editor = new Role( Role.names[1],1);
        Checker = new Role( Role.names[2],2);
        Visitor = new Role( Role.names[3],3);
    }




    public String name;
    public Access[] access;
    private Role(String nn ,int ii){
        name = nn;
        access = defaultAccess[ii];
    }
    public Role(){
    }

}

