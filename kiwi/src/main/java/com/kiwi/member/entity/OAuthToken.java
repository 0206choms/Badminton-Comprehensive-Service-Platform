package com.kiwi.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
// objectMapper.readValue null 에러 해결하는 어노테이션
@JsonIgnoreProperties(ignoreUnknown = true)
public class OAuthToken {
	private String access_token;
	private String token_type;
	private String refresh_token;
	private int expires_in;
	private String scope;
	private int refresh_token_expires_in;
}
