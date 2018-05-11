package com.africa.springboot.controller;

import com.africa.springboot.domain.User;
import com.africa.springboot.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Author: StefanChoo
 * Date: 2018/5/11
 */
@RestController
public class AuthController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthService authService;

    @PostMapping("${jwt.route.authentication.path}")
    public ResponseEntity<JwtAuthenticationResponse> createAuthenticationToken(@RequestBody JwtAuthenticationRequest request)
            throws AuthenticationException {
        final String token = authService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @GetMapping("${jwt.route.authentication.refresh}")
    public ResponseEntity<JwtAuthenticationResponse> refreshAndGetAuthenticationToken(HttpServletRequest request)
            throws AuthenticationException {
        final String token = request.getHeader(tokenHeader);
        final String refreshToken = authService.refresh(token);
        return Optional.ofNullable(refreshToken)
                .map(r -> ResponseEntity.ok(new JwtAuthenticationResponse(r)))
                .orElse(ResponseEntity.badRequest().body(null));
    }

    @PostMapping("${jwt.route.authentication.register}")
    public ResponseEntity<User> register(@RequestBody User user) throws AuthenticationException {
        return ResponseEntity.ok(authService.register(user));
    }
}
