package com.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;

import com.jwt.config.jwt.JwtAuthenticationFilter;
import com.jwt.config.jwt.JwtAuthorizationFilter;
import com.jwt.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final CorsFilter corsFilter;
	private final UserRepository userRepository; 
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//시큐리티 필터에 등록 할 경우 
//		http.addFilterAfter(  new MyFilter3() , BasicAuthenticationFilter.class );
//		http.addFilterBefore(  new MyFilter3() , BasicAuthenticationFilter.class );
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS  )  // 세션을 사용하지 않음.
		.and()
		       .addFilter(corsFilter) // @CrossOrigin( 인증 x ) , 시큐리티 필터에 등록 인증(0)
		       .formLogin().disable() // 폼로긴 사용하지 않음
		        .httpBasic().disable()  //  Bearer 방식사용함 .
		        .addFilter(new JwtAuthenticationFilter(  authenticationManager() ) ) // 
		        .addFilter(new JwtAuthorizationFilter(  authenticationManager() , userRepository  ) ) // 
		        .authorizeRequests()  
		        .antMatchers("/api/v1/user/**")
		        .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ADMIN')")
		        .antMatchers("/api/v1/manager/**")
		        .access("hasRole('ROLE_MANAGER') or hasRole('ADMIN')")
		        .antMatchers("/api/v1/admin/**")
		        .access("hasRole('ADMIN')")
		        .anyRequest().permitAll()
		        ;
		
	}

	
}
