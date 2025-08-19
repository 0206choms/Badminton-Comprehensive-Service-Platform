package com.kiwi.member.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kiwi.config.auth.PrincipalDetails;
import com.kiwi.court.dto.ReservationDto;
import com.kiwi.court.entity.Court;
import com.kiwi.court.entity.Reservation;
import com.kiwi.market.entity.MarketLike;
import com.kiwi.market.service.MarketLikeService;
import com.kiwi.match.dto.MatchsReservationDto;
import com.kiwi.match.entity.Matchs;
import com.kiwi.match.entity.MatchsReservation;
import com.kiwi.match.service.MatchService;
import com.kiwi.match.service.MatchsReservationService;
import com.kiwi.member.constant.Address;
import com.kiwi.member.constant.Bank;
import com.kiwi.member.constant.Gender;
import com.kiwi.member.dto.MemberFormDto;
import com.kiwi.member.dto.MemberUpdateDto;
import com.kiwi.member.dto.OauthAddInfoDto;
import com.kiwi.member.entity.Member;
import com.kiwi.member.repository.MemberRepository;
import com.kiwi.member.service.MemberService;
import com.kiwi.pay.entity.Cash;
import com.kiwi.pay.repository.CashRepository;
import com.kiwi.pay.service.CashService;

import groovyjarjarantlr4.v4.parse.ANTLRParser.finallyClause_return;
import lombok.RequiredArgsConstructor;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CashRepository cashRepository;

	@Value("${profileImgLocation}")
	private String profileImgLocation;

	private final MatchService matchService;

	private final MatchsReservationService matchsReservationService;

	private final CashService cashService;

	private final MarketLikeService likeService;

	// 회원 가입 로직
	@GetMapping(value = "/new")
	public String memberForm(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		model.addAttribute("genders", Gender.values());
		model.addAttribute("bnames", Bank.values());
		model.addAttribute("local", Address.values());
		return "member/memberForm";
	}

	@PostMapping(value = "/new")
	public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			System.out.println("============================바인딩에러");
			model.addAttribute("genders", Gender.values());
			model.addAttribute("bnames", Bank.values());
			model.addAttribute("local", Address.values());
			return "member/memberForm";
		}
		try {
			Member member = Member.createMember(memberFormDto, passwordEncoder);
			memberService.saveMember(member);
			model.addAttribute("Message", "회원가입이 완료되었습니다.");
		} catch (IllegalStateException e) {
			model.addAttribute("genders", Gender.values());
			model.addAttribute("bnames", Bank.values());
			model.addAttribute("local", Address.values());
//			System.out.println("에러 메시지 전이야!");
			model.addAttribute("errorMessage", e.getMessage());
//			System.out.println("에러 메시지 후야!");
			return "member/memberForm";
		}
		return "redirect:/members/login";
	}

	// 로그인 로직
	@GetMapping(value = "/login")
	public String loginMember() {
		return "member/memberLoginForm";
	}

	@GetMapping(value = "/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
		return "member/memberLoginForm";
	}

	// 마이페이지
	@GetMapping("/mypage")
	public String mypage(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		Member member = memberService.mypageInfo(principalDetails);
		// System.out.println("================> 캐시 금액 : " +
		// cashRepository.amountSum(member)); // 추후 수정
		// member.setKiwicash(cashRepository.amountSum(member));

//		Integer userCash = cashRepository.amountSum(member);
//		if(cashRepository.amountSum(member) != null) {
//			System.out.println("=================> 0") ;
//		}else {
//			System.out.println("================> tt");
//		}

		model.addAttribute("member", member);
		return "mypage/mypageMain";
	}

	// 마이페이지 - 좋아요목록
	@GetMapping("/mypage/marketLike")
	public String mypageMarketLike(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		Long memberId = principalDetails.getMember().getId();

		List<MarketLike> lists = likeService.marketLike();
		model.addAttribute("lists", lists);
		model.addAttribute("memberId", memberId);
//			System.out.println(">>>>>>>>>>>>>>>>> market : " + lists.get(0).getMarketId());
//			System.out.println(">>>>>>>>>>>>>>>>> title : " + lists.get(0).getMarketId().getTitle());

		int counts = 0;
		Long marketLikeId = (long) 0;
		for (int i = 0; i < lists.size(); i++) {
			if (lists.get(i).getMemId().equals(memberId)) { // 좋아요 했을 경우
				counts += 1; // 예약한 건 수 : count

				marketLikeId = lists.get(i).getId(); // 해당하는 멤버의 좋아요 아이디 반환
				model.addAttribute("marketLikeId" + counts, marketLikeId); // 각각의 예약 id반환하기 위해서 reservatuonId1 ,,
																			// 2,,
																			// 이런식으로 줌
			}
		}
		model.addAttribute("count", counts);
		System.out.println(">>>>>>>>>> count : " + counts);

		return "mypage/mypageMarketLike";
	}

	// 마이페이지 - 충전내역
	@GetMapping("/mypage/pay")
	public String mypagePay(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		// 키위캐시 금액 표시, 충전버튼 관련
		Member member = memberService.mypageInfo(principalDetails);
		model.addAttribute("member", member);

		// 충전내역 표시
		Long memberId = principalDetails.getMember().getId();
		List<Cash> list = cashService.cash();
		model.addAttribute("list", list);
		model.addAttribute("memberId", memberId);
//		System.out.println(">>>>>>>>>>>>>>>>> pay : " + list.get(0).getAmount() + ", " + list.get(0).getTime());

		int counts = 0;
		Long payId = (long) 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getMember().getId().equals(memberId)) { // 충전 했을 경우
				counts += 1; // 충전한 건 수 : count

				payId = list.get(i).getId(); // 해당하는 멤버의 충전 아이디 반환
				model.addAttribute("mreservationId" + counts, payId); // 각각의 충전 id반환하기 위해서 payId1 ,,
																		// 2,, 이런식으로 줌
			}
		}
		model.addAttribute("count", counts);

		return "mypage/mypagePay";
	}

	// 마이페이지 - 매치 평가 /mypage/reservationMatchs
	@PostMapping("/mypage/reservationMatchs")
	public String MatchAppraisal(MatchsReservationDto matchsReservationDto, MatchsReservation matchsReservation,
			Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		// 승리일경우 매치 상대방 + 1
		// 매너점수 1점당 + 20점

		System.out.println("======================>매너점수 : " + matchsReservationDto.getManners());
		System.out.println("======================> 매치신청ID : " + matchsReservationDto.getId());
		System.out.println("======================> 승리여부 : " + matchsReservationDto.getWinYN());
		System.out.println("======================> 매치ID : " + matchsReservationDto.getMathcshId());

		Long matchId = matchsReservationDto.getMathcshId(); // 매치 평가할 매치ID

		Long memberId = principalDetails.getMember().getId(); // 현재 로그인 한 ID

		// 매치 신청 내역 중 매치 ID가 같을 경우 같이 매치 경기 진행,
		List<MatchsReservation> lists = matchsReservationService.mrCourt(); // 매치신청 리스트

		Long matchMemId = (long) 0;
		int manner = 0; // 상대방에게 입력될 매너점수
		Long win = (long) 0;
		for (int i = 0; i < lists.size(); i++) {
			System.out.println("matchId : " + matchId);
			System.out.println("lists.get(i).getMathshId().getId() : " + lists.get(i).getMathshId().getId());
			if (lists.get(i).getMathshId().getId().equals(matchId)) { // 같이 매치 경기를 한 경우
				System.out.println("if문 접근");
				matchMemId = lists.get(i).getMemId(); // 같이 경기한 멤버의 ID - 본인도 포함될거임

				if (matchMemId.equals(memberId)) { // 경기한 멤버가 본인일 경우
					System.out.println("본인 예약 정보");

				} else {
					// 매너점수
					System.out.println(">>>>>>>>> 매너점수 확인 : " + matchsReservationDto.getManners());
					if (matchsReservationDto.getManners() == 1) {
						manner = 20;
					} else if (matchsReservationDto.getManners() == 2) {
						manner = 40;
					} else if (matchsReservationDto.getManners() == 3) {
						manner = 60;
					} else if (matchsReservationDto.getManners() == 4) {
						manner = 80;
					} else if (matchsReservationDto.getManners() == 5) {
						manner = 100;
					} else if (matchsReservationDto.getManners() == 0) {
						manner = 0;
					}

					System.out.println("최종 입력될 매너점수 : " + manner);

					// 승리점수
					if (matchsReservationDto.getWinYN().equals("win,")) {
						win += 1; // 패배일경우 0
					}
					memberService.saveMatchs(matchMemId, win, manner);
					System.out.println(">>>>>>>>>>>> matchMemId : " + matchMemId);
					System.out.println(">>>>>>>>>>>> win : " + win);
					System.out.println(">>>>>>>>>>>> manner : " + manner);
				}

			}
		}
//		model.addAttribute("count", counts);
//
//		System.out.println(">>>>>>>>>>>>>>>>> 경기 상대 : " + matchsReservationDto.getMathshId().get);
//		System.out.println("==================> 승리 여부 : " + matchsReservationDto.getWin());
//		System.out.println("==================> name : " + matchsReservationDto.getCourt_name());
//		System.out.println("==================> time : " + matchsReservationDto.getCourt_time());
//		System.out.println("==================> shuttlecock : " + matchsReservationDto.getShuttlecock());
//		System.out.println("==================> racket : " + matchsReservationDto.getRacket());
//		System.out.println("==================> btnNum : " + matchsReservationDto.getBtnNum());
//		System.out.println("==================> SearchText : " + matchsReservationDto.getSearchText());
//
//		Long id = principalDetails.getMember().getId();
//		Member member = memberRepository.findMemberById(id);
//		int money = member.getKiwicash();
//		System.out.println("==================>" + money);
//		model.addAttribute("money", money);
//
//		reservationDto.subReserInfo(reservationDto, reservationDto.getReservation_info(),
//				principalDetails.getUsername());
//		model.addAttribute("reservationDto", reservationDto);
//
//		Long courtId = Long.parseLong(reservationDto.getCourt_id());
//		Court court = courtRepository.findById(courtId).orElseThrow();
//		model.addAttribute("court", court);

		return "redirect:/members/mypage";
	}

