package com.sri.Filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sri.utils.JSONWebTokenUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTRequestFilter extends OncePerRequestFilter{

	@Autowired
	JSONWebTokenUtil jwtUtil;
	
	 
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
  		
		 Cookie[] cookies = request.getCookies();
	        if (cookies != null) {
	            for (Cookie cookie : cookies) {
	                if (cookie.getName().equals("Authorization")) {
	                    String token = cookie.getValue();
  	                   if(token!=null&&SecurityContextHolder.getContext().getAuthentication()==null) {
  	                	   try {
 	        				if(jwtUtil.isValidToken(token)) {
  	        					String username=jwtUtil.getUsername(token);
	        					UsernamePasswordAuthenticationToken AuthToken=new UsernamePasswordAuthenticationToken(username, null,jwtUtil.getAuthorities(token));
	        					AuthToken.setDetails(new WebAuthenticationDetails(request));
	        					SecurityContextHolder.getContext().setAuthentication(AuthToken);
	        				}
 	                	  } catch (Exception e) {
 								System.out.println("Token Expired");
 						  }
	        			}
	                    
	                }
	            }
	        }
 		filterChain.doFilter(request, response);
 	}

}









