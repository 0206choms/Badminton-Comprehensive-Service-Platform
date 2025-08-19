package com.kiwi.match.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;

import com.kiwi.match.dto.MatchsReservationDto;
import com.kiwi.member.entity.Member;
import com.kiwi.shop.entity.BaseEntity;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "matchsReservation")
@Getter
@Setter
@ToString
@NoArgsConstructor // 디폴트 생성자
public class MatchsReservation extends BaseEntity{
	
	@Id
	@Column(name = "mr_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; // 매치 아이디

	@Column(name = "mr_memId")
    private Long memId;
	
	// 승리여부
	private String winYN;
	
	// 매너점수
	private Long manners;
	
	@ManyToOne
    @JoinColumn(name = "mr_mathsId")
    private Matchs mathshId;
	
	// 결제 시간
	private LocalDateTime pay_time;

	public static MatchsReservation createMR(Long id) {
		MatchsReservation mr = new MatchsReservation();
		mr.setMemId(id);
		mr.setPay_time(LocalDateTime.now());
		
		return mr;
	}
	
	
	
}
