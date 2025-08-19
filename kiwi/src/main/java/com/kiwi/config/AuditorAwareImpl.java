package com.kiwi.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import com.kiwi.config.auth.PrincipalDetails;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware {
    @Override
    public Optional getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        //User user = (User) authentication.getPrincipal();
        String name = "";
        if(authentication != null){
            //PrincipalDetails prinici = (PrincipalDetails) authentication.getPrincipal();      // 현재 로그인한 사용자의 정보를 조회하여 사용자의 이름을 등록자와 수정자로 지정합니다.
            //name = user.getName();
            name = authentication.getName();
        }
		return Optional.of(name);
    }
}
