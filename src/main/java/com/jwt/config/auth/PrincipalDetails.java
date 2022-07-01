package com.jwt.config.auth;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jwt.model.User;

import lombok.Getter;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
// 스프링 시큐리티으 고유한 세션 저장소에 저장을 해준다. Security ContextHoder
// 오브젝트 => Authentication 타입 객체
// Authentication 안에 User 정보가 있어야 됨.
// Security Sessin ==>  Authentication ==> UseDetails ==> User
// getter는 세션에서 나중에 user정보를 꺼내서 쓸때 사용한다.
@Getter
public class PrincipalDetails implements UserDetails {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User user; // 콤포지션
    private Map<String ,Object> attributes;
    
    // 일반로그인 
	public PrincipalDetails (User user )
	{
		this.user = user;
	}
	

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		// 실제 email로 로그인한다.
		return user.getUsername();
	}

	// 계정이 만료되지 않았는지를 리턴한다.( true : 만료안됨 )
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정이 잠겨있지 않았는지 리턴한다 ( true : 잠기지 않음 )
	@Override
	public boolean isAccountNonLocked() {

		return true;
	}
	
    // 비밀번호가 만료되지 않았는지 리턴한다. ( true : 만료되지 않음 )
	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	// 계정이 활성화인지 리턴한다. ( true : 활성화 )
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	// 계정이 가지고 있는 권한 목록을 리턴한다.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection <GrantedAuthority> collocter = new ArrayList<>();
		
		user.getRoleList().forEach( r -> {
			collocter.add(()->r );
		});
		
		return collocter;
	}

}