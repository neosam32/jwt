package com.jwt.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.jwt.config.auth.PrincipalDetails;
import com.jwt.model.User;
import com.jwt.repository.UserRepository;

// 시큐리티가 필터를 가지고 있는데 BasicAuthenticationFilter 있음.
// 권한이나 인증이 필요한 특정 주소를 요청했을때 위 필터를 무조건 탐
// 만약에 권한이나 인증이 필요한 주소가 아니면 안탐.

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private UserRepository userRepository; 
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager , UserRepository userRepository) {
		super(authenticationManager);
		
		this.userRepository = userRepository;
		
	}

	// 인증이나 권한이 필요한 주소 요청시 해당 필터가 호출됨.
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println(" 권한이나 인증이 필요한 주소가 들어왔음. ");
		
		String jwtHeader = request.getHeader("Authorization");
		
		System.out.println(" jwtHeader= "+ jwtHeader);
		
		// JWT 토큰을 검증해서 정상적인 사용자인지 검증
		if( jwtHeader == null || !jwtHeader.startsWith("Bearer") ) {
			chain.doFilter(request, response);
		}
			
		String token = request.getHeader("Authorization").replace("Bearer ", "");
		
		String username = JWT.require( Algorithm.HMAC512("HTS_TOKEN")).build().verify(token).getClaim("username").asString();
		
		if( username != null )
		{
			// 서명이 정상적으로 됨.
			System.out.println(" username= "+ username);
			
			User userEntity = userRepository.findByEmail(username);
			
			// 이메일 체크 할거라 바꿈
			userEntity.setUsername(username);
			
			PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
			
			// JWT 토큰 서명이 정상이면 Authentication 객체를 강제로 만든다.
			Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails,principalDetails.getAuthorities() );
			
			
			
			// 시큐리에 세션을 저장할수 있는 공간에 넣어준다.
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			chain.doFilter(request, response);
		}
			
	}
}
