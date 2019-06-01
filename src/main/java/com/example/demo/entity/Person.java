package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private Integer personId;

    private String personName;

    private String personPwd;

    private String roleName;

    private List<Access> accesses = new ArrayList<>();

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName == null ? null : personName.trim();
    }

    public String getPersonPwd() {
        return personPwd;
    }

    public void setPersonPwd(String personPwd) {
        this.personPwd = personPwd == null ? null : personPwd.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public List<Access> getAccesses() {
        return accesses;
    }

    public void setAccesses(List<Access> accesses) {
        this.accesses = accesses;
    }
}