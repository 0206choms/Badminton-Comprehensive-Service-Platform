package com.kiwi.court.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kiwi.config.auth.PrincipalDetails;
import com.kiwi.court.entity.Court;
import com.kiwi.court.entity.Reservation;
import com.kiwi.court.repository.CourtRepository;
import com.kiwi.court.repository.ReservationRepository;
import com.kiwi.member.entity.Member;
import com.kiwi.member.repository.MemberRepository;
import com.kiwi.pay.service.CashService;

@Service
@Transactional
public class ReservationService {
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired 
	private CourtRepository courtRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private CashService cashService;
	
	public Reservation saveReservation(@AuthenticationPrincipal PrincipalDetails principalDetails, Reservation reservation, String court_id) {
		Court court = courtRepository.findById(Long.parseLong(court_id)).orElseThrow(IllegalArgumentException::new);
		reservation.setCourt(court);
		
		Long id = principalDetails.getMember().getId();
		Member member = memberRepository.findMemberById(id);
		reservation.setMember(member);
		cashService.cashDeposit(id, 20000);
		
		return reservationRepository.save(reservation);
	}
	
	public List<Reservation> reserAll(){
		List<Reservation> reserAll = reservationRepository.findAll();
		return reserAll;
	}
	
	
}
