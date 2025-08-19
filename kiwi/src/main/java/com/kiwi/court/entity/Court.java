package com.kiwi.court.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="court")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Court {
	
	@Id
    @Column(name="court_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	// 장소명
	private String name;
	
	// 휴관일
	private String holiday;
	
	// 평일 시작시간
	private String weekday_start;
	
	// 평일 종료시간
	private String weekday_end;
	
	// 주말 시작시간
	private String weekend_start;
	
	// 주말 종료시간
	private String weekend_end;
	
	// 유료,무료
	private String free_yn;
	
	// 면적
	private String area;
	
	// 도로명주소
	private String address1;
	
	// 주소
	private String address2;
	
	// 전화번호
	private String call_number;
	
	// 위도
	private String lat;
	
	// 경도
	private String lng;
}