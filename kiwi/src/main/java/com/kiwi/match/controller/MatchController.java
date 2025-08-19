package com.kiwi.match.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kiwi.config.auth.PrincipalDetails;
import com.kiwi.court.entity.Reservation;
import com.kiwi.court.repository.ReservationRepository;
import com.kiwi.match.constant.Status;
import com.kiwi.match.dto.MatchDto;
import com.kiwi.match.dto.MatchSearchDto;
import com.kiwi.match.dto.MatchsReservationDto;
import com.kiwi.match.entity.Matchs;
import com.kiwi.match.entity.MatchsReservation;
import com.kiwi.match.repository.MatchRepository;
import com.kiwi.match.service.MatchService;
import com.kiwi.match.service.MatchsReservationService;
import com.kiwi.member.entity.Member;
import com.kiwi.member.repository.MemberRepository;
import com.kiwi.pay.service.CashService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/match")
@Controller
@RequiredArgsConstructor
public class MatchController {

	private final MatchService matchService;

	@Autowired
	private MatchRepository matchRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MatchsReservationService mrService;

	@Autowired
	private CashService cashservice;

	private Long id;

	// 매치 메인 - 매치 리스트
//	@GetMapping("/matchList")
//	public String matchList(Model model) {
//		//String courtName = matchService.courtTest(1L);
//		
//		List<Matchs> list =  matchService.matchList();
//		model.addAttribute("list", list);
//		
//		return "match/matchList";
//	}

//	// 매치 메인 - 매치 리스트 (페이징)
//	@GetMapping("/matchList")
//	public String matchList(Model model,@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,@RequestParam(required = false, defaultValue = "") String searchText) {
//		System.out.println("================> : "+searchText);
//		Page<Matchs> list = matchRepository.findByRetimeContaining(searchText, pageable);
//		int startPage = Math.max(1, list.getPageable().getPageNumber() - 4);
//		int endPage = Math.min(list.getTotalPages(), list.getPageable().getPageNumber() + 4);
//		model.addAttribute("startPage", startPage);
//		model.addAttribute("endPage", endPage);
//		model.addAttribute("list", list);
//
//		return "match/matchList";
//	}
	
	// 매치 메인 - 매치 리스트 (페이징)
	@GetMapping({"/matchList", "/matchList/{page}"})
	public String matchList(MatchSearchDto matchSearchDto,Model model, @PathVariable("page") Optional<Integer> page) {
		//matchSearchDto.setSearchDate(searchText);
		if(matchSearchDto.getSearchDate() == "") {
			matchSearchDto.setSearchDate(null);
		}
		
		System.out.println("====================> date : " + matchSearchDto.getSearchDate());
		System.out.println("====================> Level : " + matchSearchDto.getSearchLevel());
		System.out.println("====================> status : " + matchSearchDto.getSearchStatus());
		System.out.println("====================> Query : " + matchSearchDto.getSearchQuery());
		
		
		
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
		Page<Matchs> matchs = matchService.getSearchMatchPage(matchSearchDto, pageable);
		

		
		model.addAttribute("list", matchs);
		model.addAttribute("matchSearchDto", matchSearchDto);
		model.addAttribute("maxPage", 5);
		
//		Page<Matchs> list = matchRepository.findByRetimeContaining(searchText, pageable);
//		int startPage = Math.max(1, list.getPageable().getPageNumber() - 4);
//		int endPage = Math.min(list.getTotalPages(), list.getPageable().getPageNumber() + 4);
//		model.addAttribute("startPage", startPage);
//		model.addAttribute("endPage", endPage);
//		model.addAttribute("list", list);

		return "match/matchList";
	}

	// 매치 디테일
	@GetMapping("/matchDetail/{id}")
	public String matchDetail(@PathVariable("id") Long id, Model model) {
		Matchs matchs = matchService.matchDetail(id);
		model.addAttribute("matchs", matchs);
		model.addAttribute("matchsReservationDto", new MatchsReservationDto());
		return "match/matchDetail";
	}

