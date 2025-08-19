package com.kiwi.market.controller;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.kiwi.config.auth.PrincipalDetails;
import com.kiwi.market.dto.MarketDto;
import com.kiwi.market.dto.MarketSearchDto;
import com.kiwi.market.entity.Comment;
import com.kiwi.market.entity.Market;
import com.kiwi.market.entity.MarketLike;
import com.kiwi.market.repository.MarketRepository;
import com.kiwi.market.service.CommentService;
import com.kiwi.market.service.MarketLikeService;
import com.kiwi.market.service.MarketService;
import com.kiwi.match.dto.MatchSearchDto;
import com.kiwi.match.entity.Matchs;
import com.kiwi.member.constant.Address;
import com.kiwi.member.entity.Member;
import com.kiwi.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequestMapping("/market")
@Controller
@RequiredArgsConstructor
public class MarketController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	private final MarketService marketService;
	private final CommentService commentService;

	@Value("${marketImgLocation}")
	private String marketImgLocation;

	@Autowired
	private MarketRepository marketRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MarketLikeService mlService;

	// 마켓 좋아요 기능
	@ResponseBody
	@PostMapping("/marketLike")
	public void marketLike(String id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println(">>>>>>>>>>>>>>> ajax로 넘겨온 id  : " + id);
		Long marketId = Long.parseLong(id);

		Long memberId = principalDetails.getMember().getId();
		Long marketLikeId = (long)0;
		boolean count = false;  // 좋아요를 눌렀는지 확인

		List<MarketLike> list = mlService.marketLike();
//		System.out.println(">>>>>>>>>>>> list" + list);
		
		// 이미 좋아요가 눌러진 경우
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getMarketId().getId().equals(marketId)) { // 마켓 ID가 DB에 있는 경우 -> 이미 좋아요를 누른 경우
				if (list.get(i).getMemId() == memberId) { // 마켓ID와 멤버 ID도 모두 동일 한 경우
					count = true; 
//					System.out.println(">>>>>>>>>>>>>>> " + count + marketId + "이미 좋아요 눌렀습니다.");
					marketLikeId = list.get(i).getId();
					// 좋아요 삭제 하는 기능 추가
					mlService.deleteMarketLike(marketLikeId);
//					System.out.println(">>>>>>>>>>>>>>>>>>> : " + marketLikeId + "좋아요를 취소하였습니다.");
				} 
			} 
		}
		
		// 좋아요 저장
		if(!count) {
//			System.out.println(">>>>>>>>>>>>>>>>>>>>> 좋아요 완료" + count);
			MarketLike ml = MarketLike.createML(memberId);
			marketService.saveMarketLike(ml, marketId, memberId);
		}
	}

	// 마켓 글 작성 페이지
	@GetMapping(value = "/marketNew")
	public String market(Model model) {
		model.addAttribute("marketDto", new MarketDto());
		model.addAttribute("local", Address.values());
		return "market/marketForm";
	}

	@PostMapping(value = "/marketNew")
	public String marketNew(@Valid MarketDto marketDto, BindingResult bindingResult, Model model, MultipartFile file,
			@AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception {
		if (bindingResult.hasErrors()) {
			System.out.println("-------------------->바인딩에러");
			return "market/marketForm";
		}

		try {
			Market market = Market.createMarket(marketDto);
			String memberName = principalDetails.getMember().getName();
			String memberImage = principalDetails.getMember().getImage();
			Long memberId = principalDetails.getMember().getId();

			marketService.saveMarket(market, file, memberName, memberImage, memberId);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
			return "market/marketForm";
		}
		// 상품이 정상적으로 등록되었다면 메인 페이지로 이동
		return "redirect:/market/marketList";
	}

	@PostMapping(value = "/image/upload")
	public ModelAndView image(MultipartHttpServletRequest request) throws Exception {
		// ckeditor는 이미지 업로드 후 이미지 표시하기 위해 uploaded 와 url을 json 형식으로 받아야 함
		// modelandview를 사용하여 json 형식으로 보내기위해 모델앤뷰 생성자 매개변수로 jsonView 라고 써줌
		// jsonView 라고 쓴다고 무조건 json 형식으로 가는건 아니고 @Configuration 어노테이션을 단
		// WebConfig 파일에 MappingJackson2JsonView 객체를 리턴하는 jsonView 매서드를 만들어서 bean으로 등록해야
		// 함
		ModelAndView mav = new ModelAndView("jsonView");

		// ckeditor 에서 파일을 보낼 때 upload : [파일] 형식으로 해서 넘어오기 때문에 upload라는 키의 밸류를 받아서
		// uploadFile에 저장함
		MultipartFile uploadFile = request.getFile("upload");

		// 파일의 오리지널 네임
		String originalFileName = uploadFile.getOriginalFilename();

		// 파일의 확장자
		String ext = originalFileName.substring(originalFileName.indexOf("."));

		// 서버에 저장될 때 중복된 파일 이름인 경우를 방지하기 위해 UUID에 확장자를 붙여 새로운 파일 이름을 생성
		String newFileName = UUID.randomUUID() + ext;

		// 현재경로/upload/파일명이 저장 경로
		String savePath = marketImgLocation + newFileName;

		// 브라우저에서 이미지 불러올 때 절대 경로로 불러오면 보안의 위험 있어 상대경로를 쓰거나 이미지 불러오는 jsp 또는 클래스 파일을 만들어
		// 가져오는 식으로 우회해야 함
		// 때문에 savePath와 별개로 상대 경로인 uploadPath 만들어줌
		String uploadPath = "/market/image/upload/" + newFileName;

		// 저장 경로로 파일 객체 생성
		File file = new File(savePath);

		// 파일 업로드
		uploadFile.transferTo(file);

		// uploaded, url 값을 modelandview를 통해 보냄
		mav.addObject("uploaded", true); // 업로드 완료
		mav.addObject("url", uploadPath); // 업로드 파일의 경로

		return mav;
	}

	// 마켓 리스트
//	@GetMapping("/marketList")
//	public String marketList(Model model) {
//		List<Market> list = marketService.maketList();
//		model.addAttribute("list", list);
//		System.out.println(">>>>>>>>>>>>>>>>>>>>> market list : " + list);
//		return "/market/marketList";
//	}

//	// 마켓 리스트 - 페이지
//	@GetMapping("/marketList")
//	public String marketList(Model model,@PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,@RequestParam(required = false, defaultValue = "") String searchText) {
//		
//		Page<Market> list = marketRepository.findByTitleContainingOrDetailContaining(searchText, searchText, pageable);
//		int startPage = Math.max(1, list.getPageable().getPageNumber() - 7);
//		int endPage = Math.min(list.getTotalPages(), list.getPageable().getPageNumber() + 7);
//
//		model.addAttribute("startPage", startPage);
//		model.addAttribute("endPage", endPage);
//		model.addAttribute("markets", list);
//
//		model.addAttribute("list", list);
//		model.addAttribute("local", Address.values());
//		return "market/marketList";
//
//	}
	
	// 마켓 리스트 - 페이지
	@GetMapping({"/marketList","/marketList/{page}"})
	public String marketList(MarketSearchDto marketSearchDto,Model model,@PathVariable("page") Optional<Integer> page) {
		
//		Page<Market> list = marketRepository.findByTitleContainingOrDetailContaining(searchText, searchText, pageable);
//		int startPage = Math.max(1, list.getPageable().getPageNumber() - 7);
//		int endPage = Math.min(list.getTotalPages(), list.getPageable().getPageNumber() + 7);
//
//		model.addAttribute("startPage", startPage);
//		model.addAttribute("endPage", endPage);
//		model.addAttribute("markets", list);
//
//		model.addAttribute("list", list);
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 8);
		Page<Market> markets = marketService.getSearchMarketPage(marketSearchDto, pageable);
		model.addAttribute("list", markets);
		model.addAttribute("matchSearchDto", marketSearchDto);
		model.addAttribute("maxPage", 8);
		model.addAttribute("local", Address.values());
		return "market/marketList";

	}
	
	// 마켓 상세 페이지
	@GetMapping("/marketDetail/{id}")
	public String marketDetail(@PathVariable("id") Long id, Model model,@AuthenticationPrincipal PrincipalDetails principalDetails) {

		if (principalDetails == null) {
			principalDetails.getMember().setId(Long.valueOf(0));
//			System.out.println(principalDetails.getMember().getId());
		}

//		System.out.println(principalDetails);
		Long memberId = principalDetails.getMember().getId();
		Market market = marketService.marketDetail(id);
		model.addAttribute("market", market);
//		System.out.println(">>>>>>>>>>>> marketId: " + market.getId());
		model.addAttribute("marketDto", new MarketDto());

		// System.out.println(">>>>>>>>>>> : " + new MarketDto());
		model.addAttribute("memberId", memberId); // 현재 로그인 한 ID

		List<Comment> list = commentService.commentList();
		model.addAttribute("list", list);
		
		String level = principalDetails.getMember().getLevel();
		model.addAttribute("level", level);
		// ----------- 좋아요 확인 ----------- 
		int counts = 0;  // 좋아요를 눌렀는지 확인

		List<MarketLike> lists = mlService.marketLike();
		
		// 이미 좋아요가 눌러진 경우
		for (int i = 0; i < lists.size(); i++) {
			if (lists.get(i).getMarketId().getId().equals(id)) { // 마켓 ID가 DB에 있는 경우 -> 이미 좋아요를 누른 경우
				if (lists.get(i).getMemId().equals(memberId)) { // 마켓ID와 멤버 ID도 모두 동일 한 경우
					counts += 1; // 좋아요 누른 경우
//					System.out.println(">>>>>>>>>>>>>>>>>>> : " + marketLikeId + "좋아요를 취소하였습니다.");
				} 
			} 
		}
		
		model.addAttribute("likeStatus", counts);
		System.out.println(">>>>>>> 좋아요 상태 : " + counts);

		return "market/marketDetail";

	}

	// 마켓 수정 페이지
	@GetMapping("/marketUpdate/{id}")
	public String mDetail(@PathVariable("id") Long id, Model model,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		Long memberId = principalDetails.getMember().getId();
		Market market = marketService.marketDetail(id);
		model.addAttribute("memberId", memberId); // 현재 로그인 한 ID
		model.addAttribute("market", market);
		model.addAttribute("local", Address.values());
		return "market/marketUpdate";
	}

	// 마켓 수정 페이지
	@PostMapping(value = "/marketUpdate/{id}")
	public String marketUpdate(Market market, MultipartFile file, @AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception {
		
		String memberName = principalDetails.getMember().getName();
		String memberImage = principalDetails.getMember().getImage();
		Long memberId = principalDetails.getMember().getId();

		marketService.saveMarket(market, file, memberName, memberImage, memberId);

		return "redirect:/market/marketList";
	}

	// 마켓 삭제 페이지
	@GetMapping(value = "/marketDelete/{id}")
	public String marketDelete(@PathVariable("id") Long id) {
		marketService.deleteMarket(id);
		return "redirect:/market/marketList";
	}

	// 마켓 구매 페이지
	@GetMapping("/pay")
	public String marketBuy(Market market, Model model) {
		return "pay/marketBuy";
	}

	// 마켓 구매 페이지
	@PostMapping("/pay")
	public String marketBuy(MarketDto marketDto, Model model,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		// 캐시 조회
//		System.out.println("==============>" + marketDto.getId());
		Long id = principalDetails.getMember().getId();
		Member member = memberRepository.findMemberById(id);
		int money = member.getKiwicash();
//		System.out.println("==================>" + money);
		model.addAttribute("money", money);

		// marketDto.marketBuy(marketDto, id);

		marketDto.marketBuy(marketDto, id);
		model.addAttribute("marketDto", marketDto);
//		System.out.println(marketDto.getTitle() + " ,  " + marketDto.getPrice());

		return "pay/marketBuy";
	}

	// 마켓 결제(구매 대기)
	@PostMapping("/pay/result")
	@ResponseBody
	public void payResult(Long id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		marketService.buyMarket(principalDetails, id);
	}

	// 마켓 결제(구매 완료)
	@PostMapping("/pay/finalResult")
	@ResponseBody
	public void payFinal(Long id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		marketService.finalBuyMarket(id);
	}
}