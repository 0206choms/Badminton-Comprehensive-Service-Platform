package com.kiwi.config;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 로그인 기억하기 테이블
 * @author Jongha
 *
 */
@Table(name="persistent_logins")
@Entity
@Getter
@Setter
public class PersistentLogins {
	
	@Id
	@Column(length = 64)
	private String series;
	
	@Column(nullable = false,length = 64)
	private String username;
	
	@Column(nullable = false,length = 64)
	private String token;
	
	@Column(name="last_used",nullable = false,length = 64)
	private LocalDateTime lastUsed;
}
