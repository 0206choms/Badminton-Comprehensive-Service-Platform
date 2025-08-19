package com.kiwi.market.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kiwi.config.auth.PrincipalDetails;
import com.kiwi.market.UploadFile;
import com.kiwi.market.constant.ItemSellStatus;
import com.kiwi.market.dto.MarketDto;
import com.kiwi.market.dto.MarketSearchDto;
import com.kiwi.market.entity.Market;
import com.kiwi.market.entity.MarketLike;
import com.kiwi.market.repository.MarketLikeRepository;
import com.kiwi.market.repository.MarketRepository;
import com.kiwi.match.dto.MatchSearchDto;
import com.kiwi.match.entity.Matchs;
import com.kiwi.member.entity.Member;
import com.kiwi.member.repository.MemberRepository;
import com.kiwi.pay.service.CashService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MarketService {

	@Autowired
	MarketRepository marketRepository;

	@Autowired
	UploadFile uploadFile;

	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	private CashService cashService;

	@Autowired
	private MarketLikeRepository marketLikeRepository;

	// 마켓 좋아요
	public void saveMarketLike(MarketLike ml, Long marketId, Long memberId) {
		Market market = marketRepository.findById(marketId).orElseThrow(EntityNotFoundException::new);
		System.out.println();
		ml.setMarketId(market);

		ml.setMemId(memberId);
		marketLikeRepository.save(ml);
	}
	
	// 마켓 글 저장
	public void saveMarket(Market market, MultipartFile file, String memberName, String memberImage, Long memberId) throws Exception {
		if (file.isEmpty()) {
			String img = "";
			market.setFilepath(img);
		} else {
			uploadFile.fildUpload(market, file);
		}
		
		Member member = memberRepository.findMemberById(memberId);
		
		market.setMemId(memberId);
		market.setMemName(memberName);
		market.setMemImg(member.getImage());
		market.setStatus(ItemSellStatus.판매중);
		System.out.println(">>>>>>>>>> 판매중 : " + market.getStatus());
		marketRepository.save(market);
	}

	// 마켓 리스트
	public List<Market> maketList() {

		List<Market> list = marketRepository.findAllByOrderByIdDesc();

		return list;
	}

	// 마켓 상세 조회
	public Market marketDetail(Long id) {
		Optional<Market> optional = marketRepository.findById(id);
		if (optional.isPresent()) {
			Market market = optional.get();
			return market;
		} else {
			throw new NullPointerException();
		}
	}

	// 마켓 글 삭제
	public void deleteMarket(Long id) {
		marketRepository.deleteById(id);
	}

	// 마켓 조회 -> 페이지
	public Page<Market> findAll(PageRequest of) {
		return null;
	}

	// 마켓 구매하기
	public MarketDto getMarketDtl(Long marketId) {
		Market market = marketRepository.findById(marketId).orElseThrow(EntityNotFoundException::new);
		MarketDto dto = MarketDto.of(market);
		return dto;
	}

	// 마켓 구매 완료하기
	public void saveBuyMarket(@AuthenticationPrincipal PrincipalDetails principalDetails, Market market, Long id) {
		// Market -> void 임시로 막기
		Long memid = principalDetails.getMember().getId();
		market.setBuy_memId(memid);
		cashService.cashDeposit(memid, 20000);
		
		//return marketRepository.save(market);
	}
	
	// 마켓 결제(구매 대기) 
	public void buyMarket(@AuthenticationPrincipal PrincipalDetails principalDetails,Long id) {
		
		Long memId = principalDetails.getMember().getId();
		Market market = marketRepository.findById(id).orElseThrow(IllegalArgumentException::new);
		market.setBuy_memId(memId);
		String price = market.getPrice();
		cashService.cashDeposit(memId, Integer.parseInt(price));
		market.setStatus(ItemSellStatus.구매대기);
		
	}
	
	// 마켓 결제(구매 확정) 
	public void finalBuyMarket(Long id) {
		Market market = marketRepository.findById(id).orElseThrow(IllegalArgumentException::new);
		Long memId = market.getMemId();
		String price = market.getPrice();
		cashService.cashPlus(memId, Integer.parseInt(price));
		market.setStatus(ItemSellStatus.구매완료);
	}
	
	// 마켓 검색 필터
	public Page<Market> getSearchMarketPage(MarketSearchDto marketSearchDto, Pageable pageable){
		return marketRepository.getSearchMarketPage(marketSearchDto, pageable);
	}

}
