package com.sri.utils;

import java.util.Date;
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
	EmployeeService empSer;

	@Value("${JWT.SecurityKey}")
	private String securityKey;
	
	@Value("${JWT.ExpiryTime}")
	private long expiryTime;
	
	public String generateToken(String username,Map<String,Object> map) {
		return Jwts.builder()
				.addClaims(map)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+expiryTime))
				.signWith(SignatureAlgorithm.HS256,securityKey.getBytes())
				.compact();
	}
	
	public Claims getClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(securityKey.getBytes())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}
	public Set<String> getRoles(String token){
		return (Set<String>) getClaims(token).get("Roles");
	}
	public Set<SimpleGrantedAuthority> getAuthorities(String token){
		return (Set<SimpleGrantedAuthority>) getRoles(token).stream().map(role->new SimpleGrantedAuthority(role)).collect(Collectors.toSet());
	}
	
	public String getName(String token){
		return getClaims(token).get("Name", String.class);
	}
	public Date getExpiryTime(String token) {
		return getClaims(token).getExpiration();
	}
	public boolean isTokenExpired(String token) {
		return  getExpiryTime(token).before(new Date());
	}
	public boolean isValidToken(String token) {
		return (empSer.isEmployeeExist(getUsername(token))&&!isTokenExpired(token));
	}
	
	
 	
	
}
