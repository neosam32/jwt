package com.jwt.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jwt.filter.MyFilter1;
import com.jwt.filter.MyFilter2;

@Configuration
public class FilterConfig {

	    @Bean
	    public FilterRegistrationBean< MyFilter1 > fiter1(){
	    	FilterRegistrationBean<MyFilter1> bean = new FilterRegistrationBean<>( new MyFilter1() );
	    	
	    	bean.addUrlPatterns("/*");
	    	
	    	bean.setOrder(0);  // 낮은 번호가 제일먼저 실행됨.
	    	return bean;
	    }
	    
	    @Bean
	    public FilterRegistrationBean< MyFilter2 > fiter2(){
	    	FilterRegistrationBean<MyFilter2> bean = new FilterRegistrationBean<>( new MyFilter2() );
	    	
	    	bean.addUrlPatterns("/*");
	    	bean.setOrder(1);  // 낮은 번호가 제일먼저 실행됨.
	    	return bean;
	    }
	
}