//	@GetMapping("/mypage/reservationMatchs")
//	public String GetMatchAppraisal(MatchsReservation matchsReservationDto, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
//		model.addAttribute("matchsReservationDto", matchsReservationDto);
//
//		return "mypage/mypageReservation";
//	}
	// 마이페이지 - 신청 내역
	@GetMapping("/mypage/reservation")
	public String mypageReservation(MatchsReservationDto matchsReservationDto,
			@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		model.addAttribute("matchsReservationDto", matchsReservationDto);
		Long memberId = principalDetails.getMember().getId();
		// 매치 신청 부분
		List<MatchsReservation> lists = matchsReservationService.mrCourt();
		model.addAttribute("lists", lists);
		model.addAttribute("memberId", memberId);
//		System.out.println(">>>>>>>>>>>>>>>>> pay : " + lists.get(0).getPay_time());

		int counts = 0;
		Long mreservationId = (long) 0;
		for (int i = 0; i < lists.size(); i++) {
			if (lists.get(i).getMemId().equals(memberId)) { // 예약 했을 경우
				counts += 1; // 예약한 건 수 : count

				mreservationId = lists.get(i).getId(); // 해당하는 멤버의 예약 아이디 반환
				model.addAttribute("mreservationId" + counts, mreservationId); // 각각의 예약 id반환하기 위해서 reservatuonId1 ,,
																				// 2,,
																				// 이런식으로 줌
			}
		}
		model.addAttribute("count", counts);

		// 코트 예약 부분
		List<Reservation> list = matchService.matchsCourt();
		model.addAttribute("list", list);
		model.addAttribute("memberId", memberId);

		int count = 0;
		Long reservationId = (long) 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getMember().getId().equals(memberId)) { // 예약 했을 경우
				count += 1; // 예약한 건 수 : count

				reservationId = list.get(i).getId(); // 해당하는 멤버의 예약 아이디 반환
				model.addAttribute("reservationId" + count, reservationId); // 각각의 예약 id반환하기 위해서 reservatuonId1 ,, 2,,
																			// 이런식으로 줌
			}
		}
		model.addAttribute("count", count);

		return "mypage/mypageReservation";
	}

	// 마이페이지 - 프로필 설정
	@GetMapping("/mypage/profile")
	public String mypageProfile(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		Member member = memberService.mypageInfo(principalDetails);
		model.addAttribute("member", member);
		model.addAttribute("bnames", Bank.values());
		model.addAttribute("local", Address.values());
		model.addAttribute("memberUpdateDto", new MemberUpdateDto());
		return "mypage/mypageProfile";
	}
 
	@PostMapping("/mypage/profile/insert_image")
	public String image_insert(@AuthenticationPrincipal PrincipalDetails principalDetails,MemberUpdateDto memberUpdateDto, HttpServletRequest request, Model model) throws Exception {
		
		Member member = memberService.mypageInfo(principalDetails);

		// 파일의 오리지널 네임
		//String originalFileName = mFile.getOriginalFilename();
		//String ext = originalFileName.substring(originalFileName.indexOf("."));
		String originalFileName = memberUpdateDto.getFilename().getOriginalFilename();
		
		
		// 이미지 수정 안 하는 경우
		if(originalFileName.equals("")) {
			memberService.updateProfile(member, memberUpdateDto);
		} 
		// 이미지 수정 하는 경우
		else {
			String ext = originalFileName.substring(originalFileName.indexOf("."));
			String newFileName = UUID.randomUUID() + ext;
			String uploadPath = "/members/mypage/profile/" + newFileName;

			try {
				if (member.getImage() != null) { // 이미 프로필 사진이 있을경우
					File file = new File(profileImgLocation + member.getImage()); // 경로 + 유저 프로필사진 이름을 가져와서
					file.delete(); // 원래파일 삭제
				}
				//mFile.transferTo(new File(profileImgLocation + newFileName)); // 경로에 업로드
				memberUpdateDto.getFilename().transferTo(new File(profileImgLocation + newFileName)); // 경로에 업로드
			} catch (IOException e) {
				e.printStackTrace();
			}
			memberService.updateImage(member, uploadPath,memberUpdateDto);
		}
		
		
		return "redirect:/members/mypage";
	}

	// 소셜로그인 추가정보
	@GetMapping("/login/addInfo")
	public String addInfo(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		model.addAttribute("oauthAddInfoDto", new OauthAddInfoDto());
		model.addAttribute("bnames", Bank.values());
		model.addAttribute("local", Address.values());
		return "member/memberAddInfo";
	}

	// 소셜로그인 추가정보 등록
	@PostMapping("/login/addInfo")
	public String addInfo(@AuthenticationPrincipal PrincipalDetails principalDetails, OauthAddInfoDto oauthAddInfoDto,
			Model model) {
		memberService.addInfo(principalDetails, oauthAddInfoDto);
		return "redirect:/";
	}

	// form로그인 테스트
	@GetMapping("/test/login")
	@ResponseBody
	public String testLogin(Authentication authentication,
			@AuthenticationPrincipal PrincipalDetails principalDetails2) {
//		System.out.println("/test/login ===============");
		// PrincipalDetail
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//		System.out.println("authentication : " + principalDetails.getMember());
//		System.out.println("userDetails : " + principalDetails2.getMember());
		return "세션 정보 확인하기";
	}

	// 소셜로그인 테스트
	@GetMapping("/test/oauth/login")
	@ResponseBody
	public String testLogin(Authentication authentication, @AuthenticationPrincipal OAuth2User oauth) {
//		System.out.println("/test/oauth/login ===============");
		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
//		System.out.println("authentication : " + oAuth2User.getAttributes());
//		System.out.println("OAuth2User : " + oauth.getAttributes());

		return "OAuth 세션 정보 확인하기";
	}

}
