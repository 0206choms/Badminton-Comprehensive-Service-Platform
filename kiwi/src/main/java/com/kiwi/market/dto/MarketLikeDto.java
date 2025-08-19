package com.kiwi.market.dto;

import com.kiwi.market.entity.Market;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MarketLikeDto {
	private Long id; // 마켓 좋아요 아이디

    private Long memId; // 좋아요 누른 멤버 아이디
	
    private Market marketId;
}
