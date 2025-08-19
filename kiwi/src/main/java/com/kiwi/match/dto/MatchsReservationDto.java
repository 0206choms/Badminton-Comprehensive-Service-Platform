package com.kiwi.match.dto;

import com.kiwi.match.entity.Matchs;
import com.kiwi.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchsReservationDto {

	private Long id; // 매치 아이디

	private Long memId;

	private Long mathcshId;

	private Long manners; // 매너점수

	private String winYN; // 승리 여부
	
//	private Matchs MathcshId;

}
