package com.africa.springboot.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

/**
 * Author: StefanChoo
 * Date: 2018/5/11
 */
@Slf4j
@Component
public class JwtTokenUtil {

    @Value("${jwt.expiration}")
    private long expiration;       // unit: s

    @Value("${jwt.secret}")
    private String secret;


    public String generateToken(UserDetails user) {

        //     claims
        //     setSubject(user.getUsername())
        //     setIssuedAt(new Date())
        //     setExpiration(generateExpirationDate())

        return Jwts.builder()
                .setClaims(new HashMap())
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private Boolean isTokenExpired(String authToken) {
        return getExpirationDate(authToken).before(new Date());
    }

    private Date generateExpirationDate() {
        long now = new Date().getTime();
        return new Date(now + 1000 * expiration);
    }

    public String getUsernameFromToken(String authToken) {
        log.info(getAllClaimsFromToken(authToken).getSubject());
        return getAllClaimsFromToken(authToken).getSubject();
    }

    public Date getIssuedAtDateFromToken(String authToken) {
        return getAllClaimsFromToken(authToken).getIssuedAt();
    }

    private Date getExpirationDate(String authToken) {
        return getAllClaimsFromToken(authToken).getExpiration();
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Claims getAllClaimsFromToken(String authToken) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(authToken)
                .getBody();

    }

    public boolean validateToken(String authToken, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        final String username = getUsernameFromToken(authToken);
        final Date created = getIssuedAtDateFromToken(authToken);

        return username.equals(user.getUsername())
                && !isTokenExpired(authToken)
                && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate());

    }

    public boolean canTokenBeRefreshed(String authToken, Date lastPasswordResetDate) {
        final Date created = getIssuedAtDateFromToken(authToken);
        return !isTokenExpired(authToken)
                && !isCreatedBeforeLastPasswordReset(created, lastPasswordResetDate);
    }

    public String refreshToken(String authToken) {
        final Claims claims = getAllClaimsFromToken(authToken);
        claims.setIssuedAt(new Date());
        claims.setExpiration(generateExpirationDate());
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
