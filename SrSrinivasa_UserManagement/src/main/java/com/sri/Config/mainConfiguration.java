package com.sri.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.view.BeanNameViewResolver;

@Configuration
public class mainConfiguration {

	
	 @Bean
	BeanNameViewResolver viewresolver() {
		BeanNameViewResolver res=new BeanNameViewResolver();
		res.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return res;
	}

}