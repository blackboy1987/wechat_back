package com.igomall.config;

import com.igomall.audit.AuditLogMethodArgumentResolver;
import com.igomall.entity.Admin;
import com.igomall.entity.member.Member;
import com.igomall.interceptor.AdminLoginInterceptor;
import com.igomall.interceptor.CorsInterceptor;
import com.igomall.interceptor.LoginInterceptor;
import com.igomall.security.CurrentUserHandlerInterceptor;
import com.igomall.security.CurrentUserMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public CorsInterceptor corsInterceptor() {
        CorsInterceptor corsInterceptor = new CorsInterceptor();
        return corsInterceptor;
    }

    @Bean
    public LoginInterceptor loginInterceptor() {
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        return loginInterceptor;
    }

    @Bean
    public AdminLoginInterceptor adminLoginInterceptor() {
        AdminLoginInterceptor adminLoginInterceptor = new AdminLoginInterceptor();
        return adminLoginInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(corsInterceptor())
                .addPathPatterns("/**");

        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/api/member/**")
                .excludePathPatterns("/api/member/login","/api/member/register/**","/api/member/logout");

       /* registry.addInterceptor(adminLoginInterceptor())
                .addPathPatterns("/api/admin/**")
                .excludePathPatterns("/api/admin/login","/api/admin/logout");


        registry.addInterceptor(currentUserHandlerInterceptor())
                .addPathPatterns("/api/admin/**");*/
        registry.addInterceptor(currentUserHandlerInterceptor1())
                .addPathPatterns("/api/member/**");

    }


    @Bean
    public FixedLocaleResolver localeResolver(){
        FixedLocaleResolver localeResolver = new FixedLocaleResolver();
        return localeResolver;
    }

    @Bean
    public CurrentUserHandlerInterceptor currentUserHandlerInterceptor() {
        CurrentUserHandlerInterceptor currentUserHandlerInterceptor = new CurrentUserHandlerInterceptor();
        currentUserHandlerInterceptor.setUserClass(Admin.class);
        return currentUserHandlerInterceptor;
    }

    @Bean
    public CurrentUserHandlerInterceptor currentUserHandlerInterceptor1() {
        CurrentUserHandlerInterceptor currentUserHandlerInterceptor = new CurrentUserHandlerInterceptor();
        currentUserHandlerInterceptor.setUserClass(Member.class);
        return currentUserHandlerInterceptor;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        List<HandlerMethodArgumentResolver> handlerMethodArgumentResolvers = new ArrayList<>();
        handlerMethodArgumentResolvers.add(currentUserMethodArgumentResolver());
        handlerMethodArgumentResolvers.add(auditLogMethodArgumentResolver());
        resolvers.addAll(handlerMethodArgumentResolvers);
    }

    @Bean
    public CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver(){

        return new CurrentUserMethodArgumentResolver();
    }

    @Bean
    public AuditLogMethodArgumentResolver auditLogMethodArgumentResolver(){

        return new AuditLogMethodArgumentResolver();
    }

}
