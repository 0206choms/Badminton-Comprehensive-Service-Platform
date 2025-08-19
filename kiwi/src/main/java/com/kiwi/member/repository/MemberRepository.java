package com.kiwi.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiwi.member.entity.Member;


public interface MemberRepository extends JpaRepository<Member,Long> {
    //회원 가입시 중복되지 않기 위해 이메일 검색하는 쿼리 메소드 추가
    Member findByEmail(String email);
    Member findByProviderId(String ProviderId);
    
    // optional 타입으로 하고 findById(Long id)하는게 맞는데 빠르게 작업하기 위해 일단 이렇게 추후 변경
    Member findMemberById(Long id);
}
