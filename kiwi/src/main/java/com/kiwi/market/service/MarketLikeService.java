package com.kiwi.market.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kiwi.market.entity.MarketLike;
import com.kiwi.market.repository.MarketLikeRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MarketLikeService {
	
	@Autowired
	MarketLikeRepository marketLikeRepository; 
	
//	 마켓 좋아요 목록 리스트 조회
	public List<MarketLike> marketLike() {
		List<MarketLike> marketLike = marketLikeRepository.findAllByOrderByIdDesc();
		return marketLike;
	}
	
	// 마켓 좋아요 삭제	
	public void deleteMarketLike(Long id) {
		marketLikeRepository.deleteById(id);
	}
}
