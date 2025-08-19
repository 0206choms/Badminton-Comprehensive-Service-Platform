package com.kiwi.config.oauth.provider;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo{

	private Map<String, Object> attributes; // oAuth2User.getAttributes()
	
	//properties={nickname=김종하}, kakao_account={profile_nickname_needs_agreement=false, profile={nickname=김종하}, has_email=true, email_needs_agreement=false, is_email_valid=true, is_email_verified=true, email=gbr369369@naver.com}
	public KakaoUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String getProviderId() {
		return String.valueOf(attributes.get("id"));
	}
 
	@Override
	public String getProvider() {
		return "kakao";
	}

	@Override
	public String getEmail() {
		Map<String, Object> profile = (Map<String, Object>) attributes.get("kakao_account");
		return String.valueOf(profile.get("email"));
	}

	@Override
	public String getName() {
		Map<String, Object> profile = (Map<String, Object>) attributes.get("properties");
		return String.valueOf(profile.get("nickname"));
	}

	@Override
	public String getImage() {
		Map<String, Object> profile = (Map<String, Object>) attributes.get("properties");
		return String.valueOf(profile.get("profile_image"));
	}

}
