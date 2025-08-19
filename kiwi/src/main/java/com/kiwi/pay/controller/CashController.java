package com.kiwi.pay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kiwi.config.auth.PrincipalDetails;
import com.kiwi.member.service.MemberService;
import com.kiwi.pay.entity.Cash;
import com.kiwi.pay.service.CashService;

@RequestMapping("/cash")
@Controller
public class CashController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private CashService cashService;
	
	// 키위 캐시 충전 페이지
	@GetMapping(value = "/charge")
	public String payCharge(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		Long id = memberService.getIdFromAuth(principalDetails);
		int cash = cashService.cashSearch(id);
		model.addAttribute("cash",cash);
		return "pay/charge";
	}
	 
	
	
	// 키위 캐시 충전 
	@GetMapping("/pay/charge")
	@ResponseBody
	public void pay(@AuthenticationPrincipal PrincipalDetails principalDetails, int amount, String merchant_uid, String imp_uid,String apply_num) {
//		System.out.println("============ 결제 금액 : " + amount);
//		System.out.println("============ 상점 거래 ID : " + merchant_uid);
//		System.out.println("============ 고유ID : " + imp_uid);
//		System.out.println("============ 카드 승인번호 : " + apply_num);
		Long id = memberService.getIdFromAuth(principalDetails); 
		System.out.println("==================>"+ id);
		cashService.chargeCash(new Cash(amount,merchant_uid,imp_uid,apply_num), id);
		cashService.cashMapping(id,amount);
	}

}
