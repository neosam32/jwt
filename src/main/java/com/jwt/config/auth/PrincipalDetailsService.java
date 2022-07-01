package com.jwt.config.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jwt.model.User;
import com.jwt.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(PrincipalDetails.class);
	
	@Autowired
	private UserRepository userRepository; 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		logger.error(" PrincipalDetailsService username =[{}] " , username );
		User userEntity = userRepository.findByEmail( username );
		
		if( userEntity != null )
		{
			return new PrincipalDetails( userEntity );
		}
		
		return null;
	}
	
	

}