	// 매치 신청하기
	@GetMapping("/matchsReservation")
	public String mrSave(@Valid MatchsReservationDto matchsReservationDto,
			@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

		Long mathcshId = matchsReservationDto.getMathcshId();
		Matchs matchs = matchRepository.findById(mathcshId).orElseThrow();
		model.addAttribute("matchs", matchs);
		model.addAttribute("matchsReservationDto", matchsReservationDto);

		Long id = principalDetails.getMember().getId();
		Member member = memberRepository.findMemberById(id);
		int money = member.getKiwicash();
		model.addAttribute("money", money);

		return "pay/matchBuy";
	}

	// 매치 개설하기
//	@GetMapping("/matchNew")
//	public String matchNew(Model model) {
//		model.addAttribute("matchDto", new MatchDto());
//		return "match/matchForm";
//	}

	// 매치 개설하기
	@GetMapping("/matchNew")
	public String matchNew(@AuthenticationPrincipal PrincipalDetails principalDetails, @Valid MatchDto matchDto,
			Model model) {
		Long memberId = principalDetails.getMember().getId();
		List<Reservation> list = matchService.matchsCourt();
		model.addAttribute("list", list);
		model.addAttribute("memberId", memberId);

		int count = 0;
		Long reservationId = (long) 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getMember().getId().equals(memberId)) { // 예약 했을 경우
				count += 1; // 예약한 건 수 : count

				reservationId = list.get(i).getId(); // 해당하는 멤버의 예약 아이디 반환
				System.out.println(">>>>>>>>>>>>>>>>>> reservationId : " + reservationId);
				model.addAttribute("reservationId" + count, reservationId); // 각각의 예약 id반환하기 위해서 reservatuonId1 ,, 2,,
																			// 이런식으로 줌
			}
		}
		System.out.println("=======================> count : " + count);
		model.addAttribute("count", count);
		// model.addAttribute("reservationId", reservationId);

		return "match/matchForm";
	}

	// 매치 개설
	@PostMapping("/matchNew")
	public String matchNewPost(@Valid MatchDto matchDto, Model model,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		Long memberId = principalDetails.getMember().getId();
		Matchs match = Matchs.createMatch(matchDto);
		matchService.saveMatch(match, memberId, matchDto.getReser_id());
		
		String id = match.getId() + "";		
		
		matchBuyResult(id, model, principalDetails); // 매치 개설 후 바로 그 건에 대해 매치 신청도 같이 진행
		return "redirect:/match/matchList";
	}

	// 매치 신청 완료 페이지
	@ResponseBody
	@PostMapping("/pay/result")
	public void matchBuyResult(String id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		// System.out.println("ajax로 넘겨온 id : " + id);

		System.out.println(">>>>>>>>>>>>>>> id  : " + id);
		Long mathcshId = Long.parseLong(id);

		// 매치 신청 하기
		Long memberId = principalDetails.getMember().getId();

		MatchsReservation mr = MatchsReservation.createMR(memberId);
		matchService.saveMatchsReservation(mr, mathcshId, memberId);

		// 매치 신청 마감 확인
		List<MatchsReservation> list = mrService.mrCourt();

		int count = 0; // 1vs1단식일 경우 참가할 수 있는 사람은 2명
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getMathshId().getId() == mathcshId) { // 매치 ID가 DB에 있는 경우 -> 매치를 신청한 경우
				count += 1; // 매치 신청 건 수 카운트
			}
		}

		if (mr.getMathshId().getCount() <= count) {
			mr.getMathshId().setStatus(Status.마감);

			Long reservation = mr.getMathshId().getReservation().getId();
			Matchs match = mr.getMathshId();
			matchService.saveMatch(match, memberId, reservation);
			cashservice.cashDeposit(memberId, 5000);
		}
	}
}
