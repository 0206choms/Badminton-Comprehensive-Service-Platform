package com.kiwi.member.service;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kiwi.config.auth.PrincipalDetails;
import com.kiwi.market.entity.Comment;
import com.kiwi.market.entity.Market;
import com.kiwi.market.repository.CommentRepository;
import com.kiwi.market.repository.MarketRepository;
import com.kiwi.match.entity.Matchs;
import com.kiwi.member.dto.MemberUpdateDto;
import com.kiwi.member.dto.OauthAddInfoDto;
import com.kiwi.member.entity.Member;
import com.kiwi.member.repository.MemberRepository;


@Service
//로직을 처리하다가 에러가 발생하면 변경된 데이터를 조직 이전으로 콜백 시켜주기 위해
@Transactional
//final이나 NonNull 붙은 필드에 생성자를 생성해줌
@RequiredArgsConstructor

//UserDetailsService는 데이터베이스에서 회원정보를 가져오는 역할 (즉, 시큐리티에서 로그인 담당한다고 생각하면 됨)
public class MemberService implements UserDetailsService {

    //빈에 생성자가 1개이고 생성자의 파라미터 타입이 빈으로 등록이 가능하면 @Autowired 없이 의존성 주입 가능
    @Autowired
    private MemberRepository memberRepository;
    
    @Autowired
    private MarketRepository marketRepository;
    
    @Autowired
    private CommentRepository commentRepository;

    public Member saveMember(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }
    
    public Member saveMatchs(Long memberId, Long win, int point){
    	Member m = memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);
    	// 승리 점수
    	int score = win.intValue();
        m.setWin(m.getWin() + score);
        
        // 매너 점수
        m.setPoint(m.getPoint() + point);
        
        // 승리 점수에 따른 레벨 변경
        if(m.getWin() >= 5) {
        	if(m.getLevel().equals("A")) {
        		m.setLevel("B");
        	} else if(m.getLevel().equals("B")) {
        		m.setLevel("C");
        	} else if(m.getLevel().equals("C")) {
        		m.setLevel("D");
        	}
        	m.setWin(0);
        }
    	return memberRepository.save(m);
    }

    // 회원 중복체크
    private void validateDuplicateMember(Member member){
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if(findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
    
    // OAuth2 추가정보 등록
    public Member addInfo(@AuthenticationPrincipal PrincipalDetails principalDetails,OauthAddInfoDto addInfoDto) {
    	String email = principalDetails.getMember().getEmail();
    	Member member = memberRepository.findByEmail(email);
    	member.addInfoOAuth2(addInfoDto);
    	return member;
    }
    
    // mypage 정보 출력
    public Member mypageInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
    	String email = principalDetails.getMember().getEmail();
    	Member member = memberRepository.findByEmail(email);
    	return member;
    }
    
    // 로그인한 회원 ID 출력
    public Long getIdFromAuth(@AuthenticationPrincipal PrincipalDetails principalDetails) {
    	Long id = principalDetails.getMember().getId();
    	return id;
    }
    
    // 프로필 수정(이미지 변경할 경우)
    public Member updateImage(Member member, String fileName, MemberUpdateDto memberUpdateDto) {
    	member.setImage(fileName);
    	member.setBnumber(memberUpdateDto.getBnumber());
    	member.setBname(memberUpdateDto.getBname());
    	member.setAddress(memberUpdateDto.getAddress());
    	member.setPnum(memberUpdateDto.getPnum());
    	
    	// 로그인한 사용자가 이미지 변경했을때 마켓 테이블 프로필 이미지 변경
    	if(marketRepository.findMarketByMemId(member.getId()) != null) {
    	List<Market> markets = marketRepository.findMarketByMemId(member.getId());
    	for (Market market2 : markets) {
			market2.setMemImg(fileName);
			}
    	}
    	
    	// 로그인한 사용자가 이미지 변경했을때 댓글 테이블 프로필 이미지 변경
    	if(commentRepository.findCommentByMemId(member.getId()) != null) {
    		List<Comment> comments = commentRepository.findCommentByMemId(member.getId());
    		for (Comment comment : comments) {
				comment.setMemImg(fileName);
			}
    	}
    	
    	
    	//marketRepository.save(market);
    	return memberRepository.save(member);
    }
    
    // 프로필 수정(이미지 변경 안할 경우)
    public Member updateProfile(Member member,MemberUpdateDto memberUpdateDto) {
    	member.setBnumber(memberUpdateDto.getBnumber());
    	member.setBname(memberUpdateDto.getBname());
    	member.setAddress(memberUpdateDto.getAddress());
    	member.setPnum(memberUpdateDto.getPnum());
    	return memberRepository.save(member);
    }
    
    
    //public Member saveOAuth2()

    // UserDetailsService 인터페이스의 오버라이딩한다. 로그인할 유저의 email을 파라미터로 전달함( 이름은 동명이인이 있을수 있기 때문에)
    // 시큐리티 session(내부 Authentication(내부 UserDetails))
    // 메서드 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	Member member = memberRepository.findByEmail(email);
        	if(member == null) {
        		System.out.println("DB에 유저가 없어용 ㅠ");
        		throw new UsernameNotFoundException(email);
    		}else {
    			return new PrincipalDetails(member);
    		}
    }
}
