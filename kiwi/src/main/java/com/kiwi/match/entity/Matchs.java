package com.kiwi.match.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;

import com.kiwi.court.entity.Reservation;
import com.kiwi.match.constant.Level;
import com.kiwi.match.constant.Status;
import com.kiwi.match.dto.MatchDto;
import com.kiwi.member.entity.Member;
import com.kiwi.shop.entity.BaseEntity;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "matchs")
@Getter
@Setter
@ToString
@NoArgsConstructor // 디폴트 생성자
public class Matchs extends BaseEntity {
	
	@Id
	@Column(name = "matchs_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; // 매치 아이디
	
//	@ManyToOne
//    @JoinColumn(name = "member_id")
//    private Member member;
	
	@Column(name="member_id")
	private Long memberId; // 상태
	
	// 지역, 도시 추가 해야함
	
	@Enumerated(EnumType.STRING)
    private Level level; // 레벨
	
	@Enumerated(EnumType.STRING)
    private Status status; // 종류
	
	@Column(name="matchs_type")
	private String type; // 상태
	
	@OneToOne
	@JoinColumn(name = "reservation_id")
	private Reservation reservation; // 코트예약 아이디. 매치는 하나의 코트예약건에서만 할 수 있음
	
	@Column(name="matchs_count")
	private long count;      // 매치 인원
	
	@Column(name="matchs_retime")
	private String retime;  // 매치 날짜 - 경기 날짜

	public static Matchs createMatch(@Valid MatchDto matchDto) {
		Matchs match = new Matchs();
		match.setLevel(matchDto.getLevel());
		match.setStatus(Status.신청);
		match.setType(matchDto.getType());
		match.setCount(matchDto.getCount());
		match.setRetime("2022-11-10");
		return match;
	}
	
}
