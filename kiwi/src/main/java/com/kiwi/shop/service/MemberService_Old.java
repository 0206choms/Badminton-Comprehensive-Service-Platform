package com.kiwi.shop.service;
//package com.kiwi.shop.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.kiwi.member.entity.Member;
//import com.kiwi.member.repository.MemberRepository;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
////로직을 처리하다가 에러가 발생하면 변경된 데이터를 조직 이전으로 콜백 시켜주기 위해
//@Transactional
////final이나 NonNull 붙은 필드에 생성자를 생성해줌
//@RequiredArgsConstructor
//
////UserDetailsService는 데이터베이스에서 회원정보를 가져오는 역할 (즉, 시큐리티에서 로그인 담당한다고 생각하면 됨)
//public class MemberService implements UserDetailsService {
//
//    //빈에 생성자가 1개이고 생성자의 파라미터 타입이 빈으로 등록이 가능하면 @Autowired 없이 의존성 주입 가능
//    @Autowired
//    private MemberRepository memberRepository;
//
//    public Member saveMember(Member member){
//        validateDuplicateMember(member);
//        return memberRepository.save(member);
//    }
//
//    // 회원 중복체크
//    private void validateDuplicateMember(Member member){
//        Member findMember = memberRepository.findByEmail(member.getEmail());
//        if(findMember != null){
//            throw new IllegalStateException("이미 가입된 회원입니다.");
//        }
//    }
//
//    // UserDetailsService 인터페이스의 오버라이딩한다. 로그인할 유저의 email을 파라미터로 전달함( 이름은 동명이인이 있을수 있기 때문에)
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Member member = memberRepository.findByEmail(email);
//
//        if(member == null){
//            throw new UsernameNotFoundException(email);
//        }
//
//        return User.builder()       // UserDetail을 구현하고 있는 User객체를 반환합니다. User객체를 생성하기 위해서 생성자로 회원의이메일,패스워드,role을 넘겨준다.
//                .username(member.getEmail())
//                .password(member.getPassword())
//                .roles(member.getRole().toString())
//                .build();
//    }
//}
