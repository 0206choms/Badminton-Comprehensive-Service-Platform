package com.kiwi.market.dto;

import com.kiwi.member.constant.Address;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MarketSearchDto {
		// 검색 지역
		private Address searchLocal;
		
		private String searchQuery = "";
}
