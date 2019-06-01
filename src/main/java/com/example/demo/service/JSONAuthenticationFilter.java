package com.example.demo.service;

import com.example.demo.entity.AuthenticationUser;
import com.example.demo.mapper.PersonMapper;
import com.example.demo.util.CommonResponseData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class JSONAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest = null;

        // 当request的content-type是json的时候
        if(request.getContentType()!=null &&
                ( request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                ||request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE) )){
            logger.info("接收到JSON的登陆请求");

            ObjectMapper mapper = new ObjectMapper();
            try (InputStream is = request.getInputStream()){
                // 把body中的数据 json 化成 对象 AuthenticationBean
                AuthenticationBean authenticationBean = mapper.readValue(is,AuthenticationBean.class);
                // 根据获得的 json 数据，生成认证用户的请求，
                authRequest = new UsernamePasswordAuthenticationToken(
                        authenticationBean.getUsername(), authenticationBean.getPassword());
                logger.info("JSON的登陆请求: username:"+authenticationBean.getUsername()+",password:"+authenticationBean.getPassword());
            }catch (IOException e) {
                logger.info("JSON的登陆请求解析失败{username,password}");
                // 出错的时候，随便生成一个认证请求
                authRequest = new UsernamePasswordAuthenticationToken("", "");
            }
        }
        // 把 认证请求 提交给AuthenticationManager对象，然后让该对象认证这个请求，返回认证结果
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);

    }

    public static AuthenticationSuccessHandler createSuccessHandler(){
        CommonResponseData retdata = new CommonResponseData();
        retdata.setMsg("认证成功");
        retdata.setStatusCode(200);
        ObjectMapper mapper = new ObjectMapper();

        AuthenticationSuccessHandler ret = new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                AuthenticationUser user = (AuthenticationUser)authentication.getPrincipal();
                user.setPassword("");
                retdata.setData(user);
                response.getWriter().print( mapper.writeValueAsString(retdata) );
            }
        };
        return ret;
    }

    public static AuthenticationFailureHandler createFailureHandler(){
        CommonResponseData retdata = new CommonResponseData();
        retdata.setMsg("认证失败");
        retdata.setStatusCode(500);
        retdata.setData(null);
        ObjectMapper mapper = new ObjectMapper();

        AuthenticationFailureHandler ret = new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                response.setStatus(200);
                response.getWriter().print(mapper.writeValueAsString(retdata));
            }
        };
        return ret;
    }
}

// 解析JSON包中的用户名和密码
class AuthenticationBean{
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

