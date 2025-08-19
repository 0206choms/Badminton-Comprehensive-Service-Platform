package com.kiwi.pay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kiwi.match.entity.MatchsReservation;
import com.kiwi.member.entity.Member;
import com.kiwi.member.repository.MemberRepository;
import com.kiwi.pay.entity.Cash;
import com.kiwi.pay.repository.CashRepository;

@Service
//로직을 처리하다가 에러가 발생하면 변경된 데이터를 조직 이전으로 콜백 시켜주기 위해
@Transactional
public class CashService {
	
	@Autowired
	private CashRepository cashRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	// 유저 캐시 충전 - dto 만들어서 작업해야하지만 시간이 없어서 일단 엔티티로 바로 접근해서 하고 추후에 dto로 변경
	public Cash chargeCash(Cash cash, Long id) {
		Member member = memberRepository.findMemberById(id);
		cash.setMember(member);
//		member.setKiwicash(0);
//		int memberOriCash = member.getKiwicash();
//		member.setKiwicash(memberOriCash + cashRepository.amountSum(member));
		return cashRepository.save(cash);
	}
		
	// 유저 캐시 필드 매핑
	public void cashMapping(Long id, int amount) {
		Member member = memberRepository.findMemberById(id);
		member.setKiwicash(member.getKiwicash() + amount);
	}
	
	// 유저 캐시 잔액
	public int cashSearch(Long id) {
		Member member = memberRepository.findMemberById(id);
		int kiwicash = member.getKiwicash();
		return kiwicash;
	}
	
	// 캐시 차감
	public void cashDeposit(Long id, int amount) {
		Member member = memberRepository.findMemberById(id);
		if(member.getKiwicash() < amount) {
			System.out.println("금액이 부족해,,");
			
		} else {
			member.deposit(amount);
		}
		
	}
	
	// 캐시 증가( 키위 마켓용)
	public void cashPlus(Long id, int amount) {
		Member member = memberRepository.findMemberById(id);
		member.withdraw(amount);
	}

	// 캐시 내역 리스트로 띄우기 위함
	public List<Cash> cash() {
		List<Cash> cash = cashRepository.findAllByOrderByIdDesc();
		return cash;
	}
	
}
