package com.sri.utils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.sri.Service.EmployeeService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JSONWebTokenUtil {

    @Autowired
    private EmployeeService empSer;

    @Value("${JWT.SecurityKey}")
    private String securityKey;

    @Value("${JWT.ExpiryTime}")
    private long expiryTime;

    // Generate JWT token with username and claims
    public String generateToken(String username, Map<String, Object> claims) {
        return Jwts.builder()
                .addClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiryTime))
                .signWith(SignatureAlgorithm.HS256, securityKey.getBytes())
                .compact();
    }

    // Retrieve claims from the token
    public Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(securityKey.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new RuntimeException("Invalid token");
        }
    }

    // Get username from the token
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Get roles from the token
    public List<String> getRoles(String token) {
        Object roles = getClaims(token).get("ROLES");
        if (roles instanceof List) {
            return (List<String>) roles;
        }
        return List.of(); // Return an empty list if roles are not present or not a List
    }

    // Get authorities from roles
    public Set<SimpleGrantedAuthority> getAuthorities(String token) {
		return (Set<SimpleGrantedAuthority>) getRoles(token).stream().map(role->new SimpleGrantedAuthority(role)).collect(Collectors.toSet());

    }

    // Get name from the token
    public String getName(String token) {
        return getClaims(token).get("Name", String.class);
    }

    // Get token expiry time
    public Date getExpiryTime(String token) {
        return getClaims(token).getExpiration();
    }

    // Check if the token is expired
    public boolean isTokenExpired(String token) {
        return getExpiryTime(token).before(new Date());
    }

    // Validate the token
    public boolean isValidToken(String token) {
        return empSer.isEmployeeExist(getUsername(token)) && !isTokenExpired(token);
    }
}
