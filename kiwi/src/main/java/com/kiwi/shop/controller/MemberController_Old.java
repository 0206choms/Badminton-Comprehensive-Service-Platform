package com.kiwi.shop.controller;
//package com.kiwi.shop.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.kiwi.shop.dto.MemberFormDto;
//import com.kiwi.shop.entity.Member;
//import com.kiwi.shop.service.MemberService;
//
//import javax.validation.Valid;
//
//@RequestMapping("/members")
//@Controller
//@RequiredArgsConstructor
//public class MemberController {
//
//    @Autowired
//    private MemberService memberService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    // 회원 가입 로직
//    @GetMapping(value = "/new")
//    public String memberForm(Model model){
//        model.addAttribute("memberFormDto", new MemberFormDto());
//        return "member/memberForm"; 
//    }
//    
//    @PostMapping(value = "/new")
//    public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){
//        if(bindingResult.hasErrors()){
//            return "member/memberForm";
//        }
//        try{
//            Member member = Member.createMember(memberFormDto,passwordEncoder);
//            memberService.saveMember(member);
//        }
//        catch (IllegalStateException e){
//            model.addAttribute("errorMessage", e.getMessage());
//            return "member/memberForm";
//        }
//        return "redirect:/";
//    }
//
////    //로그인 로직
////    @GetMapping(value = "/login")
////    public String loginMember(){
////        return "/member/memberLoginForm";
////    }
//    //로그인 로직
//    @GetMapping(value = "/login")
//    public String loginMember(){
//        return "/member/memberLoginForm";
//    }
//    
//    
//    @GetMapping(value = "/login/error")
//    public String loginError(Model model){
//        model.addAttribute("loginErrorMsg","아이디 또는 비밀번호를 확인해주세요");
//        return "/member/memberLoginForm";
//    }
//
//
//
//
//
//}
