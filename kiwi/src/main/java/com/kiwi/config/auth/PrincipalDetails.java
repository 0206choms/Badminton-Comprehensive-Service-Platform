package com.kiwi.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.kiwi.member.entity.Member;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails,OAuth2User {
	
	private Member member;	
	
	private Map<String,Object> attributes;

	// 일반 로그인
	public PrincipalDetails(Member member) {
		this.member = member;
	}
	
	// OAuth 로그인
	public PrincipalDetails(Member member, Map<String, Object> attributes) {
		this.member = member;
		this.attributes = attributes;
	}

	
	// 해당 User의 권한을 리턴하는 곳
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				return member.getRole().toString();
			}
		});
		return collect;
	}

	@Override
	public String getPassword() {
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		return member.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		// 1년동안 회원이 로그인을 안하면 휴먼 계정으로 하기로 설정
		// 현재시간 - 로그인시간 => 1년을 초과하면 return false;로 설정!
		// member.getLoginDate();
		// member엔티티에  logindate 필드 추가
		
		
		return true;
	}

	// 아래 메소드는 OAuth2User에서 받아온것 
	
	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String getName() {
		return member.getName();
	}

	
}
