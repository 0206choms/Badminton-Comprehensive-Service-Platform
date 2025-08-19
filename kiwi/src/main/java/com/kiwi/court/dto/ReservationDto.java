package com.kiwi.court.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationDto {

	// 외래키에 넣을 필드,, 엔티티에는 필요 없음
	// 코트 Id
	private String court_id;

	// 코트장
	private String court_name;

	// 플레이 타임(예약 시간 및 장소) ex) 코트0 06:00 ~ 08:00
	private String reservation_info;

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
	
	// 임시 코트 예약시간 변수
	private String searchText;
	
	public void subReserInfo(ReservationDto dto, String reserInfo,String email) {
		dto.setCourt_num(reserInfo.substring(0, 3));
		dto.setCourt_time(reserInfo.substring(4));
		dto.setReservation_email(email);
	}

}
