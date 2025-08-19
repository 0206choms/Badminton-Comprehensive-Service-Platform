//package com.kiwi.shop.entity;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Getter @Setter
//public class OrderItem extends BaseEntity{
//    @Id
//    @GeneratedValue
//    @Column(name = "order_item_id")
//    private Long id;
//
//    // 하나의 상품은 여러 주문 상품으로 들어갈 수 있다. 주문 상품 기준으로 다대일 단방향 매핑
//    @ManyToOne(fetch = FetchType.LAZY)      // 지연로딩 방식 지정 ( 사용하지 않는 데이터도 가져오기 때문에 즉시 로딩지정)
//    @JoinColumn(name = "item_id")
//    private Item item;
//
//    // 한 번에 주문에 여러개 상품이 들어갈 수 있다. 주문 상품 엔티티와 주문 엔티티를 다대일 단뱡향 매핑
//    @ManyToOne(fetch = FetchType.LAZY)      // 지연로딩 방식 지정 ( 사용하지 않는 데이터도 가져오기 때문에 즉시 로딩지정)
//    @JoinColumn(name = "order_id")
//    private Order order;
//
//    // 주문가격
//    private int orderPrice;
//
//    // 수량
//    private int count;
//
//    // Auditing 이용해서 BaseEntity로 부터 regTime, updateTime, createBy, modifyBy 상속 받고 알아서 지정되므로 기존에 필드는 주석처리
////    private LocalDateTime regTime;
////    private LocalDateTime updateTime;
//
//
//    public static OrderItem createOrderItem(Item item, int count){
//        OrderItem orderItem = new OrderItem();
//        orderItem.setItem(item);            // 주문할 상품 설정
//        orderItem.setCount(count);          // 주문할 수량 설정
//        orderItem.setOrderPrice(item.getPrice());       // 주문 가격 설정
//
//        item.removeStock(count);        // 주문 수량 만큼 재고에서 빼는 메소드 설정
//        return orderItem;
//    }
//
//    // 주문 수량과 가격을 곱해서 총 가격을 결정함
//    public int getTotalPrice(){
//        return orderPrice*count;
//    }
//
//    // 주문 취소시 주문 수량만큼 상품의 재고를 더해준다.
//    public void cancel(){
//        this.getItem().addStock(count);
//    }
//}
