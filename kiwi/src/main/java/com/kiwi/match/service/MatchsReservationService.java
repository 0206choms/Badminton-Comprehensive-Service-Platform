package com.kiwi.match.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kiwi.court.entity.Reservation;
import com.kiwi.match.entity.MatchsReservation;
import com.kiwi.match.repository.MatchsReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MatchsReservationService {
	
	@Autowired
	MatchsReservationRepository matchsReservationRepository; 
	
//	 매치 신청 마감 확인을 위한 매치 신청 리스트 조회
	public List<MatchsReservation> mrCourt() {
		List<MatchsReservation> reservation = matchsReservationRepository.findAllByOrderByIdDesc();
		return reservation;
	}
	
}
