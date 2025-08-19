package com.kiwi.match.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.kiwi.match.constant.Level;
import com.kiwi.match.constant.Status;
import com.kiwi.match.dto.MatchSearchDto;
import com.kiwi.match.entity.Matchs;
import com.kiwi.match.entity.QMatchs;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class MatchRepositoryImpl implements MatchRepositoryCustom {
	
	private JPAQueryFactory queryFactory;
	
	public MatchRepositoryImpl(EntityManager em) {
		queryFactory = new JPAQueryFactory(em);
	}
	
	private BooleanExpression searchStatusEq(Status status) {
		return status == null ? null : QMatchs.matchs.status.eq(status);
	}
	
	private BooleanExpression searchLeverEq(Level level) {
		return level == null ? null : QMatchs.matchs.level.eq(level);
	}
	
	private BooleanExpression searchType(String searchType) {
		
		if(StringUtils.equals("all", searchType) || searchType == null) {
			return null;
		}
		else if(StringUtils.equals("1vs1(단식)", searchType)) {
			return QMatchs.matchs.type.eq(searchType);
		}
		else if(StringUtils.equals("2vs2(복식)", searchType)) {
			return QMatchs.matchs.type.eq(searchType);
		}
		return null;
		
		//return searchType == null ? null : QMatchs.matchs.type.eq(searchType);
	}
	
	private BooleanExpression searchDateEq(String searchDate) {
		System.out.println("====================> retime 직전 : " + searchDate);
		return searchDate == null ? null : QMatchs.matchs.retime.eq(searchDate);
	}
	
	private BooleanExpression searchByQuery(String searchQuery) {
			return searchQuery == null ? null : QMatchs.matchs.reservation.court_name.like("%" + searchQuery + "%");
	}
	
	

	@Override
	public Page<Matchs> getSearchMatchPage(MatchSearchDto matchSearchDto, Pageable pageable) {
		List<Matchs> matchsList = queryFactory
				.selectFrom(QMatchs.matchs)
				.where(
						searchStatusEq(matchSearchDto.getSearchStatus()),
						searchLeverEq(matchSearchDto.getSearchLevel()),
						searchType(matchSearchDto.getSearchType()),
						searchDateEq(matchSearchDto.getSearchDate()),
						searchByQuery(matchSearchDto.getSearchQuery())
					  )
				.orderBy(QMatchs.matchs.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		
		long total = queryFactory
				.select(Wildcard.count)
				.from(QMatchs.matchs)
				.where(
						searchStatusEq(matchSearchDto.getSearchStatus()),
						searchLeverEq(matchSearchDto.getSearchLevel()),
						searchType(matchSearchDto.getSearchType()),
						searchDateEq(matchSearchDto.getSearchDate()),
						searchByQuery(matchSearchDto.getSearchQuery())
						)
				.fetchOne();
				
		
		return new PageImpl<>(matchsList,pageable,total);
	}

}
