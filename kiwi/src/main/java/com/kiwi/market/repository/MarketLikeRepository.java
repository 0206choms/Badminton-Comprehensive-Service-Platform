package com.kiwi.market.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kiwi.market.entity.MarketLike;

@Repository
public interface MarketLikeRepository extends JpaRepository<MarketLike, Long> {
	List<MarketLike> findAllByOrderByIdDesc();
	
	
}