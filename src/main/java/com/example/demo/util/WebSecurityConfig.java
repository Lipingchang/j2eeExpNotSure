package com.example.demo.util;

import com.example.demo.service.JSONAuthenticationFilter;
import com.example.demo.service.AuthenticationUserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;


import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static String jsonStr = ""; // 未认证的时候返回的字符串

    @Autowired
    private AuthenticationUserDetailsServiceImpl jwtUserDetailsService;

    static {
        CommonResponseData data = new CommonResponseData();
        data.setData(null);
        data.setMsg("未认证");
        data.setStatusCode(401);
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonStr =  mapper.writeValueAsString(data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    // HTTP 请求安全处理
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.authorizeRequests()
                .antMatchers(Constant.URL_HOME,Constant.URL_REGIST).permitAll();
        http.authorizeRequests()
                .anyRequest().authenticated();
        http.formLogin().loginPage("/login").permitAll();
//        http.logout().permitAll();
        http.csrf().disable();
        http.authorizeRequests().anyRequest().authenticated();
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());

        http.addFilterAt(customAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class);
    }

    // 身份验证管理生成器
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.jwtUserDetailsService)
                .passwordEncoder(new CustomPasswordEncoder());
    }

    // Web 安全
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    private AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest httpServletRequest,
                                 HttpServletResponse httpServletResponse,
                                 AuthenticationException e)
                    throws IOException, ServletException {
                httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                httpServletResponse.setStatus(401);
                httpServletResponse.getWriter().append(jsonStr);
            }
        };
    }

    //注册自定义的UsernamePasswordAuthenticationFilter
    @Bean
    JSONAuthenticationFilter customAuthenticationFilter() throws Exception {
        JSONAuthenticationFilter filter = new JSONAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(JSONAuthenticationFilter.createSuccessHandler());
        filter.setAuthenticationFailureHandler(JSONAuthenticationFilter.createFailureHandler());
        filter.setFilterProcessesUrl("/login");

        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}

class CustomPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(charSequence.toString());
    }
}