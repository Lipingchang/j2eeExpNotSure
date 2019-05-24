package com.example.demo.entity;

public class Person {
    private Integer personId;

    private String personName;

    private String personPwd;

    private String roleName;

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
}