package com.sri.Filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sri.Service.EmployeeService;
import com.sri.utils.JSONWebTokenUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTRequestFilter extends OncePerRequestFilter{

	@Autowired
	JSONWebTokenUtil jwtUtil;
	
	@Autowired
	EmployeeService empSer;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		final String AuthorizationHeader=request.getHeader("Authorization");
		
		if(AuthorizationHeader!=null&&AuthorizationHeader.startsWith("Bearer ")) {
			String token=AuthorizationHeader.substring(7);
			String username=jwtUtil.getUsername(token);
			if(token!=null&&SecurityContextHolder.getContext().getAuthentication()==null) {
				UserDetails userDetails=empSer.loadUserByUsername(username);
				if(jwtUtil.isValidToken(token)) {
					UsernamePasswordAuthenticationToken AuthToken=new UsernamePasswordAuthenticationToken(username, null,jwtUtil.getAuthorities(token));
					AuthToken.setDetails(new WebAuthenticationDetails(request));
					SecurityContextHolder.getContext().setAuthentication(AuthToken);
				}
			}
			
			
		}
		filterChain.doFilter(request, response);
	}

}










