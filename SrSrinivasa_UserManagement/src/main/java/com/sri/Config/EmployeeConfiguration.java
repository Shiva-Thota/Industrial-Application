package com.sri.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.view.BeanNameViewResolver;

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
    
    @Bean
	BeanNameViewResolver viewresolver() {
		BeanNameViewResolver res=new BeanNameViewResolver();
		res.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return res;
	}
        
}
