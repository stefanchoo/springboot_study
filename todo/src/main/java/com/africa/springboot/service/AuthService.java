package com.africa.springboot.service;

import com.africa.springboot.domain.User;

/**
 * Author: StefanChoo
 * Date: 2018/5/11
 */
public interface AuthService {
    User register(User user);
    String login(String username, String password);
    String refresh(String oldToken);
}
