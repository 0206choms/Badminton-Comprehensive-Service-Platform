package com.kiwi.market.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kiwi.market.dto.MarketSearchDto;
import com.kiwi.market.entity.Market;

public interface MarketRepositoryCustom {
					
	Page<Market> getSearchMarketPage(MarketSearchDto marketSearchDto, Pageable pageable);
					
}
