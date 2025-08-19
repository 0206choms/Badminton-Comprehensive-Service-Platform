package com.kiwi.market.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.kiwi.market.dto.MarketSearchDto;
import com.kiwi.market.entity.Market;
import com.kiwi.market.entity.QMarket;
import com.kiwi.match.entity.QMatchs;
import com.kiwi.member.constant.Address;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class MarketRepositoryImpl implements MarketRepositoryCustom {
	
	private JPAQueryFactory queryFactory;
	
	public MarketRepositoryImpl(EntityManager em) {
		queryFactory = new JPAQueryFactory(em);
	}
//	
	private BooleanExpression searchLocalEq(Address address) {
		return address == null ? null : QMarket.market.address.eq(address);
	}
	
	private BooleanExpression searchByQuery(String searchQuery) {
		return searchQuery == null ? null : QMarket.market.title.like("%" + searchQuery + "%");
	}
//	
//	private BooleanExpression searchLeverEq(Level level) {
//		return level == null ? null : QMatchs.matchs.level.eq(level);
//	}
//	
//	private BooleanExpression searchType(String searchType) {
//		
//		if(StringUtils.equals("all", searchType) || searchType == null) {
//			return null;
//		}
//		else if(StringUtils.equals("1vs1(단식)", searchType)) {
//			return QMatchs.matchs.type.eq(searchType);
//		}
//		else if(StringUtils.equals("2vs2(복식)", searchType)) {
//			return QMatchs.matchs.type.eq(searchType);
//		}
//		return null;
//		
//		//return searchType == null ? null : QMatchs.matchs.type.eq(searchType);
//	}
//	
//	private BooleanExpression searchDateEq(String searchDate) {
//		if(StringUtils.equals("all", searchDate) || searchDate == null) {
//			return null;
//		} else {
//			return QMatchs.matchs.retime.eq(searchDate);
//		}
//		
//	}
//	
//	private BooleanExpression searchByQuery(String searchQuery) {
//			return searchQuery == null ? null : QMatchs.matchs.reservation.court_name.like("%" + searchQuery + "%");
//	}
	
	

//	@Override
//	public Page<Matchs> getSearchMatchPage(MatchSearchDto matchSearchDto, Pageable pageable) {
//		List<Matchs> matchsList = queryFactory
//				.selectFrom(QMatchs.matchs)
//				.where(
//						searchStatusEq(matchSearchDto.getSearchStatus()),
//						searchLeverEq(matchSearchDto.getSearchLevel()),
//						searchType(matchSearchDto.getSearchType()),
//						searchByQuery(matchSearchDto.getSearchQuery()),
//						searchDateEq(matchSearchDto.getSearchDate())
//					  )
//				.orderBy(QMatchs.matchs.id.desc())
//				.offset(pageable.getOffset())
//				.limit(pageable.getPageSize())
//				.fetch();
//		
//		long total = queryFactory
//				.select(Wildcard.count)
//				.from(QMatchs.matchs)
//				.where(
//						searchStatusEq(matchSearchDto.getSearchStatus()),
//						searchLeverEq(matchSearchDto.getSearchLevel()),
//						searchType(matchSearchDto.getSearchType()),
//						searchByQuery(matchSearchDto.getSearchQuery()),
//						searchDateEq(matchSearchDto.getSearchDate())
//						)
//				.fetchOne();
//				
//		
//		return new PageImpl<>(matchsList,pageable,total);
//	}

	@Override
	public Page<Market> getSearchMarketPage(MarketSearchDto marketSearchDto, Pageable pageable) {
		List<Market> marketsList = queryFactory
		.selectFrom(QMarket.market)
		.where(
				searchLocalEq(marketSearchDto.getSearchLocal()),
				searchByQuery(marketSearchDto.getSearchQuery())
			  )
		.orderBy(QMarket.market.id.desc())
		.offset(pageable.getOffset())
		.limit(pageable.getPageSize())
		.fetch();

long total = queryFactory
		.select(Wildcard.count)
		.from(QMarket.market)
		.where(
				searchLocalEq(marketSearchDto.getSearchLocal()),
				searchByQuery(marketSearchDto.getSearchQuery())
				)
		.fetchOne();
		

		return new PageImpl<>(marketsList,pageable,total);
	}

}
