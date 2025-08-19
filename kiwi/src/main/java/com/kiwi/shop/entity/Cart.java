//package com.kiwi.shop.entity;
//
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//import javax.persistence.*;
//
//import com.kiwi.member.entity.Member;
//
//@Entity
//@Table(name = "cart")
//@Getter @Setter
//@ToString
//public class Cart extends BaseEntity {
//
//    @Id
//    @Column(name = "cart_id")
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @OneToOne(fetch = FetchType.LAZY)      // 일대일 관계 -> 회원 엔티티와 장바구니
//    @JoinColumn(name = "member_id")     // 매핑할 외래키 지정 -> name 속성안에 매핑할 외래키 이름 지정함 // 장바구니 엔티티에만 joinColumn이 있으므로 단방향 일대일 관계
//    private Member member;
//
//    // 회원 1명당 1개의 장바구니를 가지므로 처음 장바구니에 상품을 담을려면 해당 회원의 장바구니를 생성해줘야함.
//    public static Cart createCart(Member member){
//        Cart cart = new Cart();
//        cart.setMember(member);
//        return cart;
//    }
//}
