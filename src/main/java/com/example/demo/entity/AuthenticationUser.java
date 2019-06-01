package com.example.demo.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
/*
    用于Spring Security的用户认证, authorities用来指定用户的角色
 */
public class AuthenticationUser implements UserDetails {
    private int id;
    private String username;
    private String password;
    private String rolename;
    private Collection<? extends GrantedAuthority> authorities;

    public AuthenticationUser(
            int iid,
            String uusername,
            String ppassword,
            String rrolename,
            Collection<? extends GrantedAuthority> aauthorities
            ){
        id = iid;
        username = uusername;
        password = ppassword;
        rolename = rrolename;
        authorities = aauthorities;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public int getId() {
        return id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}
