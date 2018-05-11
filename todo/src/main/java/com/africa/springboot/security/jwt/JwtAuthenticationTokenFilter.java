package com.africa.springboot.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: StefanChoo
 * Date: 2018/5/11
 */
@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;  // Authorization

    @Value("${jwt.tokenHead}")
    private String tokenHead;    // Bearer


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. 从header中取token <Authentication: Bearer jwt_token>
        String authHeader = request.getHeader(this.tokenHeader);
        if(authHeader != null && authHeader.startsWith(tokenHead)) {
            // 2. 去除 "Bearer "，得到 jwt_token
            final String authToken = authHeader.substring(tokenHead.length());
            // 3. 确认用户是否存在
            String username = jwtTokenUtil.getUsernameFromToken(authToken);
            log.info("checking authentication " + username);
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // 如果足够相信 token 的机制的话，可以不用验证
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                // 4. 验证 token
                if(jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    logger.info("authentication user " + username + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
