package com.kiwi.shop.entity;
//package com.kiwi.shop.entity;
//
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import com.kiwi.constant.Role;
//import com.kiwi.shop.dto.MemberFormDto;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name="member")
//@Getter @Setter
//@ToString
//public class Member extends BaseEntity{
//    @Id
//    @Column(name="member_id")
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    private String name;
//
//    // 회원은 이메일을 통해 유일하게 구분해야 하기 때문에, 동일한 값이 DB에 저장할 수 없도록 unique 속성 지정한다.
//    @Column(unique = true)
//    private String email;
//
//    private String password;
//
//    private String address;
//
//    // enum 타입은 기본적으로 순서가 저장되는데 순서가 바뀌면 문제가 생기므로 STRING 옵션 설정한다.
//    @Enumerated(EnumType.STRING)
//    private Role role;
//
//    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
//        Member member = new Member();
//        member.setName(memberFormDto.getName());
//        member.setEmail(memberFormDto.getEmail());
//        member.setAddress(memberFormDto.getAddress());
//        String password = passwordEncoder.encode(memberFormDto.getPassword());
//        member.setPassword(password);
//        member.setRole(Role.ADMIN);
//        return member;
//    }
//}
