//package com.kiwi.shop.entity;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//import com.kiwi.constant.OrderStatus;
//import com.kiwi.member.entity.Member;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Table(name = "orders")     // 정려할때 사용하는 order 키워드가 있으므로 테이블 이름은 orders로 지정
//@Getter @Setter
//public class Order extends BaseEntity {
//
//    @Id @GeneratedValue
//    @Column(name = "order_id")
//    private Long id;
//
//    // 한명의 회원은 여러번 주문할 수 있다. 주문 엔티티 기준 다대일 단방향 매핑
//    @ManyToOne (fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;
//
//    // 주문일
//    private LocalDateTime orderDate;
//
//    // 주문상태
//    @Enumerated(EnumType.STRING)
//    private OrderStatus orderStatus;
//
//    // 주문 상품과 일대다 매핑 // 외래키가 order_item 테이블에 있으므로 연관 관계 주인은 OrderItem 엔티티 이다.
//    // order테이블에 연관 관계 주인이 아니므로 mappedBy 설정! mappedBy에는 연관관계 주인 테이블의 필드를 적어주면 됨! ex) Order order 필드이므로 "order" 맞다!
//    // 부모 엔티티의 영속성 상태 변화를 자식 엔티티에 모두 전이하는 CascadeType.ALL옵션 설정
//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private List<OrderItem> orderItems = new ArrayList<>();    // 하나의 주문에는 여러개의 주문 상품을 갖으므로 List자료형 사용한다!
//
//    // Auditing 이용해서 BaseEntity로 부터 regTime, updateTime, createBy, modifyBy 상속 받고 알아서 지정되므로 기존에 필드는 주석처리
////    private LocalDateTime regTime;
////    private LocalDateTime updateTime;
//
//
//    // 위에서 생성한 orderItems 객체에 주문 상품 정보(orderItem)를 담아줍니다.
//    public void addOrderItem(OrderItem orderItem){
//        orderItems.add(orderItem);
//        orderItem.setOrder(this);   // order엔티티와 orderItem엔티티가 양방향 참조 관계이므로 orderItem객체에도 order객체를 세팅한다
//    }
//
//    public static Order createOrder(Member member, List<OrderItem> orderItemList){
//        Order order = new Order();
//        order.setMember(member);        // 상품을 주문한 회원 정보 세팅
//        // 상품 페이지에서는 1개의 주문을 하지만 장바구니 에서는 한번에 여러개의 주문을 할 수 있기 때문에 여러개의 주문 상품을 받을수 있도록 리스트 형태로 세팅
//        for(OrderItem orderItem : orderItemList){
//            order.addOrderItem(orderItem);
//        }
//        order.setOrderStatus(OrderStatus.ORDER);    // 주문 상태 ORDER로 세팅
//        order.setOrderDate(LocalDateTime.now());    // 주문 시간을 현재시간으로 설정
//        return order;
//    }
//
//    // 총 주문 금액을 세팅
//    public int getTotalPrice(){
//        int totalPrice = 0;
//        for (OrderItem orderItem: orderItems) {
//            totalPrice += orderItem.getTotalPrice();
//        }
//        return totalPrice;
//    }
//
//    public void cancelOrder(){
//        this.orderStatus = OrderStatus.CANCEL;
//        for (OrderItem orderItem: orderItems) {
//            orderItem.cancel();
//        }
//    }
//}
