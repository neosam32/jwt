package com.jwt.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.config.auth.PrincipalDetails;
import com.jwt.model.User;
import com.jwt.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
public class RestApiController {
	private static final Logger logger = LoggerFactory.getLogger(RestApiController.class);
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/home")
	public String home() {
		return "<h1>  home </h1>";
	}
	
	@PostMapping("/token")
	public String token() {
		return "<h1>  token </h1>";
	}
	
	@PostMapping("join")
	public String  join(@RequestBody User user ) 
	{
	
			System.out.println("START join 3");
			System.out.println(" join START = [{}]"+ user );
			user.setRoles("ROLE_USER");
			
			String rawPassword = user.getPassword();
			String encPassword = bCryptPasswordEncoder.encode(rawPassword);
			user.setPassword(encPassword);
			
			userRepository.save( user  );	
		
		
        // redirect 는 /loginForm 이라는 함수 호출 함 .		
		return "redirect:/loginForm";
 	}
	
	
	@GetMapping("/api/v1/user")
	public String user( Authentication authentication  ) {
		
		PrincipalDetails principalDetails = ( PrincipalDetails )  authentication.getDetails();
		
		System.out.println("principalDetails" + principalDetails.getUser().getId() );
		
		return " user";
	}
	
	@GetMapping("/api/v1/admin")
	public String admin() {
		return "<h1>  admin </h1>";
	}
	
	@GetMapping("/api/v1/manager")
	public String manager() {
		return "<h1>  manager </h1>";
	}
	
}
