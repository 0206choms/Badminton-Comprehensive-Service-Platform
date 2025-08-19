//package com.kiwi.shop.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.annotation.*;
//
//import com.kiwi.shop.dto.OrderDto;
//import com.kiwi.shop.dto.OrderHistDto;
//import com.kiwi.shop.service.OrderService;
//
//import javax.validation.Valid;
//import java.security.Principal;
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//@RequiredArgsConstructor
//public class OrderController {
//
//    private final OrderService orderService;
//
//    @PostMapping(value = "/order")
//    public @ResponseBody ResponseEntity order (@RequestBody @Valid OrderDto orderDto, BindingResult bindingResult, Principal principal){
//
//        // 주문 정보를 받는 orderDto 객체에 데이터 바인딩시 에러가 있는지 검사
//        if(bindingResult.hasErrors()){
//            StringBuilder sb = new StringBuilder();
//            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
//            for (FieldError fieldError: fieldErrors) {
//                sb.append(fieldError.getDefaultMessage());
//            }
//            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);   // 에러정보를 ResponseEntity 객체에 담아서 반환
//        }
//
//        // principal 객체에서 현재 로그인한 회원의 이메일 정보 조회
//        String email = principal.getName();
//        Long orderId;
//        try{
//            // 화면으로 부터 넘어오는 주문 정보와 회원의 이메일을 이용하여 주문 로직 생성
//            orderId = orderService.order(orderDto, email);
//        }catch (Exception e){
//            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//        // 결과값으로 부터 생성된 주문번호와 요청이 성공했다는 http응답 상태코드를 반환
//        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
//    }
//
//    @GetMapping(value = {"/orders","/orders/{page}"})
//    public String orderHist(@PathVariable("page")Optional<Integer> page, Principal principal, Model model){
//        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0,4);
//
//        Page<OrderHistDto> orderHistDtoList = orderService.getOrderList(principal.getName(), pageable);
//
//        model.addAttribute("orders",orderHistDtoList);
//        model.addAttribute("page",pageable.getPageNumber());
//        model.addAttribute("maxPage",5);
//
//        return "order/orderHist";
//    }
//
//    @PostMapping("/order/{orderId}/cancel")
//    public @ResponseBody ResponseEntity cancelOrder (@PathVariable("orderId") Long orderId, Principal principal){
//
//        // 자바스크립트에서는 취소할 주문 번호가 조작이 가능하므로 주문 취소 권한을 백엔드 단에서 검사함
//        if(!orderService.validateOrder(orderId, principal.getName())){
//            return new ResponseEntity<String>("주문 취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
//        }
//
//        // 주문 취소 로직 호출
//        orderService.cancelOrder(orderId);
//        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
//    }
//}
