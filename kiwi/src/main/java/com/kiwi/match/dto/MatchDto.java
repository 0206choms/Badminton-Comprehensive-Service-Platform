package com.kiwi.match.dto;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.kiwi.court.entity.Reservation;
import com.kiwi.market.constant.ItemSellStatus;
import com.kiwi.match.constant.Level;
import com.kiwi.match.constant.Status;
import com.kiwi.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchDto {
	
	private Long id; // 매치 아이디
	
//    private Member member;
	
	private Long memberId; 
	
	// 지역, 도시, 타입 추가 해야함
	
    private Level level; // 레벨
	
    private Status status; // 종류
	
	@Column(name="match_type")
	private String type; // 상태
	
	//private Reservation reservation; // 코트예약 아이디. 매치는 하나의 코트예약건에서만 할 수 있음
	
	private Long reser_id;
	
	private String retime;  // 매치 날짜 - 경기 날짜
	
	@Column(name="match_count")
	private long count;      // 매치 인원

}
