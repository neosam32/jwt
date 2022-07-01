package com.jwt.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.model.User;
import com.jwt.repository.UserRepository;

public class MyFilter2 implements Filter {

private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository userRepository; 
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
//		System.out.println(" fileter 3 ");
//		
//		HttpServletRequest req = ( HttpServletRequest ) request;
//		HttpServletResponse res = ( HttpServletResponse ) response;
//		
////		authenticationManager = request.
//
//		// 토큰 : id , pw 가 정상적으로 들어와서 로그인이 완료 되면 토큰을 만들어 주고 그걸 응답을 해준다.
//		// 요청할 때 마다 header에 Authorization에 value 값으로 토큰을 가지고옴
//		// 토큰이 넘어 오면 내가 만든 토큰인지만 검증하면 됨. RSA , HS256
//		// JwtAuthenticationFilter 필터에서 동작안해서 여기서 필터 만듬
//		
//		
//		System.out.println("req.getRequestURL() = " + req.getRequestURL() );
//		System.out.println("req.getRequestURI()() = " + req.getRequestURI() );
//		
////		BufferedReader br = request.getReader();
//		
////		String input = null;
////		while( ( input = br.readLine() ) != null )
////		{
//////input() = {
//////input() =     "username" : "hts",
//////input() =     "password" : "ddd"
//////input() = }
////			System.out.println( input );
////		}
//		
//		ObjectMapper om = new ObjectMapper(); // json 파싱 
//		
//		User user = om.readValue(request.getInputStream(), User.class );
//		System.out.println("user= " + user );
//		
//		
//		if( req.getRequestURI().equals("/login") )
//		{
//			System.out.println(" login 요청됨. " );
//			// username email.
//			// password 처리 여기서 하면 됨
//			// 정상 로그인 시도인지 여기서 체크 
////			System.out.println(" PrincipalDetailsService username =[{}] " , username );
////			User userEntity = userRepository.findByEmail( username );
//			
//			// 토큰을 만든다.
////			UsernamePasswordAuthenticationToken authenticationToken = 
////					new  UsernamePasswordAuthenticationToken( user.getUsername() , user.getPassword() );
////			System.out.println(" login 요청됨. 2" );
////		    Authentication authenticateAction = authenticationManager.authenticate(authenticationToken);
////		    System.out.println(" login 요청됨. 3" );
////		    PrincipalDetails principalDetails = (PrincipalDetails) authenticateAction.getPrincipal();
////		    System.out.println(" principalDetails.getUser = " + principalDetails.getUser());
//		}
//		
//		// 토큰 
////		if( req.getMethod().equals("POST"))
////		{
////			System.out.println(" POST 요청됨. " );
////			String headerAuth = req.getHeader("Authorization") ;
////			System.out.println(" headerAuth = " + headerAuth );
////			
////			if( headerAuth.equals("HTS")  )
////			{
////				chain.doFilter(req, res );
////			}else
////			{
////				PrintWriter printWriter = res.getWriter();
////				System.out.println(" 인증안됨. " );
////			}
////			
////		}
		
		chain.doFilter(request, response );
		
	}

}
