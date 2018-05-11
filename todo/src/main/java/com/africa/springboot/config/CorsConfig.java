package com.africa.springboot.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.FilterRegistration;

/**
 * Author: StefanChoo
 * Date: 2018/5/11
 * <p>
 * 跨域请求 ( cross-origin HTTP request )
 * 早期解决方案为 JSONP
 * 现在为 CORS (Cross-Origin Resource Sharing)
 * <p>
 * 1. 请求方先发一个 OPTION（不改变任何资源） 请求，包含这次请求需要干什么
 * 2. 接收方预检查这次请求的目的，同意后给出响应
 * 3. 如果同意，请求方再发出实际的请求
 * 4. 接收方响应，结束
 */
@Configuration
public class CorsConfig {
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置你要允许的网站域名，如果全允许则设为 *
        // "http://localhost:4000"
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));

        // 顺序很重要, 为避免麻烦，设置在前
        bean.setOrder(0);
        return bean;
    }
}
