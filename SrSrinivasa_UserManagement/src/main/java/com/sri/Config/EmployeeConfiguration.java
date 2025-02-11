package com.sri.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sri.Filters.JWTRequestFilter;
import com.sri.Service.EmployeeService;

@Configuration
@EnableWebSecurity
public class EmployeeConfiguration {

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JWTRequestFilter jwtFilter;
    
    @Bean
     AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
        		http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(employeeService)
            						.passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }
	@Bean
    SecurityFilterChain  filterChains(HttpSecurity http) throws Exception {
    	http.authorizeHttpRequests(req-> 
    		req.requestMatchers("/employee/login","/images/**","/css/**","/employee/error-403"
    				,"/employee/user/forgotPassword","/employee/user/updateForgotPassword","/employee/user/updatePassword","/employee/user/forgotPasswordPage").permitAll()
    		.requestMatchers("/employee/hr/addToDeleteEmp","/employee/hr/changeRoleandDprt/**","/employee/hr//EmployeeList/download","/employee/hr/EmployeeList","/employee/hr/profile","/employee/hr/updateEmp"
    				,"/employee/hr/enableOldEmployee","/employee/hr/enableOldEmp/**","/employee/hr/register","/employee/","/employee/Dashboard/hr","/employee/Dashboard/").hasAnyAuthority("GENERAL_MANAGER","HUMAN_RESOURCE")
    		.requestMatchers("/employee/hr/dlteEmpReqList","/employee/hr/dlteEmpReqDetails/**","/employee/hr/dlteEmpReq/approve/**","/employee/hr/dlteEmpReq/reject/**").hasAuthority("GENERAL_MANAGER")
    		.anyRequest().authenticated()
    	).csrf(csrf->csrf.disable())
    	.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    	.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class)
    	.exceptionHandling(exception->exception.accessDeniedPage("/employee/error-403"));
    	return http.build();
    }
        
}
