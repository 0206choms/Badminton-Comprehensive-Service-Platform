//package com.kiwi.shop.entity;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Entity
//@Getter @Setter
//@Table(name = "cart_item")
//public class CartItem extends BaseEntity{
//
//    @Id
//    @GeneratedValue
//    @Column(name = "cart_item_id")
//    private Long id;
//
//    // 하나의 장바구니에는 여러개의 상품이 담을수 있으므로 다대일 관계
//    @ManyToOne (fetch = FetchType.LAZY)
//    @JoinColumn(name = "cart_id")
//    private Cart cart;
//
//    // 장바구니에 담을 상품의 정보를 알아야 하므로 상품 엔티티 매핑
//    // 하나의 상품은 여러개의 장바구니에 담을수 있으므로 대대일 관계
//    @ManyToOne (fetch = FetchType.LAZY)
//    @JoinColumn(name = "item_id")
//    private Item item;
//
//    private int count;
//
//    // 장바구니에 담을 장바구니 상품 엔티티 생성
//    public static CartItem createCartItem(Cart cart, Item item, int count){
//        CartItem cartItem = new CartItem();
//        cartItem.setCart(cart);
//        cartItem.setItem(item);
//        cartItem.setCount(count);
//        return cartItem;
//    }
//
//    // 장바구니 기존에 담겨 있는 상품에 해당 상품을 추가로 장바구니에 담을 경우 기존 수량에 현재 담을 수량을 더한다.
//    public void addCount(int count){
//        this.count += count;
//    }
//
//    public void updateCount(int count){
//        this.count = count;
//    }
//}
