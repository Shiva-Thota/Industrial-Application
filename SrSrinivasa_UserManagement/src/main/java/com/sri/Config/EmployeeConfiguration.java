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
//	@Bean
//	AuthenticationManager authenticationManager(AuthenticationConfiguration authConfigurer) throws Exception {
//		return authConfigurer.getAuthenticationManager();
//	}
//	
//	
//	@Bean
//	AuthenticationProvider authenticationProvider(AuthenticationProvider prvdr) {
//		DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
//		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
//		daoAuthenticationProvider.setUserDetailsService(employeeService);
//		return prvdr;
//	}
	
   //For Statefull Authentication
     
     @Bean
    SecurityFilterChain  filterChains(HttpSecurity http) throws Exception {
    	http.authorizeHttpRequests(req->
    		req.requestMatchers("/","/user/loginPage","/images/**","/css/**","/hr/register").permitAll()
    		.requestMatchers("/hr/**").hasAnyAuthority("ADMIN","HUMAN_RESOURCE")
    		.anyRequest().authenticated()
    	)
    	.formLogin(frm->
    		frm.loginPage("/user/loginPage").loginProcessingUrl("/login").defaultSuccessUrl("/Dashboard/")
    	).logout(logout -> logout
    	        .logoutRequestMatcher(new AntPathRequestMatcher("/signOut"))
    	        .logoutSuccessUrl("/user/loginPage?logout")
    	).exceptionHandling(ex->ex.accessDeniedPage("/accessDenined403"));
    	return http.build();
    }
    
//	@Bean
//    SecurityFilterChain  filterChains(HttpSecurity http) throws Exception {
//    	http.authorizeHttpRequests(req->
//    		req.requestMatchers("/","/user/loginPage","/images/**","/css/**","/hr/register").permitAll()
//    		.requestMatchers("/hr/**").hasAnyAuthority("ADMIN","HUMAN_RESOURCE")
//    		.anyRequest().authenticated()
//    	).csrf(csrf->csrf.disable())
//    	.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//    	.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
//    	return http.build();
//    }
        
}
