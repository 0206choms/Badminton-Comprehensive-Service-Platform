package com.kiwi.match.dto;

import com.kiwi.match.constant.Level;
import com.kiwi.match.constant.Status;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MatchSearchDto {
	
	// 검색 레벨
	private Level searchLevel;
	
	// 검색 상태
	private Status searchStatus;
	
	// 검색 종류
	private String searchType; 
	
	// 검색 날짜
	private String searchDate;
	
	
	private String searchQuery = "";
}
