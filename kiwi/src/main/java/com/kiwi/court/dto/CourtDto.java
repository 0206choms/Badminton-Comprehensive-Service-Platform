package com.kiwi.court.dto;

import org.modelmapper.ModelMapper;

import com.kiwi.court.entity.Court;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourtDto {

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

	private static ModelMapper modelMapper = new ModelMapper();

	// modelMapper를 이용하여 엔티티 객체와 DTO객체 간의 데이터를 복사하여 복사한 객체를 반환해주는 메소드
	public Court createItem() {
		return modelMapper.map(this, Court.class);
	}
	
	// modelMapper를 이용하여 엔티티 객체와 DTO객체 간의 데이터를 복사하여 복사한 객체를 반환해주는 메소드
	public static CourtDto of(Court court) {
		return modelMapper.map(court, CourtDto.class);
	}
}
