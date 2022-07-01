package com.jwt.config.jwt;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.config.auth.PrincipalDetails;
import com.jwt.model.User;

import lombok.RequiredArgsConstructor;

// 시큐리트에서 UsernamePasswordAuthenticationFilter 필터 
// /login 요청시 username , password를 전송하면 UsernamePasswordAuthenticationFilter 필터가 동작하나
// formlogin disable로 작동안함.

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	
	private final AuthenticationManager authenticationManager;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws org.springframework.security.core.AuthenticationException {
		HttpServletRequest req = ( HttpServletRequest ) request;
		HttpServletResponse res = ( HttpServletResponse ) response;
		
		 System.out.println( " /login 요청을 하면 로그인 시도를 위해서 실행되는 메소드 " );
		System.out.println("req.getRequestURL() = " + req.getRequestURL() );
		System.out.println("req.getRequestURI()() = " + req.getRequestURI() );
		
		ObjectMapper om = new ObjectMapper(); // json 파싱 
		
		
		try {
			User user = om.readValue(request.getInputStream(), User.class );
			System.out.println("user= " + user );
			
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
			
			System.out.println("step=1 " );
			
			// PrincipalDetailsService의 loadUserByUsername()함수가 실햏ㅇ된후 정상적인 Authentication  함수가 정상적으로 리턴됨.
			// 사용자 체크 완료.
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			
			System.out.println("step=2 " );
			
			PrincipalDetails principalDetails = ( PrincipalDetails )authentication.getPrincipal();
			
			System.out.println("step=3 User = "+ principalDetails.getUser() );
			
			// 굳이 JWT토큰을 사용하면서 세션을 만들 이유가 없음. 권한 처리를 위해서 만듬.
			// authentication 객체가 리턴할때 저장됨.
			return  authentication;
			
		} catch (StreamReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// 인증이 정상적으로 완료 되었으면 함수가 실행된다.
		System.out.println("인증이 정상적으로 완료 되었으면 함수가 실행된다.");
		PrincipalDetails principalDetails = ( PrincipalDetails )authResult.getPrincipal();
		
		// Hash 암호방식 
		String jwtToken = JWT.create()
				.withSubject( "hts" )
				.withExpiresAt(new Date(  System.currentTimeMillis() + ( (60000) * 10 ) ))
				// 넣고 싶으면 withClaim 늘리면 된다.
				.withClaim("id", principalDetails.getUser().getId() )
				.withClaim("username", principalDetails.getUser().getEmail() )
				.sign(Algorithm.HMAC512("HTS_TOKEN"));
		
		response.addHeader("Authentication", "Bearer " + jwtToken );
		
		
	}
	
}
