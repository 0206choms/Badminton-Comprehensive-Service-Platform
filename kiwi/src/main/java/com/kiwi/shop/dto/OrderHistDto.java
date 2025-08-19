//package com.kiwi.shop.dto;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.kiwi.constant.OrderStatus;
//import com.kiwi.shop.entity.Order;
//
//@Getter @Setter
//public class OrderHistDto {
//
//    // 생성자로 order 객체를 파라미터로 받아서 멤버 변수값을 세팅함. 주문날짜의 경우 포맷을 수정해서 이쁘게 출력함
//    public OrderHistDto(Order order) {
//        this.orderId = order.getId();
//        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
//        this.orderStatus = order.getOrderStatus();
//    }
//
//    private Long orderId;   // 주문 아이디
//
//    private String orderDate;   // 주문 날짜
//
//    private OrderStatus orderStatus;    // 주문 상태
//
//    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();    // 주문 상품 리스트
//
//    // OrderItemDto 객체를 주문 상품 리스트에 추가
//    public void addOrderItemDto(OrderItemDto orderItemDto){
//        orderItemDtoList.add(orderItemDto);
//    }
//
//
//}
