package com.sri.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.sri.Service.EmployeeService;

@Configuration
@EnableWebSecurity
public class EmployeeConfiguration {

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
    
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
    		req.requestMatchers("/","/emp/loginPage","/images/**","/css/**","/emp/register").permitAll()
    		.anyRequest().authenticated()
    	)
    	.formLogin(frm->
    		frm.loginPage("/emp/loginPage").loginProcessingUrl("/login").defaultSuccessUrl("/Dashboard/")
    	).logout(logout -> logout
    	        .logoutRequestMatcher(new AntPathRequestMatcher("/signOut"))
    	        .logoutSuccessUrl("/emp/loginPage?logout")
    	);
    	return http.build();
    }
    
    
        
}
