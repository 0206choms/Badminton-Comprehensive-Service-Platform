//package com.kiwi.shop.service;
//
//
//import lombok.RequiredArgsConstructor;
//import org.aspectj.weaver.ast.Or;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.thymeleaf.util.StringUtils;
//
//import com.kiwi.member.entity.Member;
//import com.kiwi.member.repository.MemberRepository;
//import com.kiwi.shop.dto.OrderDto;
//import com.kiwi.shop.dto.OrderHistDto;
//import com.kiwi.shop.dto.OrderItemDto;
////import com.kiwi.entity.*;
//import com.kiwi.shop.entity.Item;
//import com.kiwi.shop.entity.ItemImg;
//import com.kiwi.shop.entity.Order;
//import com.kiwi.shop.entity.OrderItem;
//import com.kiwi.shop.repository.ItemImgRepository;
//import com.kiwi.shop.repository.ItemRepository;
//import com.kiwi.shop.repository.OrderRepository;
//
//import javax.persistence.EntityNotFoundException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class OrderService {
//
//    private final ItemRepository itemRepository;
//    private final MemberRepository memberRepository;
//    private final OrderRepository orderRepository;
//    private final ItemImgRepository itemImgRepository;
//
//    public Long order(OrderDto orderDto, String email){
//        // 주문할 상품을 조회
//        Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);
//        // 현재 로그인한 회원의 이메일을 통해 회원 정보 조회
//        Member member = memberRepository.findByEmail(email);
//
//
//        List<OrderItem> orderItemList = new ArrayList<>();
//        // 주문할 상품 엔티티와 주문 수량을 통해 주문 상품 엔티티 생성
//        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
//        orderItemList.add(orderItem);
//
//        // 회원 정보와 주문 상품 리스트를 통해 주문 엔티티 생성
//        Order order = Order.createOrder(member, orderItemList);
//        // 생성한 주문 엔티티 저장
//        orderRepository.save(order);
//
//        return order.getId();
//    }
//
//    @Transactional(readOnly = true)
//    public Page<OrderHistDto> getOrderList(String email, Pageable pageable){
//
//        // 유저 아이디와 페이징 조건을 이용하여 주문 목록 조회
//        List<Order> orders = orderRepository.findOrders(email,pageable);
//        // 유저의 주문 총 개수 조회
//        Long totalCount = orderRepository.countOrder(email);
//
//        List<OrderHistDto> orderHistDtos = new ArrayList<>();
//
//        // 주문 목록으로 구매이력 dto 값 전달
//        for (Order order: orders) {
//            OrderHistDto orderHistDto = new OrderHistDto(order);
//            List<OrderItem> orderItems = order.getOrderItems();
//            for (OrderItem orderItem: orderItems) {
//                // 주문한 상품의 대표 이미지 조회
//                ItemImg itemImg = itemImgRepository.findByItemIdAndRepimgYn(orderItem.getItem().getId(),"Y");
//                OrderItemDto orderItemDto = new OrderItemDto(orderItem,itemImg.getImgUrl());
//                orderHistDto.addOrderItemDto(orderItemDto);
//            }
//            orderHistDtos.add(orderHistDto);
//        }
//        // 페이징 구현 객체 생성하여 반환
//        return new PageImpl<OrderHistDto>(orderHistDtos,pageable,totalCount);
//    }
//
//
//    @Transactional(readOnly = true)
//    // 현재 로그인한 사용자와 주문 데이터를 사용한 사용자 같은지 조회함 같으면 true 다르면 false
//    public boolean validateOrder(Long orderId, String email){
//        Member curMember = memberRepository.findByEmail(email);
//        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
//        Member savedMember = order.getMember();
//
//        if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())){
//            return false;
//        }
//        return true;
//    }
//
//    public void cancelOrder(Long orderId){
//        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
//        order.cancelOrder();    //  주문 취소 상태로 변경되면 변경 감지 기능에 의해 트랜잭션이 끝날때 update 쿼리가 실행됨
//    }
//
//
//    // 장바구니 상품 주문
//    public Long orders(List<OrderDto> orderDtoList, String email){
//        Member member = memberRepository.findByEmail(email);
//        List<OrderItem> orderItemList = new ArrayList<>();
//
//        // 주문할 상품 리스트 생성
//        for (OrderDto orderDto: orderDtoList) {
//            Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);
//
//            OrderItem orderItem = OrderItem.createOrderItem(item,orderDto.getCount());
//            orderItemList.add(orderItem);
//        }
//
//        // 현재 로그인한 회원과 주문 상품 목록을 이용하여 주문 엔티티 생성
//        Order order = Order.createOrder(member,orderItemList);
//        orderRepository.save(order);
//
//        return order.getId();
//    }
//}
