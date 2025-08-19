package com.kiwi.court.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.kiwi.court.dto.ReservationDto;
import com.kiwi.member.constant.Role;
import com.kiwi.member.dto.MemberFormDto;
import com.kiwi.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reservation {

	@Id
	@Column(name = "reservation_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 코트장
	private String court_name;

	// 코트 넘버
	private String court_num;
 
	// 코트 시간
	private String court_time;

	// 코트 예약 시간
	private String reservation_time;

	// 라켓 대여
	private String racket;

	// 셔틀콕 대여
	private String shuttlecock;

	// 예약한 아이디
	private String reservation_email;
	
	// 버튼 번호
	private String btnNum;
	
	// 결제 시간
	private LocalDateTime pay_time;
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
	@ManyToOne
	@JoinColumn(name = "court_id")
	private Court court;
	
	public Reservation(String name, String num, String time, String racket, String shuttlecock,String email,String reservation_time,String btnNum) {
		court_name = name;
		court_num = num;
		court_time = time;
		this.racket = racket;
		this.shuttlecock = shuttlecock;
		reservation_email = email;
		this.reservation_time = reservation_time;
		this.btnNum = btnNum;
		pay_time = LocalDateTime.now();
	}

}
