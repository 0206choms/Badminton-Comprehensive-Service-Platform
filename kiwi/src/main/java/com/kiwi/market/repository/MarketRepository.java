package com.kiwi.market.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.kiwi.market.entity.Market;
import com.kiwi.match.entity.Matchs;
import com.kiwi.match.repository.MatchRepositoryCustom;

@Repository
public interface MarketRepository extends JpaRepository<Market,Long>,QuerydslPredicateExecutor<Market>, MarketRepositoryCustom{

	List<Market> findAllByOrderByIdDesc();
	
	// 검색 - 제목, 내용
	Page<Market> findByTitleContainingOrDetailContaining(String title, String detail, Pageable pageable);
	
	List<Market> findMarketByMemId(Long id);
}