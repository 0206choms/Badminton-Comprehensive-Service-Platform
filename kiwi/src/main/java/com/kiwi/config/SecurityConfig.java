package com.kiwi.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.kiwi.config.oauth.PrincipalOauth2UserService;
import com.kiwi.handler.LoginSuccessHandler;
import com.kiwi.member.service.MemberService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    // http 요청에 대한 보안설정
//    protected void configure(HttpSecurity http) throws Exception {
//		
//    }

   @Autowired
    private MemberService memberService;
   
   @Autowired
   	private DataSource dataSource;
   
   @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;
   
   @Autowired
   	private LoginSuccessHandler LoginSuccessHandler;
   
   
    // 인증 or 인가에 대한 설정
    // 스프링 시큐리티 5.7 버전부터는 WebSecurityConfigurerAdapter가 Deprecated 되었기 때문에
    // 아래와 같이SecurityFilterChain 타입의 빈으로 대체
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin()
//                .csrf().disable()        // 스프링 시큐리티에서는 CSRF공격을 방어하기 위해서 POST방식의 데이터 전송에는 반드시 CSRF토큰이 있어야함

                .loginPage("/members/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .passwordParameter("password")
                .failureUrl("/members/login/error")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/members/login")	// 구글 로그인이 완료된후 후처리가 필요함. Oauth2-client 사용하면 코드x / 액세스토큰 + 사용자프로필정보 같이 받아옴
                .successHandler(new LoginSuccessHandler())
                .userInfoEndpoint()
        		.userService(principalOauth2UserService);
    
        		

        http.authorizeRequests()

                .mvcMatchers("/css/**","/js/**","/img/**","/video/**","/login/**","/signup/**","/image/title/**","/pay/**", "/pay/charge/**").permitAll()

                .mvcMatchers("/", "https://app.gather.town/**", "/market/**", "/members/**","/item/**","/images/**",  "/image/upload/**", "/oauth2/**","/members/login/**", "/mypage/**", "/pay/**","/court/**", "/match/**").permitAll()

                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();

        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());
        
        // 로그인 기억하기 
        http.rememberMe()
        		.userDetailsService(memberService)
        		.tokenRepository(tokenRepository());

        return http.build();
    }

    // 비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public PersistentTokenRepository tokenRepository() {
    	// JDBC 기반의 tokenRepository 구현체
    	JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
    	jdbcTokenRepository.setDataSource(dataSource);
    	return jdbcTokenRepository;
    }
}