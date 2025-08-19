package com.kiwi.match.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kiwi.match.dto.MatchSearchDto;
import com.kiwi.match.entity.Matchs;

public interface MatchRepositoryCustom {
					
	Page<Matchs> getSearchMatchPage(MatchSearchDto matchSearchDto, Pageable pageable);
					
}
